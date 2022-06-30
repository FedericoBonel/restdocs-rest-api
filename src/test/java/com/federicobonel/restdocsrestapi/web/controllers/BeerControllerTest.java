package com.federicobonel.restdocsrestapi.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.federicobonel.restdocsrestapi.ConstrainedFields;
import com.federicobonel.restdocsrestapi.services.BeerService;
import com.federicobonel.restdocsrestapi.web.model.BeerDTO;
import com.federicobonel.restdocsrestapi.web.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
    public static final String BEER_SNIPPET_IDENTIFIER = "v1/beer";
    public static final String BEER_URL_ID_PARAM = "/api/v1/beer/{beerId}";

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
                .quantityOnHand(MIN_ON_HAND)
                .build();

        beerDTOJson = mapper.writeValueAsString(beer);
    }

    @Test
    void getById() throws Exception {
        given(beerService.findById(any())).willReturn(beer);

        mockMvc.perform(get(BEER_URL_ID_PARAM, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(BEER_SNIPPET_IDENTIFIER,
                        pathParameters(
                                parameterWithName("beerId").description("UUID of the desired beer to get.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of the beer."),
                                fieldWithPath("version").description("Version number."),
                                fieldWithPath("creationDate").description("Date of beer creation."),
                                fieldWithPath("lastModifiedDate").description("Date of last beer modification."),
                                fieldWithPath("name").description("Beer name."),
                                fieldWithPath("style").description("Beer style (i.g. IPA, Lager)."),
                                fieldWithPath("upc").description("Universal product code of beer"),
                                fieldWithPath("price").description("Beer price"),
                                fieldWithPath("quantityOnHand").description("Quantity of beers on hand")
                        )));
    }

    @Test
    void createBeer() throws Exception {

        ConstrainedFields fields = new ConstrainedFields(BeerDTO.class);

        given(beerService.createBeer(any())).willReturn(beer);

        mockMvc.perform(post(BeerController.BEERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(beerDTOJson))
                .andExpect(status().isCreated())
                .andDo(document(BEER_SNIPPET_IDENTIFIER,
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("creationDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("name").description("Beer name."),
                                fields.withPath("style").description("Beer style (i.g. IPA, Lager)."),
                                fields.withPath("upc").description("Universal product code of beer"),
                                fields.withPath("price").description("Beer price"),
                                fields.withPath("quantityOnHand").description("Quantity of beers on hand")
                        )));
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