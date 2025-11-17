package com.coingshuttle.projects.airbnbApp.Controller;

import com.coingshuttle.projects.airbnbApp.Dto.BookingDto;
import com.coingshuttle.projects.airbnbApp.Dto.BookingRequest;
import com.coingshuttle.projects.airbnbApp.Dto.GuestDto;
import com.coingshuttle.projects.airbnbApp.Service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest){

        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId,
                                                @RequestBody List<GuestDto> guestDtoList){
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
    }

}
