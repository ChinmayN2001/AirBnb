package com.coingshuttle.projects.airbnbApp.Controller;

import com.coingshuttle.projects.airbnbApp.Dto.HotelDto;
import com.coingshuttle.projects.airbnbApp.Dto.HotelInfoDto;
import com.coingshuttle.projects.airbnbApp.Dto.HotelPriceDto;
import com.coingshuttle.projects.airbnbApp.Dto.HotelSearchRequest;
import com.coingshuttle.projects.airbnbApp.Service.HotelService;
import com.coingshuttle.projects.airbnbApp.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){

       var page =  inventoryService.searchHotels(hotelSearchRequest);
       return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){

        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
