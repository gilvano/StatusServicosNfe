package com.gilvano.statusservicosnfe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatusOffLineOnLine {
    OFF_LINE("OFF_LINE"),
    ON_LINE("ON_LINE");

    @Getter private String value;
}
