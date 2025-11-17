package com.coingshuttle.projects.airbnbApp.strategy;

import com.coingshuttle.projects.airbnbApp.Entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
