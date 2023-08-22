package com.financial.fintechorg.controller;


import java.math.BigDecimal;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financial.fintechorg.config.BaseIntegrationTest;
import com.financial.fintechorg.dto.TransactionDTO;
import com.financial.fintechorg.helper.UUIDHelper;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class TransactionControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldSucceedPost() throws Exception {
        final TransactionDTO transactionDTO = new TransactionDTO(UUIDHelper.defaultUuid, UUIDHelper.uuid2, BigDecimal.valueOf(100));
        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(transactionDTO));
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void shouldGetNoTransactions() throws Exception {
        final TransactionDTO transactionDTO = new TransactionDTO(UUIDHelper.defaultUuid, UUIDHelper.uuid2, BigDecimal.valueOf(100));
        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/transactions/user/{userId}", UUIDHelper.defaultUuid);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(0)));
    }
}