package com.gilvano.statusservicosnfe.component;

import com.gilvano.statusservicosnfe.DTO.AutorizadorStatus;
import com.gilvano.statusservicosnfe.service.AutorizadorHistoricoStatusService;
import com.gilvano.statusservicosnfe.service.ConsultaStatusServicosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
@RequiredArgsConstructor
public class ConsultaStatusServicosJob {

    private final ConsultaStatusServicosService consultaStatusServicosService;
    private final AutorizadorHistoricoStatusService autorizadorHistoricoStatusService;

    @Scheduled(fixedRate = 300000)
    public void consultarStatusServicos() {
        log.info("Executando consulta de status dos serviços da nfe");

        try {
            Optional<List<AutorizadorStatus>> status = consultaStatusServicosService.consultarStatusServicos();

            status.orElse(Collections.emptyList())
                    .forEach(autorizador -> {
                        autorizadorHistoricoStatusService.salvar(autorizador);
                        log.info("Salvando Status do Autorizador: {}", autorizador.getAutorizador());
                    });
        } catch (IOException e) {
            log.warn("Erro ao consultar o status dos serviços da nfe, erro: " + e);
        }
    }
}
