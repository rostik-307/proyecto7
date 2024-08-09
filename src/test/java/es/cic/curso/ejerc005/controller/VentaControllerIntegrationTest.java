// Java
package es.cic.curso.ejerc005.controller;

import es.cic.curso.ejerc005.model.Venta;
import es.cic.curso.ejerc005.repository.VentaRepository;
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
public class VentaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        ventaRepository.deleteAll(); // Limpiar la base de datos antes de cada prueba
    }

    @Test
    @WithMockUser(roles = "TAQUILLERO")
    void testAñadirVenta() throws Exception {
        // Arrange
        Venta venta = new Venta();
        venta.setNumeroEntradas(2);
        venta.setFechaHoraVenta(LocalDateTime.now());
        venta.setPrecioPorEntrada(10.0);
        venta.setNumeroEntradasDescuentoJoven(1);
        venta.setDescuentoGrupo(false);
        venta.setSalaId(1L);
        venta.setSesionId(1L);
        venta.setPrecioTotal(20.0);

        // Act & Assert
        mockMvc.perform(post("/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numeroEntradas\":2,\"fechaHoraVenta\":\"2023-10-10T10:00:00\",\"precioPorEntrada\":10.0,\"numeroEntradasDescuentoJoven\":1,\"descuentoGrupo\":false,\"salaId\":1,\"sesionId\":1,\"precioTotal\":20.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroEntradas").value(2))
                .andExpect(jsonPath("$.precioTotal").value(20.0));
    }


    @Test

    void testAñadirVenta_FalloNoTengoElRol() throws Exception {
        // Arrange
        Venta venta = new Venta();
        venta.setNumeroEntradas(2);
        venta.setFechaHoraVenta(LocalDateTime.now());
        venta.setPrecioPorEntrada(10.0);
        venta.setNumeroEntradasDescuentoJoven(1);
        venta.setDescuentoGrupo(false);
        venta.setSalaId(1L);
        venta.setSesionId(1L);
        venta.setPrecioTotal(20.0);

        // Act & Assert
        mockMvc.perform(post("/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numeroEntradas\":2,\"fechaHoraVenta\":\"2023-10-10T10:00:00\",\"precioPorEntrada\":10.0,\"numeroEntradasDescuentoJoven\":1,\"descuentoGrupo\":false,\"salaId\":1,\"sesionId\":1,\"precioTotal\":20.0}"))
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    void testLeerVenta() throws Exception {
        // Arrange
        Venta venta = new Venta();
        venta.setNumeroEntradas(2);
        venta.setFechaHoraVenta(LocalDateTime.now());
        venta.setPrecioPorEntrada(10.0);
        venta.setNumeroEntradasDescuentoJoven(1);
        venta.setDescuentoGrupo(false);
        venta.setSalaId(1L);
        venta.setSesionId(1L);
        venta.setPrecioTotal(20.0);
        venta = ventaRepository.save(venta);

        // Act & Assert
        mockMvc.perform(get("/ventas/" + venta.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(venta.getId()))
                .andExpect(jsonPath("$.numeroEntradas").value(2))
                .andExpect(jsonPath("$.precioTotal").value(20.0));
    }

    @Test
    void testListarVentas() throws Exception {
        // Arrange
        Venta venta1 = new Venta();
        venta1.setNumeroEntradas(2);
        venta1.setFechaHoraVenta(LocalDateTime.now());
        venta1.setPrecioPorEntrada(10.0);
        venta1.setNumeroEntradasDescuentoJoven(1);
        venta1.setDescuentoGrupo(false);
        venta1.setSalaId(1L);
        venta1.setSesionId(1L);
        venta1.setPrecioTotal(20.0);
        ventaRepository.save(venta1);

        Venta venta2 = new Venta();
        venta2.setNumeroEntradas(3);
        venta2.setFechaHoraVenta(LocalDateTime.now());
        venta2.setPrecioPorEntrada(15.0);
        venta2.setNumeroEntradasDescuentoJoven(0);
        venta2.setDescuentoGrupo(true);
        venta2.setSalaId(2L);
        venta2.setSesionId(2L);
        venta2.setPrecioTotal(45.0);
        venta2.setBorrado(true);
        ventaRepository.save(venta2);

        // Act & Assert
        mockMvc.perform(get("/ventas?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].numeroEntradas").value(2));
                ;
    }

    @Test
    @WithMockUser(username = "Juan", roles = "TAQUILLERO")
    void testDevolverVenta() throws Exception {
        // Arrange
        Venta venta = new Venta();
        venta.setNumeroEntradas(2);
        venta.setFechaHoraVenta(LocalDateTime.now());
        venta.setPrecioPorEntrada(10.0);
        venta.setNumeroEntradasDescuentoJoven(1);
        venta.setDescuentoGrupo(false);
        venta.setSalaId(1L);
        venta.setSesionId(1L);
        venta.setPrecioTotal(20.0);
        venta = ventaRepository.save(venta);

        // Act & Assert
        mockMvc.perform(delete("/ventas/" + venta.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}