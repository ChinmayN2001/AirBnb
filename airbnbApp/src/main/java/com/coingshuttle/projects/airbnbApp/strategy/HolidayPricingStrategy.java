package com.coingshuttle.projects.airbnbApp.strategy;

import com.coingshuttle.projects.airbnbApp.Entity.Inventory;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        boolean isTodayHoliday = true;  //call an API or check with local data

        if(isTodayHoliday)
        {
            price = price.multiply(BigDecimal.valueOf(1.25));
        }

        return price;
    }
}
