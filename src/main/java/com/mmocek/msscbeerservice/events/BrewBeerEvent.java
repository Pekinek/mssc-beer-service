package com.mmocek.msscbeerservice.events;

import com.mmocek.msscbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent {


    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
