// Java
package es.cic.curso.ejerc005.controller;

import es.cic.curso.ejerc005.model.Sofa;
import es.cic.curso.ejerc005.repository.SofaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SofaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SofaRepository sofaRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        sofaRepository.deleteAll(); // Limpiar la base de datos antes de cada prueba
    }

    @Test
    @WithMockUser(roles = "TAQUILLERO")
    void testAñadirSofa() throws Exception {
        // Arrange
        Sofa sofa = new Sofa();
        sofa.setNumeroEntradas(2);
        sofa.setFechaHoraSofa(LocalDateTime.now());
        sofa.setPrecioPorEntrada(10.0);
        sofa.setNumeroEntradasDescuentoJoven(1);
        sofa.setDescuentoGrupo(false);
        sofa.setSalaId(1L);
        sofa.setSesionId(1L);
        sofa.setPrecioTotal(20.0);

        // Act & Assert
        mockMvc.perform(post("/sofas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numeroEntradas\":2,\"fechaHoraSofa\":\"2023-10-10T10:00:00\",\"precioPorEntrada\":10.0,\"numeroEntradasDescuentoJoven\":1,\"descuentoGrupo\":false,\"salaId\":1,\"sesionId\":1,\"precioTotal\":20.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroEntradas").value(2))
                .andExpect(jsonPath("$.precioTotal").value(20.0));
    }


    @Test

    void testAñadirSofa_FalloNoTengoElRol() throws Exception {
        // Arrange
        Sofa sofa = new Sofa();
        sofa.setNumeroEntradas(2);
        sofa.setFechaHoraSofa(LocalDateTime.now());
        sofa.setPrecioPorEntrada(10.0);
        sofa.setNumeroEntradasDescuentoJoven(1);
        sofa.setDescuentoGrupo(false);
        sofa.setSalaId(1L);
        sofa.setSesionId(1L);
        sofa.setPrecioTotal(20.0);

        // Act & Assert
        mockMvc.perform(post("/sofas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numeroEntradas\":2,\"fechaHoraSofa\":\"2023-10-10T10:00:00\",\"precioPorEntrada\":10.0,\"numeroEntradasDescuentoJoven\":1,\"descuentoGrupo\":false,\"salaId\":1,\"sesionId\":1,\"precioTotal\":20.0}"))
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    void testLeerSofa() throws Exception {
        // Arrange
        Sofa sofa = new Sofa();
        sofa.setNumeroEntradas(2);
        sofa.setFechaHoraSofa(LocalDateTime.now());
        sofa.setPrecioPorEntrada(10.0);
        sofa.setNumeroEntradasDescuentoJoven(1);
        sofa.setDescuentoGrupo(false);
        sofa.setSalaId(1L);
        sofa.setSesionId(1L);
        sofa.setPrecioTotal(20.0);
        sofa = sofaRepository.save(sofa);

        // Act & Assert
        mockMvc.perform(get("/sofas/" + sofa.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sofa.getId()))
                .andExpect(jsonPath("$.numeroEntradas").value(2))
                .andExpect(jsonPath("$.precioTotal").value(20.0));
    }

    @Test
    void testListarSofas() throws Exception {
        // Arrange
        Sofa sofa1 = new Sofa();
        sofa1.setNumeroEntradas(2);
        sofa1.setFechaHoraSofa(LocalDateTime.now());
        sofa1.setPrecioPorEntrada(10.0);
        sofa1.setNumeroEntradasDescuentoJoven(1);
        sofa1.setDescuentoGrupo(false);
        sofa1.setSalaId(1L);
        sofa1.setSesionId(1L);
        sofa1.setPrecioTotal(20.0);
        sofaRepository.save(sofa1);

        Sofa sofa2 = new Sofa();
        sofa2.setNumeroEntradas(3);
        sofa2.setFechaHoraSofa(LocalDateTime.now());
        sofa2.setPrecioPorEntrada(15.0);
        sofa2.setNumeroEntradasDescuentoJoven(0);
        sofa2.setDescuentoGrupo(true);
        sofa2.setSalaId(2L);
        sofa2.setSesionId(2L);
        sofa2.setPrecioTotal(45.0);
        sofa2.setBorrado(true);
        sofaRepository.save(sofa2);

        // Act & Assert
        mockMvc.perform(get("/sofas?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].numeroEntradas").value(2));
                ;
    }

    @Test
    @WithMockUser(username = "Juan", roles = "TAQUILLERO")
    void testDevolverSofa() throws Exception {
        // Arrange
        Sofa sofa = new Sofa();
        sofa.setNumeroEntradas(2);
        sofa.setFechaHoraSofa(LocalDateTime.now());
        sofa.setPrecioPorEntrada(10.0);
        sofa.setNumeroEntradasDescuentoJoven(1);
        sofa.setDescuentoGrupo(false);
        sofa.setSalaId(1L);
        sofa.setSesionId(1L);
        sofa.setPrecioTotal(20.0);
        sofa = sofaRepository.save(sofa);

        // Act & Assert
        mockMvc.perform(delete("/sofas/" + sofa.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}