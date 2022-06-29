package com.federicobonel.restdocsrestapi.web.mappers;

import com.federicobonel.restdocsrestapi.model.Beer;
import com.federicobonel.restdocsrestapi.web.model.BeerDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class}, componentModel = "spring")
public interface BeerMapper {

    BeerDTO beerToBeerDTO(Beer beer);

    Beer beerDTOToBeer(BeerDTO beerDTO);

}
