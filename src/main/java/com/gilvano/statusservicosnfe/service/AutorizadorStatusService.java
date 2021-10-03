package com.gilvano.statusservicosnfe.service;

import com.gilvano.statusservicosnfe.model.AutorizadorHistoricoStatus;
import com.gilvano.statusservicosnfe.model.AutorizadorStatus;
import com.gilvano.statusservicosnfe.resource.response.StatusEstado;

import java.util.List;

public interface AutorizadorStatusService {
    void salvar(AutorizadorStatus autorizadorStatus);
    List<StatusEstado> buscarStatusAtualPorEstado();
}
