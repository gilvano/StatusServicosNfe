package com.gilvano.statusservicosnfe.service;

import com.gilvano.statusservicosnfe.model.Autorizador;

import java.util.Optional;

public interface AutorizadorService {
    Optional<Autorizador> FindById(Long id);
}
