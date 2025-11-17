package com.coingshuttle.projects.airbnbApp.Service;

import com.coingshuttle.projects.airbnbApp.Dto.HotelDto;
import com.coingshuttle.projects.airbnbApp.Dto.HotelPriceDto;
import com.coingshuttle.projects.airbnbApp.Dto.HotelSearchRequest;
import com.coingshuttle.projects.airbnbApp.Entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto>  searchHotels(HotelSearchRequest hotelSearchRequest);


}
