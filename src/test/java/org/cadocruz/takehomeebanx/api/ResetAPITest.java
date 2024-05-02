package org.cadocruz.takehomeebanx.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResetAPITest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testResetAccounts() throws Exception {
        this.mvc.perform(post("/reset")).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    @Test
    public void testResetSendEvent() throws Exception {
        mvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"type\":\"deposit\", \"destination\":\"100\", \"amount\":10}"))
                .andExpect(status().isOk());

        this.mvc.perform(post("/reset")).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }
}
