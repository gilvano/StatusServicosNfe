package com.gilvano.statusservicosnfe.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class StatusEstado {
    private String estado;
    private String status;
}
