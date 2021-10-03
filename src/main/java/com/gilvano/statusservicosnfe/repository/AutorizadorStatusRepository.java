package com.gilvano.statusservicosnfe.repository;

import com.gilvano.statusservicosnfe.model.AutorizadorHistoricoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorizadorStatusRepository extends JpaRepository<AutorizadorHistoricoStatus, Long> {
}
