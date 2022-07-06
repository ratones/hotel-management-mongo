package org.fasttrackit.hotel.management.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.hotel.management.exceptions.BadRequestException;
import org.fasttrackit.hotel.management.exceptions.ResourceNotFoundException;
import org.fasttrackit.hotel.management.model.RoomReviewEntity;
import org.fasttrackit.hotel.management.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    // Mutate in RoomController
//    @GetMapping
//    List<RoomReviewEntity> getRoomReviews(@PathVariable String roomId){
//        return service.getRoomReviews(roomId);
//    }
//
//    @PostMapping
//    RoomReviewEntity insertRoomReview(@PathVariable String roomId, @RequestBody RoomReviewEntity roomReview){
//        return service.insertRoomReview(roomId,roomReview).orElseThrow(() -> new BadRequestException("Could not add clean operation to the room with id " + roomId));
//    }

    @PatchMapping("{id}")
    RoomReviewEntity updateRoomReview(@PathVariable String id, @RequestBody JsonPatch jsonPatch){
        return service.updateRoomReview(jsonPatch, id);
    }

    @DeleteMapping("{id}")
    RoomReviewEntity deleteRoomReview(@PathVariable String id){
        return service.deleteRoomReview(id)
                .orElseThrow(() ->new ResourceNotFoundException("Could not find record with id " + id));
    }
}
