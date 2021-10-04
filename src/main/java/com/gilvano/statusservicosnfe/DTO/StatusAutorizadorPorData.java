package com.gilvano.statusservicosnfe.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class StatusAutorizadorPorData {
    private String nome;
    private String status;
    private LocalDateTime dataStatus;
}
