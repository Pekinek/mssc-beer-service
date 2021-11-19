package com.mmocek.commons.model.events;

import com.mmocek.commons.model.BeerDto;
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
