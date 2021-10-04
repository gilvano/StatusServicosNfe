package com.gilvano.statusservicosnfe.repository;

import com.gilvano.statusservicosnfe.resource.response.AutorizadorMaiorIndisponibilidade;
import com.gilvano.statusservicosnfe.model.AutorizadorHistoricoStatus;
import com.gilvano.statusservicosnfe.DTO.StatusAutorizadorPorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AutorizadorHistoricoStatusRepository extends JpaRepository<AutorizadorHistoricoStatus, Long> {
    @Query("select new com.gilvano.statusservicosnfe.DTO.StatusAutorizadorPorData(a.nome, h.status, h.dataStatus) from Autorizador a " +
            "join AutorizadorHistoricoStatus h on h.autorizador.id = a.id " +
            "where h.dataStatus between :dataInicial and :dataFinal")
    List<StatusAutorizadorPorData> findByDataStatus(@Param("dataInicial") LocalDateTime dataInicial, @Param("dataFinal") LocalDateTime dataFinal);

    @Query("select new com.gilvano.statusservicosnfe.resource.response.AutorizadorMaiorIndisponibilidade(a.nome, count(h.id)) from Autorizador a " +
            "join AutorizadorHistoricoStatus h on h.autorizador.id = a.id " +
            "where h.status = 'OFF_LINE' " +
            "group by a.nome")
    List<AutorizadorMaiorIndisponibilidade> findAutorizadorComMaiorIndisponibilidade();

}
