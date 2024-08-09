package es.cic.curso.ejerc005;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenAccessActuatorWithoutAuthentication_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/actuator"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void whenAccessActuatorWithAuthentication_thenOk() throws Exception {
        mockMvc.perform(get("/actuator"))
            .andExpect(status().isOk());
    }
}
