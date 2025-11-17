package com.coingshuttle.projects.airbnbApp.Service;

import com.coingshuttle.projects.airbnbApp.Dto.BookingDto;
import com.coingshuttle.projects.airbnbApp.Dto.BookingRequest;
import com.coingshuttle.projects.airbnbApp.Dto.GuestDto;

import java.util.List;

public interface BookingService {

    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
