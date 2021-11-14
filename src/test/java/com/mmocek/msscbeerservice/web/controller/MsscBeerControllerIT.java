package com.mmocek.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmocek.msscbeerservice.bootstrap.BeerLoader;
import com.mmocek.msscbeerservice.services.BeerInventoryService;
import com.mmocek.msscbeerservice.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MsscBeerControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerInventoryService beerInventoryService;

    @Test
    void getBeer() throws Exception {

        when(beerInventoryService.getOnhandInventory(BeerLoader.BEER_1_UUID)).thenReturn(75);
        MvcResult mvcResult = mockMvc
                .perform(get("/api/v1/beer/" + BeerLoader.BEER_1_UUID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BeerDto beerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BeerDto.class);
        assertThat(beerDto.getQuantityOnHand()).isEqualTo(75);
        assertThat(beerDto.getBeerName()).isEqualTo("Mango Bobs");

    }
}
