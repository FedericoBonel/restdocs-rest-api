package com.federicobonel.restdocsrestapi.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.federicobonel.restdocsrestapi.services.BeerService;
import com.federicobonel.restdocsrestapi.web.model.BeerDTO;
import com.federicobonel.restdocsrestapi.web.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.federicobonel.restdocsrestapi.web.mappers")
class BeerControllerTest {

    public static final String NAME = "IPA crazy";
    public static final String STYLE = BeerStyle.IPA.name();
    public static final long UPC = 101010L;
    public static final int MIN_ON_HAND = 20;
    public static final BigDecimal PRICE = BigDecimal.valueOf(MIN_ON_HAND);

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    BeerService beerService;

    BeerDTO beer;

    String beerDTOJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        beer = BeerDTO.builder()
                .name(NAME)
                .style(STYLE)
                .upc(UPC)
                .price(PRICE)
                .minOnHand(MIN_ON_HAND)
                .build();

        beerDTOJson = mapper.writeValueAsString(beer);
    }

    @Test
    void getById() throws Exception {
        given(beerService.findById(any())).willReturn(beer);

        mockMvc.perform(get(generateRandomIDUrl())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createBeer() throws Exception {
        given(beerService.createBeer(any())).willReturn(beer);

        mockMvc.perform(post(BeerController.BEERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(beerDTOJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeer() throws Exception {
        given(beerService.updateBeer(any(), any())).willReturn(beer);

        mockMvc.perform(put(generateRandomIDUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(beerDTOJson))
                .andExpect(status().isOk());
    }

    String generateRandomIDUrl() {
        return BeerController.BEERS_URL + "/" + UUID.randomUUID();
    }
}