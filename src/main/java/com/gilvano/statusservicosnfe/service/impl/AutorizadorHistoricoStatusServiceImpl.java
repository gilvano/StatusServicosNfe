package com.gilvano.statusservicosnfe.service.impl;

import com.gilvano.statusservicosnfe.DTO.AutorizadorStatus;
import com.gilvano.statusservicosnfe.model.Autorizador;
import com.gilvano.statusservicosnfe.model.AutorizadorHistoricoStatus;
import com.gilvano.statusservicosnfe.repository.AutorizadorHistoricoStatusRepository;
import com.gilvano.statusservicosnfe.repository.AutorizadorRepository;
import com.gilvano.statusservicosnfe.service.AutorizadorEstadoService;
import com.gilvano.statusservicosnfe.service.AutorizadorHistoricoStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AutorizadorHistoricoStatusServiceImpl implements AutorizadorHistoricoStatusService {
    private final AutorizadorHistoricoStatusRepository autorizadorHistoricoStatusRepository;
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
        autorizadorHistoricoStatusRepository.save(AutorizadorHistoricoStatus.builder()
                .autorizador(autorizador)
                .status(autorizadorStatus.getStatus())
                .dataStatus(autorizadorStatus.getDataStatus())
                .status(autorizadorStatus.getStatus())
                .build());
    }
}
