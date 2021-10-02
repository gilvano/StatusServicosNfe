package com.gilvano.statusservicosnfe.service.impl;

import com.gilvano.statusservicosnfe.model.AutorizadorStatus;
import com.gilvano.statusservicosnfe.model.StatusOffLineOnLine;
import com.gilvano.statusservicosnfe.service.ConsultaStatusServicosService;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ConsultaStatusServicosServiceImpl implements ConsultaStatusServicosService {
    private final String STATUS_ONLINE = "imagens/bola_verde_P.png";
    private Document doc;
    private Element table;
    private Elements rows;
    @Override
    public Optional<List<AutorizadorStatus>> consultarStatusServicos() throws IOException {
        List<AutorizadorStatus> servicos = new ArrayList<>();
        carregarSite();
        carregarTabelaListagemDados();
        carregarLinhas();
        for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
            servicos.add(buscarStatusAutorizador(i));
        }

        return Optional.of(servicos);
    }

    private AutorizadorStatus buscarStatusAutorizador(int i) {
        Element row = rows.get(i);
        Elements cols = row.select("td");
        String status = cols.get(5).getElementsByTag("img").first().attr("src");
        return AutorizadorStatus.builder()
                .autorizador(cols.get(0).text())
                .status(status.equals(STATUS_ONLINE)? StatusOffLineOnLine.ON_LINE.getValue() : StatusOffLineOnLine.OFF_LINE.getValue())
                .dataStatus(LocalDateTime.now())
                .build();
    }

    private void carregarSite() throws IOException {
        doc = Jsoup.connect("http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx")
                .timeout(30000)
                .get();
    }

    private void carregarTabelaListagemDados() {
        table =  doc.getElementsByClass("tabelaListagemDados").first();
    }

    private void carregarLinhas() {
        rows = table.select("tr");
    }

}
