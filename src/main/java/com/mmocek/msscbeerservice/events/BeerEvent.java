package com.mmocek.msscbeerservice.events;

import com.mmocek.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 4798503925091927206L;

    private final BeerDto beerDto;
}
