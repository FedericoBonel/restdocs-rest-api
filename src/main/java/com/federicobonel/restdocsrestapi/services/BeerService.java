package com.federicobonel.restdocsrestapi.services;

import com.federicobonel.restdocsrestapi.web.model.BeerDTO;

import java.util.UUID;

public interface BeerService {

    BeerDTO findById(UUID id);

    BeerDTO createBeer(BeerDTO beerDTO);

    BeerDTO updateBeer(UUID id, BeerDTO beerDTO);

}
