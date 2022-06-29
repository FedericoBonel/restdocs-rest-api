package com.federicobonel.restdocsrestapi.repositories;

import com.federicobonel.restdocsrestapi.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
