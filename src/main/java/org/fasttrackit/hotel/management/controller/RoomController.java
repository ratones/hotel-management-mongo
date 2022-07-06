package org.fasttrackit.hotel.management.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.hotel.management.exceptions.BadRequestException;
import org.fasttrackit.hotel.management.exceptions.ResourceNotFoundException;
import org.fasttrackit.hotel.management.model.CleanOperationsEntity;
import org.fasttrackit.hotel.management.model.RoomEntity;
import org.fasttrackit.hotel.management.model.RoomFilter;
import org.fasttrackit.hotel.management.model.RoomReviewEntity;
import org.fasttrackit.hotel.management.service.CleanupService;
import org.fasttrackit.hotel.management.service.ReviewService;
import org.fasttrackit.hotel.management.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;
    private final ReviewService reviewService;

    private final CleanupService cleanupService;

    @GetMapping
    Page<RoomEntity> getRooms(RoomFilter filter, Pageable pageable) {
        return service.getRooms(filter, pageable);
    }

    @GetMapping("{id}")
    RoomEntity getById(@PathVariable String id){
        return service.getRoom(id)
                .orElseThrow(() ->new ResourceNotFoundException("Could not find room with id " + id));
    }

    @DeleteMapping("{id}")
    RoomEntity deleteRoom(@PathVariable String id){
        return service.deleteRoom(id)
                .orElseThrow(() ->new ResourceNotFoundException("Could not find room with id " + id));
    }

    @PatchMapping("{id}")
    RoomEntity updateRoom(@PathVariable String id, @RequestBody JsonPatch jsonPatch){
        return service.updateRoom(id, jsonPatch);
    }

    @GetMapping("{id}/reviews")
    List<RoomReviewEntity> getRoomReviews(@PathVariable String id){
        return reviewService.getRoomReviews(id);
    }

    @PostMapping("{id}/reviews")
    RoomReviewEntity insertRoomReview(@PathVariable String id, @RequestBody RoomReviewEntity roomReview){
        return reviewService.insertRoomReview(id,roomReview).orElseThrow(() -> new BadRequestException("Could not add review to the room with id " + id));
    }

    @GetMapping("{id}/cleanups")
    List<CleanOperationsEntity> getRoomCleanups(@PathVariable String id){
        return cleanupService.getRoomCleanups(id);
    }

    @PostMapping("{id}/cleanups")
    CleanOperationsEntity insertCleanOperation(@PathVariable String id,@RequestBody CleanOperationsEntity cleanOperation){
        return cleanupService.insertCleanOperation(id,cleanOperation).orElseThrow(() -> new BadRequestException("Could not add clean operation to the room with id " + id));
    }

}
