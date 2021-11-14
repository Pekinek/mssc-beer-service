package com.mmocek.msscbeerservice.web.controller;


import com.mmocek.msscbeerservice.services.BeerService;
import com.mmocek.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beerUpc")
public class BeerUpcController {

    private final BeerService beerService;

    @GetMapping("/{beerUpc}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable String beerUpc,
                                               @RequestParam(value = "showInventoryOnHand", required = false,
                                                       defaultValue = "false") Boolean showInventoryOnHand) {

        BeerDto beerDto = beerService.getByUpc(beerUpc, showInventoryOnHand);
        return new ResponseEntity<>(beerDto, HttpStatus.OK);
    }
}
