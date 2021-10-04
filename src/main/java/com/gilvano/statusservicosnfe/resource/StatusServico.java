package com.gilvano.statusservicosnfe.resource;

import com.gilvano.statusservicosnfe.resource.response.StatusEstado;
import com.gilvano.statusservicosnfe.service.impl.AutorizadorStatusServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statusservico")
@RequiredArgsConstructor
public class StatusServico {

    private final AutorizadorStatusServiceImpl service;

    @GetMapping(value = "statusatual" ,produces = {"application/json"})
    public ResponseEntity<List<StatusEstado>> buscarStatusAtualPorEstado(){
        return ResponseEntity.ok(service.buscarStatusAtualPorEstado());
    }

    @GetMapping(value = "statusatualestado/{estado}" ,produces = {"application/json"})
    public ResponseEntity<StatusEstado> buscarStatusAtualPorEstado(@PathVariable String estado){
        return ResponseEntity.ok(service.buscarStatusAtualDoEstado(estado));
    }


}
