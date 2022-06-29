package com.federicobonel.restdocsrestapi.web.controllers;

import com.federicobonel.restdocsrestapi.services.BeerService;
import com.federicobonel.restdocsrestapi.web.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(BeerController.BEERS_URL)
public class BeerController {

    public static final String BEERS_URL = "/api/v1/beer";
    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public BeerDTO getById(@PathVariable UUID beerId) {
        log.info("Getting beer by id: " + beerId.toString());

        return beerService.findById(beerId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO createBeer(@RequestBody @Validated BeerDTO beerDTO) {
        log.info("Creating beer");

        return beerService.createBeer(beerDTO);
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public BeerDTO updateBeer(@PathVariable UUID beerId, @RequestBody @Validated BeerDTO beerDTO) {
        log.info("Updating beer with id: " + beerId.toString());

        return beerService.updateBeer(beerId, beerDTO);
    }
}
