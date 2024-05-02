package org.cadocruz.takehomeebanx.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cadocruz.takehomeebanx.domain.Account;
import org.cadocruz.takehomeebanx.domain.TransactionRequest;
import org.cadocruz.takehomeebanx.domain.TransactionResponse;
import org.cadocruz.takehomeebanx.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.cadocruz.takehomeebanx.domain.TypeOperation.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EventAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    TransactionRequest transactionRequestDeposit;
    TransactionRequest transactionRequestWithdraw;
    TransactionRequest transactionRequestTransfer;

    @BeforeEach
    void setUp() {
        transactionRequestDeposit = new TransactionRequest(DEPOSIT, "100", null, 10L);
        transactionRequestWithdraw = new TransactionRequest(WITHDRAW, null, "100", 10L);
        transactionRequestTransfer = new TransactionRequest(TRANSFER, "300", "100", 5L);
    }

    @Test
    void testCreateAccountWithInitialBalance() throws Exception {

        ResultActions result = mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(transactionRequestDeposit)))
                .andExpect(status().isCreated());

        TransactionResponse transactionResponse = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), TransactionResponse.class);

        assertEquals(10L, transactionResponse.getDestination().getBalance());
    }

    @Test
    void testDepositIntoExistingAccount() throws Exception {

        mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(transactionRequestDeposit)))
                .andExpect(status().isCreated());

        ResultActions result = mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(transactionRequestDeposit)))
                .andExpect(status().isCreated());

        TransactionResponse transactionResponse = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), TransactionResponse.class);

        assertEquals(20L, transactionResponse.getDestination().getBalance());
    }

    @Test
    void testWithdrawFromNonExistingAccount() throws Exception {

        ResultActions result = mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(transactionRequestWithdraw)))
                .andExpect(status().isNotFound());

        assertEquals("0", result.andReturn().getResponse().getContentAsString());
    }

    @Test
    void testWithdrawFromExistingAccount() throws Exception {

        mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(transactionRequestDeposit)))
                .andExpect(status().isCreated());

        ResultActions result = mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(transactionRequestWithdraw)))
                .andExpect(status().isCreated());

        TransactionResponse transactionResponse = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), TransactionResponse.class);

        assertEquals(0L, transactionResponse.getOrigin().getBalance());
    }

    @Test
    void testTransferFromExistingAccount() throws Exception {

        mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(transactionRequestDeposit)))
                .andExpect(status().isCreated());

        ResultActions result = mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(transactionRequestTransfer)))
                .andExpect(status().isCreated());

        TransactionResponse transactionResponse = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), TransactionResponse.class);

        assertEquals(5L, transactionResponse.getOrigin().getBalance());
        assertEquals(5L, transactionResponse.getDestination().getBalance());
    }
}
