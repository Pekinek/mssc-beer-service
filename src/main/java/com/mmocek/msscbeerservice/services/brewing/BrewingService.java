package com.mmocek.msscbeerservice.services.brewing;

import com.mmocek.msscbeerservice.domain.Beer;
import com.mmocek.msscbeerservice.events.BrewBeerEvent;
import com.mmocek.msscbeerservice.repositories.BeerRepository;
import com.mmocek.msscbeerservice.services.BeerInventoryService;
import com.mmocek.msscbeerservice.web.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mmocek.msscbeerservice.config.JmsConfig.BREWING_REQUEST_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate=5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer inventoryOnHond = beerInventoryService.getOnhandInventory(beer.getUpc());

            log.debug("Minimum on hand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + inventoryOnHond);

            if (beer.getMinOnHand() >= inventoryOnHond) {
                jmsTemplate.convertAndSend(BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
