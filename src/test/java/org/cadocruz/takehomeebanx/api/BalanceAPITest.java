package org.cadocruz.takehomeebanx.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cadocruz.takehomeebanx.domain.TransactionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.cadocruz.takehomeebanx.domain.TypeOperation.DEPOSIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BalanceAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetBalanceForNonExistingAccount() throws Exception {
        ResultActions result = mvc.perform(get("/balance")
                        .param("account_id", "100"))
                .andExpect(status().isNotFound());

        assertEquals("0", result.andReturn().getResponse().getContentAsString());
    }

    @Test
    void testGetBalanceForExistingAccount() throws Exception {
        TransactionRequest transactionRequestDeposit = new TransactionRequest(DEPOSIT, "100", null, 10L);
        mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(transactionRequestDeposit)))
                .andExpect(status().isCreated());


        ResultActions result = mvc.perform(get("/balance").param("account_id", "100"))
                .andExpect(status().isOk());

        assertEquals("10", result.andReturn().getResponse().getContentAsString());
    }
}
