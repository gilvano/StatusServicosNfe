package com.gilvano.statusservicosnfe.service;

import java.util.List;

public interface AutorizadorEstadoService {
    String buscarAutorizadorDoEstado(String ufEstado);
    List<String> buscarEstadoDoAutorizador(String autorizador);
}
