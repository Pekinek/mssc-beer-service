package com.mmocek.msscbeerservice.services.ordervalidation;

import com.mmocek.commons.model.BeerOrderDto;
import com.mmocek.commons.model.BeerOrderLineDto;
import com.mmocek.commons.model.ValidateBeerOrderRequest;
import com.mmocek.commons.model.ValidationResult;
import com.mmocek.msscbeerservice.config.JmsConfig;
import com.mmocek.msscbeerservice.domain.Beer;
import com.mmocek.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidateBeerOrderListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.VALIDATE_ORDER)
    public void listen(ValidateBeerOrderRequest request){
        BeerOrderDto beerOrderDto = request.getBeerOrderDto();
        Set<String> beerUpcs = beerOrderDto.getBeerOrderLines()
                                           .stream()
                                           .map(BeerOrderLineDto::getUpc)
                                           .collect(Collectors.toSet());
        List<Beer> beers = beerRepository.findByUpcIn(beerUpcs);

        ValidationResult result = new ValidationResult(beerOrderDto.getId(), false);
        if(beers.size() == beerUpcs.size()){
            result.setValid(true);
        }

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT, result);
    }
}
