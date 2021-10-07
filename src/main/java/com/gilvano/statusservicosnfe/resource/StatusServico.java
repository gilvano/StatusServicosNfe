package com.gilvano.statusservicosnfe.resource;

import com.gilvano.statusservicosnfe.resource.response.AutorizadorMaiorIndisponibilidade;
import com.gilvano.statusservicosnfe.resource.response.StatusEstado;
import com.gilvano.statusservicosnfe.resource.response.StatusEstadoPorData;
import com.gilvano.statusservicosnfe.service.impl.AutorizadorStatusServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<StatusEstadoPorData>> buscarStatusPorData(@Parameter(description = "Data no formato yyyyMMdd")
                                                                             @PathVariable String data,
                                                                         @RequestParam(
                                                                                 value = "page",
                                                                                 required = false,
                                                                                 defaultValue = "0") int page,
                                                                         @RequestParam(
                                                                                 value = "size",
                                                                                 required = false,
                                                                                 defaultValue = "10") int size){
        return ResponseEntity.ok(service.buscarStatusPorData(data, page, size));
    }

    @GetMapping(value = "autoriazadormaiorindisponibilidade", produces = {"application/json"})
    @Operation(summary = "Buscar o autorizador com maior indisponibilidade")
    public ResponseEntity<AutorizadorMaiorIndisponibilidade> buscarAutorizadorComMaiorIndisponibilidade(){
        return ResponseEntity.ok(service.buscarAutorizadorComMaiorIndisponibilidade());
    }
}
