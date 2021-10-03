package com.gilvano.statusservicosnfe.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutorizadorHistoricoStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="autorizador_id")
    private Autorizador autorizador;

    @NotNull
    private String status;

    @NotNull
    private LocalDateTime dataStatus;
}
