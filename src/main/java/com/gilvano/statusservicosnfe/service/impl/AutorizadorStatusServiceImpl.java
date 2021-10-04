package com.gilvano.statusservicosnfe.service.impl;

import com.gilvano.statusservicosnfe.model.Autorizador;
import com.gilvano.statusservicosnfe.model.AutorizadorHistoricoStatus;
import com.gilvano.statusservicosnfe.DTO.AutorizadorStatus;
import com.gilvano.statusservicosnfe.DTO.StatusAutorizadorPorData;
import com.gilvano.statusservicosnfe.repository.AutorizadorHistoricoStatusRepository;
import com.gilvano.statusservicosnfe.repository.AutorizadorRepository;
import com.gilvano.statusservicosnfe.resource.response.AutorizadorMaiorIndisponibilidade;
import com.gilvano.statusservicosnfe.resource.response.StatusEstado;
import com.gilvano.statusservicosnfe.resource.response.StatusEstadoPorData;
import com.gilvano.statusservicosnfe.service.AutorizadorEstadoService;
import com.gilvano.statusservicosnfe.service.AutorizadorStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AutorizadorStatusServiceImpl implements AutorizadorStatusService {

    private final AutorizadorHistoricoStatusRepository autorizadorHistoricoStatusRepository;
    private final AutorizadorRepository autorizadorRepository;
    private final AutorizadorEstadoService autorizadorEstadoService;

    @Override
    public void salvar(AutorizadorStatus autorizadorStatus) {
        Optional<Autorizador> autorizador = autorizadorRepository.findByNome(autorizadorStatus.getAutorizador());
        if (autorizador.isPresent()) {
            salvarHistoricoAutorizador(autorizadorStatus, autorizador.get());
            autorizador.get().setStatusAtual(autorizadorStatus.getStatus());
            autorizadorRepository.save(autorizador.get());
        } else {
            Autorizador novoAutorizador = salvarNovoAutorizador(autorizadorStatus);
            salvarHistoricoAutorizador(autorizadorStatus, novoAutorizador);
        }
    }

    private Autorizador salvarNovoAutorizador(AutorizadorStatus autorizadorStatus) {
        return autorizadorRepository.save(Autorizador.builder()
                .nome(autorizadorStatus.getAutorizador())
                .statusAtual(autorizadorStatus.getStatus())
                .build());
    }

    private void salvarHistoricoAutorizador(AutorizadorStatus autorizadorStatus, Autorizador  autorizador) {
        autorizadorHistoricoStatusRepository.save(AutorizadorHistoricoStatus.builder()
                .autorizador(autorizador)
                .status(autorizadorStatus.getStatus())
                .dataStatus(autorizadorStatus.getDataStatus())
                .status(autorizadorStatus.getStatus())
                .build());
    }

    @Override
    public List<StatusEstado> buscarStatusAtualPorEstado() {
        List<Autorizador> autorizadorList = autorizadorRepository.findAll();
        List<StatusEstado> statusEstadoList = new ArrayList<>();
        for (Autorizador autorizador: autorizadorList) {
            List<String> estadosAutorizador = autorizadorEstadoService.buscarEstadoDoAutorizador(autorizador.getNome());
            estadosAutorizador.forEach(estado ->
                statusEstadoList.add(StatusEstado.builder()
                        .estado(estado)
                        .status(autorizador.getStatusAtual())
                        .build())
            );
        }
        statusEstadoList.sort(Comparator.comparing(StatusEstado::getEstado));
        return statusEstadoList;
    }

    @Override
    public StatusEstado buscarStatusAtualDoEstado(String estado) {
        String autorizador = autorizadorEstadoService.buscarAutorizadorDoEstado(estado.toUpperCase());
        Optional<Autorizador> autorizadorEstatus = autorizadorRepository.findByNome(autorizador);
        if(autorizadorEstatus.isPresent()) {
            return StatusEstado.builder()
                    .estado(estado.toUpperCase())
                    .status(autorizadorEstatus.get().getStatusAtual())
                    .build();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "Autorizador não encontrado para o estado: " + estado
            );
        }
    }

    @Override
    public List<StatusEstadoPorData> buscarStatusPorData(String data) {
        LocalDate dataFiltro = converterParametroData(data);
        LocalDateTime dataInicial = dataFiltro.atTime(0, 0, 1);
        LocalDateTime dataFinal = dataFiltro.atTime(23, 59, 59);
        List<StatusAutorizadorPorData> statusAutorizadorList = autorizadorHistoricoStatusRepository.findByDataStatus(dataInicial, dataFinal);
        List<StatusEstadoPorData> statusEstadoPorDataList = new ArrayList<>();

        statusAutorizadorList.forEach(autorizador -> {
            List<String> estadosAutorizador = autorizadorEstadoService.buscarEstadoDoAutorizador(autorizador.getNome());
            estadosAutorizador.forEach(estado ->
                    statusEstadoPorDataList.add(StatusEstadoPorData.builder()
                            .estado(estado)
                            .status(autorizador.getStatus())
                            .data(autorizador.getDataStatus())
                            .build())
            );
        });

        statusEstadoPorDataList.sort(Comparator.comparing(StatusEstadoPorData::getEstado));
        return statusEstadoPorDataList;
    }

    @Override
    public AutorizadorMaiorIndisponibilidade buscarAutorizadorComMaiorIndisponibilidade() {
        List<AutorizadorMaiorIndisponibilidade> autorizadorComMaiorIndisponibilidadeList =
                autorizadorHistoricoStatusRepository.findAutorizadorComMaiorIndisponibilidade();

        return autorizadorComMaiorIndisponibilidadeList
                .stream()
                .max(Comparator.comparing(AutorizadorMaiorIndisponibilidade::getQuantidade))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Não foi encontrando Autorizador com indisponibilidade."));
    }

    private LocalDate converterParametroData(String data) {
        LocalDate dataFiltro;
        try {
            dataFiltro = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data informada em formato inválido. Formato esperado yyyyMMdd");
        }
        return dataFiltro;
    }
}
