package com.gilvano.statusservicosnfe.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class StatusEstadoPorData {
    private String estado;
    private String status;
    private LocalDateTime data;
}
