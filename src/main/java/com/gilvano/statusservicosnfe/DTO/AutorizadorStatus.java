package com.gilvano.statusservicosnfe.DTO;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AutorizadorStatus {
    private String autorizador;
    private String status;
    private LocalDateTime dataStatus;
}
