package com.gilvano.statusservicosnfe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AutorizadorSvanSvrsProprio {
    SVAN("SVAN"),
    SVRS("SVAN"),
    PROPRIO("PROPRIO");

    @Getter
    private String value;
}
