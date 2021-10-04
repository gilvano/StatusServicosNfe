package com.gilvano.statusservicosnfe.service.impl;

import com.gilvano.statusservicosnfe.model.AutorizadorSvanSvrsProprio;
import com.gilvano.statusservicosnfe.service.AutorizadorEstadoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AutorizadorEstadoServiceImpl implements AutorizadorEstadoService {

    private List<String> estadosAutorizadorSVAN;

    @Value("#{'${estados.autorizador.svrs}'.split(',')}")
    private List<String> estadosAutorizadorSVRS;

    @Value("#{'${estados.autorizador.proprio}'.split(',')}")
    private List<String> estadosAutorizadorProprio;

    private Map<String, String> autorizadorEstado;

    public AutorizadorEstadoServiceImpl(
            @Value("#{'${estados.autorizador.svan}'.split(',')}") List<String> estadosAutorizadorSVAN,
            @Value("#{'${estados.autorizador.svrs}'.split(',')}") List<String> estadosAutorizadorSVRS,
            @Value("#{'${estados.autorizador.proprio}'.split(',')}") List<String> estadosAutorizadorProprio) {
        this.estadosAutorizadorSVAN = estadosAutorizadorSVAN;
        this.estadosAutorizadorSVRS = estadosAutorizadorSVRS;
        this.estadosAutorizadorProprio = estadosAutorizadorProprio;

        autorizadorEstado = new HashMap<>();

        carregarEstadosAutorizadorProprio();
        carregarEstadosAutorizador(estadosAutorizadorSVAN, AutorizadorSvanSvrsProprio.SVAN);
        carregarEstadosAutorizador(estadosAutorizadorSVRS, AutorizadorSvanSvrsProprio.SVRS);
    }

    private void carregarEstadosAutorizador(List<String> estados, AutorizadorSvanSvrsProprio svrs) {
        estados.forEach(estado ->
                autorizadorEstado.put(estado, svrs.toString()));
    }

    private void carregarEstadosAutorizadorProprio() {
        estadosAutorizadorProprio.forEach(estado ->
                autorizadorEstado.put(estado, estado));
    }

    @Override
    public String buscarAutorizadorDoEstado(String ufEstado) {
        return autorizadorEstado.get(ufEstado);
    }

    @Override
    public List<String> buscarEstadoDoAutorizador(String autorizador) {
        return autorizadorEstado.entrySet().stream()
                .filter(a-> a.getValue().equalsIgnoreCase(autorizador))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


}
