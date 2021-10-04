package com.gilvano.statusservicosnfe.resource;

import com.gilvano.statusservicosnfe.resource.response.AutorizadorMaiorIndisponibilidade;
import com.gilvano.statusservicosnfe.resource.response.StatusEstado;
import com.gilvano.statusservicosnfe.resource.response.StatusEstadoPorData;
import com.gilvano.statusservicosnfe.service.impl.AutorizadorStatusServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "Status Serviços")
@RequestMapping("/api/statusservico")
@RequiredArgsConstructor
public class StatusServico {

    private final AutorizadorStatusServiceImpl service;

    @GetMapping(value = "statusatual", produces = {"application/json"})
    @Operation(summary = "Buscar status atual por estado")
    public ResponseEntity<List<StatusEstado>> buscarStatusAtualPorEstado(){
        return ResponseEntity.ok(service.buscarStatusAtualPorEstado());
    }

    @GetMapping(value = "statusatualestado/{estado}", produces = {"application/json"})
    @Operation(summary = "Buscar status atual filtrando por estado")
    public ResponseEntity<StatusEstado> buscarStatusAtualPorEstado(@Parameter(description = "UF do estado")
                                                                       @PathVariable String estado){
        return ResponseEntity.ok(service.buscarStatusAtualDoEstado(estado));
    }

    @GetMapping(value = "statuspordata/{data}", produces = {"application/json"})
    @Operation(summary = "Buscar status dos estados filtrando por data")
    public ResponseEntity<List<StatusEstadoPorData>> buscarStatusPorData(@Parameter(description = "Data no formato yyyyMMdd")
                                                                             @PathVariable String data){
        return ResponseEntity.ok(service.buscarStatusPorData(data));
    }

    @GetMapping(value = "autoriazadormaiorindisponibilidade", produces = {"application/json"})
    @Operation(summary = "Buscar o autorizador com maior indisponibilidade")
    public ResponseEntity<AutorizadorMaiorIndisponibilidade> buscarAutorizadorComMaiorIndisponibilidade(){
        return ResponseEntity.ok(service.buscarAutorizadorComMaiorIndisponibilidade());
    }
}
