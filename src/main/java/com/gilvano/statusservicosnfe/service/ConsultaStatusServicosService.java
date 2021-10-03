package com.gilvano.statusservicosnfe.service;

import com.gilvano.statusservicosnfe.model.AutorizadorHistoricoStatus;
import com.gilvano.statusservicosnfe.model.AutorizadorStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ConsultaStatusServicosService {
    Optional<List<AutorizadorStatus>> consultarStatusServicos() throws IOException;
}
