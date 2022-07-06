package org.fasttrackit.hotel.management.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.hotel.management.exceptions.ResourceNotFoundException;
import org.fasttrackit.hotel.management.model.*;
import org.fasttrackit.hotel.management.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    public List<RoomReviewEntity> getRoomReviews(String roomId) {
        return repository.findByRoomId(roomId);
    }

    public Optional<RoomReviewEntity> insertRoomReview(String roomId, RoomReviewEntity roomReview) {
        return Optional.of(repository.save(new RoomReviewEntity(
                UUID.randomUUID().toString(),
                roomReview.getMessage(),
                roomReview.getRating(),
                roomReview.getGuest(),
                roomId
        )));
    }

    public RoomReviewEntity updateRoomReview(JsonPatch jsonPatch, String id) {
        return repository.findById(id)
                .map(review -> (RoomReviewEntity)Utils.applyPatch(review, jsonPatch))
                .map(review -> applyChanges(id, review))
                .orElseThrow(() -> new ResourceNotFoundException("Could not find review with id %s".formatted(id)));
    }

    private RoomReviewEntity applyChanges(String id, RoomReviewEntity review) {
        RoomReviewEntity reviewEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find room with id %s".formatted(id)));
        return repository.save(reviewEntity
                .withMessage(review.getMessage())
                .withGuest(review.getGuest())
                .withRating(review.getRating())
        );
    }

    public Optional<RoomReviewEntity> deleteRoomReview(String id) {
        var review = repository.findById(id);
        review.ifPresent(repository::delete);
        return review;
    }
}
