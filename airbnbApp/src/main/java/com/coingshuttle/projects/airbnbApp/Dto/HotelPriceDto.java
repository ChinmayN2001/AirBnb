package com.coingshuttle.projects.airbnbApp.Dto;

import com.coingshuttle.projects.airbnbApp.Entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceDto {

    private Hotel hotel;
    private Double price;

}
