package com.coingshuttle.projects.airbnbApp.Service;

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
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService  inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating a new room in hotel with ID: {}" + hotelId);

        Hotel hotel =  hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with Id: " + hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This owner does not own this hotel with id:" + hotelId);
        }


        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        if(hotel.getActive()){
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting a new room in hotel with ID: {}" + hotelId);
        Hotel hotel =  hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with Id: " + hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This owner does not own this hotel with id:" + hotelId);
        }

        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper
                        .map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting the room with ID: {}" + roomId);
        Room room =  roomRepository
                .findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with Id: " + roomId));

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the room with ID: {}" + roomId);
        Room room =  roomRepository
                .findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with Id: " + roomId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(room.getHotel().getOwner())){
            throw new UnAuthorisedException("This user does not own this room with id:" + roomId);
        }


        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);
    }
}
