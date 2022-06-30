package com.federicobonel.restdocsrestapi.datainitializer;

import com.federicobonel.restdocsrestapi.model.Beer;
import com.federicobonel.restdocsrestapi.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public DataInitializer(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        initializeData();

    }

    private void initializeData() {
        if (beerRepository.count() == 0) {
            initializerBeers();
        }
    }

    private void initializerBeers() {

        beerRepository.save(Beer.builder()
                .name("Beer 1")
                .style("IPA")
                .quantityToBrew(300)
                .quantityOnHand(12)
                .upc(72527273070L)
                .price(BigDecimal.valueOf(10))
                .build()
        );

        beerRepository.save(Beer.builder()
                .name("Catalaxy")
                .style("PALE_ALE")
                .quantityToBrew(350)
                .quantityOnHand(20)
                .upc(72527273071L)
                .price(BigDecimal.valueOf(8))
                .build()
        );


    }
}
