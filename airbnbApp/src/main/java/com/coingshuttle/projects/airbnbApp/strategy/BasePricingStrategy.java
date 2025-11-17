package com.coingshuttle.projects.airbnbApp.strategy;

import com.coingshuttle.projects.airbnbApp.Entity.Inventory;
import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy{
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
