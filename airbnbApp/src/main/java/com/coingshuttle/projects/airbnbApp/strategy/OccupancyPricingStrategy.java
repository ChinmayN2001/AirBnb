package com.coingshuttle.projects.airbnbApp.strategy;

import com.coingshuttle.projects.airbnbApp.Entity.Inventory;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class OccupancyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        double occuppancyRate = (double) inventory.getBookedCount() / inventory.getTotalCount();
        if(occuppancyRate > 0.8){
            price = price.multiply(BigDecimal.valueOf(1.2));
        }
        return price;
    }
}
