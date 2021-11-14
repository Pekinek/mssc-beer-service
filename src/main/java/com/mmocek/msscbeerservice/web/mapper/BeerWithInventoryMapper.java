package com.mmocek.msscbeerservice.web.mapper;

import com.mmocek.msscbeerservice.domain.Beer;
import com.mmocek.msscbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperOnHandInventoryDecorator.class)
public interface BeerWithInventoryMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
