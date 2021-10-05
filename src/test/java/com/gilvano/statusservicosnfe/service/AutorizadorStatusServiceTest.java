package com.gilvano.statusservicosnfe.service;

import com.gilvano.statusservicosnfe.model.Autorizador;
import com.gilvano.statusservicosnfe.repository.AutorizadorHistoricoStatusRepository;
import com.gilvano.statusservicosnfe.repository.AutorizadorRepository;
import com.gilvano.statusservicosnfe.resource.response.StatusEstado;
import com.gilvano.statusservicosnfe.service.impl.AutorizadorEstadoServiceImpl;
import com.gilvano.statusservicosnfe.service.impl.AutorizadorStatusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AutorizadorStatusServiceTest {

    AutorizadorStatusService service;

    @MockBean
    AutorizadorHistoricoStatusRepository autorizadorHistoricoStatusRepository;

    @MockBean
    AutorizadorRepository autorizadorRepository;

    @BeforeEach
    public void setUp(){
        AutorizadorEstadoServiceImpl autorizadorEstadoService = new AutorizadorEstadoServiceImpl(
                buscarEstadosAutorizadorSVAN(),
                buscarEstadosAutorizadorSVRS(),
                buscarEstadosAutorizadorProprio()
        );

        this.service = new AutorizadorStatusServiceImpl(
                autorizadorHistoricoStatusRepository,
                autorizadorRepository,
                autorizadorEstadoService );

        Mockito.when(autorizadorRepository.findAll()).thenReturn(buscarAutorizadorList());
        Mockito.when(autorizadorRepository.findByNome("SVRS")).thenReturn(Optional.of(Autorizador.builder().id(1l).nome("SVRS").statusAtual("ON_LINE").build()));
        Mockito.when(autorizadorRepository.findByNome("SVAN")).thenReturn(Optional.of(Autorizador.builder().id(2l).nome("SVAN").statusAtual("OFF_LINE").build()));
        Mockito.when(autorizadorRepository.findByNome("PR")).thenReturn(Optional.of(Autorizador.builder().id(3l).nome("PR").statusAtual("ON_LINE").build()));
        Mockito.when(autorizadorRepository.findByNome("RS")).thenReturn(Optional.of(Autorizador.builder().id(4l).nome("RS").statusAtual("OFF_LINE").build()));
        Mockito.when(autorizadorRepository.findByNome("SP")).thenReturn(Optional.of(Autorizador.builder().id(5l).nome("SP").statusAtual("ON_LINE").build()));
    }

    @Test
    @DisplayName("Deve retornar o status para o estado PR ao buscar um estado válido e on line")
    public void buscarStatusAtualDoEstadoPROnLineTest(){
        StatusEstado resultado = service.buscarStatusAtualDoEstado("PR");
        assertThat(resultado.getEstado()).isEqualTo("PR");
        assertThat(resultado.getStatus()).isEqualTo("ON_LINE");
    }

    @Test
    @DisplayName("Deve retornar o status para o estado ao buscar um estado válido e on line")
    public void buscarStatusAtualDoEstadoRSOffLineTest(){
        StatusEstado resultado = service.buscarStatusAtualDoEstado("RS");
        assertThat(resultado.getEstado()).isEqualTo("RS");
        assertThat(resultado.getStatus()).isEqualTo("OFF_LINE");
    }

    @Test
    @DisplayName("Deve retornar o status para o estados do SVRS ao buscar estados válidos e on line")
    public void buscarStatusAtualDoEstadosSVRSOnLineTest(){
        StatusEstado resultadoPA = service.buscarStatusAtualDoEstado("PA");
        assertThat(resultadoPA.getEstado()).isEqualTo("PA");
        assertThat(resultadoPA.getStatus()).isEqualTo("ON_LINE");

        StatusEstado resultadoSC = service.buscarStatusAtualDoEstado("SC");
        assertThat(resultadoSC.getEstado()).isEqualTo("SC");
        assertThat(resultadoSC.getStatus()).isEqualTo("ON_LINE");
    }

    @Test
    @DisplayName("Deve retornar o status para o estado MA do SVAN ao buscar um estado válido e off line")
    public void buscarStatusAtualDoEstadoSVANOffLineTest(){
        StatusEstado resultado = service.buscarStatusAtualDoEstado("MA");
        assertThat(resultado.getEstado()).isEqualTo("MA");
        assertThat(resultado.getStatus()).isEqualTo("OFF_LINE");
    }

    @Test
    @DisplayName("Deve retornar o status para o estado invalido")
    public void buscarStatusAtualDoEstadoInvalido(){
        assertThrows(ResponseStatusException.class, () -> {
                service.buscarStatusAtualDoEstado("KM");
            });
    }

    private List<Autorizador> buscarAutorizadorList() {
        List<Autorizador> autorizadorList = new ArrayList<>();
        autorizadorList.add(Autorizador.builder().id(1l).nome("SVRS").statusAtual("ON_LINE").build());
        autorizadorList.add(Autorizador.builder().id(2l).nome("SVAN").statusAtual("OFF_LINE").build());
        autorizadorList.add(Autorizador.builder().id(3l).nome("PR").statusAtual("ON_LINE").build());
        autorizadorList.add(Autorizador.builder().id(4l).nome("RS").statusAtual("OFF_LINE").build());
        autorizadorList.add(Autorizador.builder().id(5l).nome("SP").statusAtual("ON_LINE").build());
        return autorizadorList;
    }

    private List<String> buscarEstadosAutorizadorSVRS() {
        return List.of("AC","AL","AP","DF","ES","PA","PB","PI","RJ","RN","RO","RR","SC","SE","TO");
    }

    private List<String> buscarEstadosAutorizadorSVAN() {
        return List.of("MA");
    }
    private List<String> buscarEstadosAutorizadorProprio() {
        return List.of("PR", "SP", "RS");
    }

}
