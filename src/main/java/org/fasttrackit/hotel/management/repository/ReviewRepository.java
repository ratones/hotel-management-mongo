package org.fasttrackit.hotel.management.repository;

import org.fasttrackit.hotel.management.model.RoomReviewEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<RoomReviewEntity, String> {
    List<RoomReviewEntity> findByRoomId(String id);
}
