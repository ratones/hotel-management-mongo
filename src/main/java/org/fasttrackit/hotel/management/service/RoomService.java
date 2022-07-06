package org.fasttrackit.hotel.management.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.hotel.management.exceptions.ResourceNotFoundException;
import org.fasttrackit.hotel.management.model.RoomEntity;
import org.fasttrackit.hotel.management.model.RoomFacilityEntity;
import org.fasttrackit.hotel.management.model.RoomFilter;
import org.fasttrackit.hotel.management.repository.CleanOperationsRepository;
import org.fasttrackit.hotel.management.repository.ReviewRepository;
import org.fasttrackit.hotel.management.repository.RoomDAO;
import org.fasttrackit.hotel.management.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository repository;
    private final CleanOperationsRepository cleanOperationsRepository;
    private final ReviewRepository reviewRepository;
    private final RoomDAO dao;
    public Page<RoomEntity> getRooms(RoomFilter filter, Pageable pageable) {
        System.out.println(filter.toString());
        return dao.findRooms(filter, pageable);
    }

    public Optional<RoomEntity> getRoom(String id) {
        return repository.findById(id);
    }

    public Optional<RoomEntity> deleteRoom(String id) {
        var room = repository.findById(id);
        room.ifPresent(repository::delete);
        cleanOperationsRepository.findByRoomId(id).forEach(cleanOperationsRepository::delete);
        reviewRepository.findByRoomId(id).forEach(reviewRepository::delete);
        return room;
    }

    public RoomEntity updateRoom(String id, JsonPatch jsonPatch) {
        return repository.findById(id)
                .map(room -> (RoomEntity)Utils.applyPatch(room, jsonPatch))
                .map(room -> applyChanges(id, room))
                .orElseThrow(() -> new ResourceNotFoundException("Could not find room with id %s".formatted(id)));
    }

    private RoomEntity applyChanges(String id, RoomEntity room) {
        RoomEntity roomEntity = getRoom(id).orElseThrow(() -> new ResourceNotFoundException("Could not find room with id %s".formatted(id)));
        RoomFacilityEntity facilities = roomEntity.getFacilities();
        return repository.save(roomEntity
                .withFloor(room.getFloor())
                .withHotelName(room.getHotelName())
                .withNumber(room.getNumber())
                .withFacilities(facilities
                        .withHasTV(room.getFacilities().isHasTV())
                        .withHasDoubleBed(room.getFacilities().isHasDoubleBed())
                )
        );
    }
}
