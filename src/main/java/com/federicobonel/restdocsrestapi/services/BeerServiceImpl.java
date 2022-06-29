package com.federicobonel.restdocsrestapi.services;

import com.federicobonel.restdocsrestapi.exceptions.ResourceNotFoundException;
import com.federicobonel.restdocsrestapi.model.Beer;
import com.federicobonel.restdocsrestapi.repositories.BeerRepository;
import com.federicobonel.restdocsrestapi.web.mappers.BeerMapper;
import com.federicobonel.restdocsrestapi.web.model.BeerDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    public BeerDTO findById(UUID id) {
        return beerRepository.findById(id)
                .map(beerMapper::beerToBeerDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public BeerDTO createBeer(BeerDTO beerDTO) {
        Beer savedBeer = beerRepository.save(beerMapper.beerDTOToBeer(beerDTO));
        return beerMapper.beerToBeerDTO(savedBeer);
    }

    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO beerDTO) {
        return beerRepository.findById(id)
                .map(beerInDB -> {
                    beerDTO.setId(id);
                    Beer savedBeer =  beerRepository.save(beerMapper.beerDTOToBeer(beerDTO));
                    return beerMapper.beerToBeerDTO(savedBeer);
                })
                .orElseThrow(ResourceNotFoundException::new);
    }
}
