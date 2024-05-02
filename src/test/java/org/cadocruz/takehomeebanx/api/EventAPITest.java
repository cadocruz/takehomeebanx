package org.cadocruz.takehomeebanx.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cadocruz.takehomeebanx.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
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
    private TransactionAPI eventAPI;

    @Test
    public void testHelloWorld() throws Exception {
        this.mvc.perform(get("/event")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello world!!!")));
    }

    //Test
    public void test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Account account = new Account("10", 30L);

        mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(account)));
    }
}
