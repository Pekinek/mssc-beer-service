package com.mmocek.msscbeerservice.web.mapper;

import com.mmocek.msscbeerservice.domain.Beer;
import com.mmocek.msscbeerservice.services.BeerInventoryService;
import com.mmocek.msscbeerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperOnHandInventoryDecorator implements BeerWithInventoryMapper {
    private BeerInventoryService beerInventoryService;
    private BeerWithInventoryMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerWithInventoryMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        BeerDto dto = mapper.beerToBeerDto(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnhandInventory(beer.getId()));
        return dto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return mapper.beerDtoToBeer(beerDto);
    }
}