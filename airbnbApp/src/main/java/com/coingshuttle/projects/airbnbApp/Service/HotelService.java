package com.coingshuttle.projects.airbnbApp.Service;

import com.coingshuttle.projects.airbnbApp.Dto.HotelDto;
import com.coingshuttle.projects.airbnbApp.Dto.HotelInfoDto;

public interface HotelService {


    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long hotelId);

    HotelInfoDto getHotelInfoById(Long hotelId);
}
