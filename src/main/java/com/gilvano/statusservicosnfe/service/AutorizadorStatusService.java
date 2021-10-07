package com.gilvano.statusservicosnfe.service;

import com.gilvano.statusservicosnfe.resource.response.AutorizadorMaiorIndisponibilidade;
import com.gilvano.statusservicosnfe.resource.response.StatusEstado;
import com.gilvano.statusservicosnfe.resource.response.StatusEstadoPorData;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AutorizadorStatusService {
    List<StatusEstado> buscarStatusAtualPorEstado();
    StatusEstado buscarStatusAtualDoEstado(String estado);
    Page<StatusEstadoPorData> buscarStatusPorData(String data, int page, int size);
    AutorizadorMaiorIndisponibilidade buscarAutorizadorComMaiorIndisponibilidade();
}
