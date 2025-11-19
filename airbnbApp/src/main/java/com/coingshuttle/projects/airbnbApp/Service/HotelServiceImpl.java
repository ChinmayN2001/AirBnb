package com.coingshuttle.projects.airbnbApp.Service;

import com.coingshuttle.projects.airbnbApp.Dto.HotelDto;
import com.coingshuttle.projects.airbnbApp.Dto.HotelInfoDto;
import com.coingshuttle.projects.airbnbApp.Dto.RoomDto;
import com.coingshuttle.projects.airbnbApp.Entity.Hotel;
import com.coingshuttle.projects.airbnbApp.Entity.Room;
import com.coingshuttle.projects.airbnbApp.Entity.User;
import com.coingshuttle.projects.airbnbApp.Exception.ResourceNotFoundException;
import com.coingshuttle.projects.airbnbApp.Exception.UnAuthorisedException;
import com.coingshuttle.projects.airbnbApp.repository.HotelRepository;
import com.coingshuttle.projects.airbnbApp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name : {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        hotel.setOwner(user);

        hotel = hotelRepository.save(hotel);
        log.info("Creating a new hotel with ID : {}", hotelDto.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with Id : {}", id);
        Hotel hotel =  hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with Id: " + id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This owner does not own this hotel with id:" + id);
        }

        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with Id : {}", id);
        Hotel hotel =  hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with Id: " + id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This owner does not own this hotel with id:" + id);
        }


        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel =  hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with Id: " + id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This owner does not own this hotel with id:" + id);
        }


        for(Room room: hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

    @Override
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with Id : {}", hotelId);
        Hotel hotel =  hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with Id: " + hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This owner does not own this hotel with id:" + hotelId);
        }


        hotel.setActive(true);

        //assuming only do it once
        for(Room room: hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }
    }

    //    public method
    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {

        Hotel hotel =  hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with Id: " + hotelId));

        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }


}
