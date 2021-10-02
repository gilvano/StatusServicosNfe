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
@Table(indexes = @Index(columnList = "autorizador"))
public class AutorizadorStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String autorizador;

    @NotNull
    private String status;

    @NotNull
    private LocalDateTime dataStatus;
}
