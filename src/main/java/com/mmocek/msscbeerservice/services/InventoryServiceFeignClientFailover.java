package com.mmocek.msscbeerservice.services;

import com.mmocek.commons.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class InventoryServiceFeignClientFailover implements BeerInventoryServiceFeignClient {

    private final InventoryFailoverFeignClient failoverFeignClient;

    @Override
    public ResponseEntity<List<BeerInventoryDto>> getOnhandInventory(String beerUpc) {
        return failoverFeignClient.getOnhandInventory();
    }
}