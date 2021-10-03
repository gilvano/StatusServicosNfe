package com.gilvano.statusservicosnfe.service.impl;

import com.gilvano.statusservicosnfe.model.Autorizador;
import com.gilvano.statusservicosnfe.service.AutorizadorService;

import java.util.Optional;

public class AutorizadorServiceImpl implements AutorizadorService {
    @Override
    public Optional<Autorizador> FindById(Long id) {
        return Optional.empty();
    }
}
