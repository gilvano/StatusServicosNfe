package com.gilvano.statusservicosnfe.service;

import com.gilvano.statusservicosnfe.model.AutorizadorHistoricoStatus;
import com.gilvano.statusservicosnfe.model.AutorizadorStatus;
import com.gilvano.statusservicosnfe.resource.response.StatusEstado;
import com.gilvano.statusservicosnfe.resource.response.StatusEstadoPorData;

import java.util.Date;
import java.util.List;

public interface AutorizadorStatusService {
    void salvar(AutorizadorStatus autorizadorStatus);
    List<StatusEstado> buscarStatusAtualPorEstado();

    StatusEstado buscarStatusAtualDoEstado(String estado);

    List<StatusEstadoPorData> buscarStatusPorData(String data);
}
