package com.gilvano.statusservicosnfe.service.impl;

import com.gilvano.statusservicosnfe.model.Autorizador;
import com.gilvano.statusservicosnfe.model.AutorizadorHistoricoStatus;
import com.gilvano.statusservicosnfe.model.AutorizadorStatus;
import com.gilvano.statusservicosnfe.repository.AutorizadorRepository;
import com.gilvano.statusservicosnfe.repository.AutorizadorStatusRepository;
import com.gilvano.statusservicosnfe.resource.response.StatusEstado;
import com.gilvano.statusservicosnfe.service.AutorizadorStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AutorizadorStatusServiceImpl implements AutorizadorStatusService {

    private final AutorizadorStatusRepository autorizadorStatusRepository;
    private final AutorizadorRepository autorizadorRepository;

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
        autorizadorStatusRepository.save(AutorizadorHistoricoStatus.builder()
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
            statusEstadoList.add(StatusEstado.builder()
                    .estado(autorizador.getNome())
                    .status(autorizador.getStatusAtual())
                    .build());
        }
        return statusEstadoList;
    }
}
