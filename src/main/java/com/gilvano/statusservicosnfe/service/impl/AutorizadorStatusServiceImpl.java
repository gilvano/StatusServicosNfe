package com.gilvano.statusservicosnfe.service.impl;

import com.gilvano.statusservicosnfe.model.AutorizadorStatus;
import com.gilvano.statusservicosnfe.repository.AutorizadorStatusRepository;
import com.gilvano.statusservicosnfe.service.AutorizadorStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AutorizadorStatusServiceImpl implements AutorizadorStatusService {

    private final AutorizadorStatusRepository repository;

    @Override
    public void salvar(AutorizadorStatus autorizadorStatus) {
        repository.save(autorizadorStatus);
    }
}
