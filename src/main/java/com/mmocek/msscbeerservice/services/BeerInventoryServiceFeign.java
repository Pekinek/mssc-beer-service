package com.mmocek.msscbeerservice.services;

import com.mmocek.commons.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Profile("local-discovery")
@Service
@RequiredArgsConstructor
public class BeerInventoryServiceFeign implements BeerInventoryService{

    private final BeerInventoryServiceFeignClient inventoryServiceFeignClient;

    @Override
    public Integer getOnhandInventory(String beerUpc) {

        log.debug("Calling feign inventory service for " + beerUpc);

        ResponseEntity<List<BeerInventoryDto>> onhandInventory = inventoryServiceFeignClient.getOnhandInventory(beerUpc);
        int onHand = Objects.requireNonNull(onhandInventory.getBody())
                         .stream()
                         .mapToInt(BeerInventoryDto::getQuantityOnHand)
                         .sum();

        log.debug("BeerUpc: " + beerUpc + " onhand: " + onhandInventory);

        return onHand;
    }
}
