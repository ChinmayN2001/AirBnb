package com.coingshuttle.projects.airbnbApp.strategy;

import com.coingshuttle.projects.airbnbApp.Entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {

    public BigDecimal calculateDynamicPricing(Inventory inventory){

        PricingStrategy pricingStrategy = new BasePricingStrategy();

        //apply the additional strategies
        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy);

        return pricingStrategy.calculatePrice(inventory);
    }
}
