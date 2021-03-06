package com.mmocek.msscbeerservice.services;

import com.mmocek.commons.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Profile("!local-discovery")
@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Component
public class BeerInventoryServiceRestTemplate implements BeerInventoryService {

    public final static String INVENTORY_PATH = "/api/v1/beerUpc/{beerUpc}/inventory";
    private final RestTemplate restTemplate;

    private String beerInventoryServiceHost;

    public BeerInventoryServiceRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    @Override
    public Integer getOnhandInventory(String beerUpc) {

        log.debug("Calling Inventory Service");

        ResponseEntity<List<BeerInventoryDto>> responseEntity =
                restTemplate.exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<BeerInventoryDto>>() {
        }, beerUpc);

        return Objects.requireNonNull(responseEntity.getBody()).stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum();
    }
}
