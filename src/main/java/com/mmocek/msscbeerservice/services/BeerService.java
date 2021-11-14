package com.mmocek.msscbeerservice.services;

import com.mmocek.msscbeerservice.domain.Beer;
import com.mmocek.msscbeerservice.repositories.BeerRepository;
import com.mmocek.msscbeerservice.web.controller.NotFoundException;
import com.mmocek.msscbeerservice.web.mapper.BeerMapper;
import com.mmocek.msscbeerservice.web.mapper.BeerWithInventoryMapper;
import com.mmocek.msscbeerservice.web.model.BeerDto;
import com.mmocek.msscbeerservice.web.model.BeerPagedList;
import com.mmocek.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    private final BeerWithInventoryMapper beerWithInventoryMapper;

    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest,
                                   Boolean showInventoryOnHand) {
        Page<Beer> beerPage = getBeerPage(beerName, beerStyle, pageRequest);
        List<BeerDto> beerDtos = mapBeersToDto(beerPage.getContent(), showInventoryOnHand);
        return new BeerPagedList(beerDtos,
                PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());
    }

    private Page<Beer> getBeerPage(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {

        if (StringUtils.hasText(beerName) && !ObjectUtils.isEmpty(beerStyle)) {
            return beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (StringUtils.hasText(beerName)) {
            return beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (!ObjectUtils.isEmpty(beerStyle)) {
            return beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            return beerRepository.findAll(pageRequest);
        }
    }

    private List<BeerDto> mapBeersToDto(List<Beer> beerList, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return beerList.stream().map(beerWithInventoryMapper::beerToBeerDto).collect(Collectors.toList());
        }
        return beerList.stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList());
    }

    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return beerWithInventoryMapper.beerToBeerDto(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        }
        return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    public BeerDto save(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    public BeerDto update(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }
}
