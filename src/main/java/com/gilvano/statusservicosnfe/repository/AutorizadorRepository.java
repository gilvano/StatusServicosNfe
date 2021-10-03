package com.gilvano.statusservicosnfe.repository;

import com.gilvano.statusservicosnfe.model.Autorizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorizadorRepository extends JpaRepository<Autorizador, Long> {
//    Optional<Autorizador> findById(Long id);
    Optional<Autorizador> findByNome(String nome);
}
