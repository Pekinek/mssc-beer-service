package com.mmocek.msscbeerservice.events;

import com.mmocek.msscbeerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 4798503925091927206L;

    private BeerDto beerDto;
}
