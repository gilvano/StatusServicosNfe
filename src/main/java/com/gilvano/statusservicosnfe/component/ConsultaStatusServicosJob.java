package com.gilvano.statusservicosnfe.component;

import com.gilvano.statusservicosnfe.model.AutorizadorHistoricoStatus;
import com.gilvano.statusservicosnfe.model.AutorizadorStatus;
import com.gilvano.statusservicosnfe.service.impl.AutorizadorStatusServiceImpl;
import com.gilvano.statusservicosnfe.service.impl.ConsultaStatusServicosServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
@RequiredArgsConstructor
public class ConsultaStatusServicosJob {

    private final ConsultaStatusServicosServiceImpl consultaStatusServicosService;
    private final AutorizadorStatusServiceImpl autorizadorStatusService;

    @Scheduled(fixedRate = 300000)
    public void consultarStatusServicos() {
        log.info("Executando consulta de status dos serviços da nfe");

        try {
            Optional<List<AutorizadorStatus>> servicos = consultaStatusServicosService.consultarStatusServicos();
            if (servicos.isPresent()){
                for (AutorizadorStatus autorizador: servicos.get()){
                    autorizadorStatusService.salvar(autorizador);
                    log.info("Salvando Autorizador: {} Status: {} DataStatus: {}",
                            autorizador.getAutorizador(),
                            autorizador.getStatus(),
                            autorizador.getDataStatus());
                }
            }
        } catch (IOException e) {
            log.warn("Erro ao consultar o status dos serviços da nfe, erro: " + e.getStackTrace());
        }
    }
}
