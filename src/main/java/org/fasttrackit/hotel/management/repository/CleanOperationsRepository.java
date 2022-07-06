package org.fasttrackit.hotel.management.repository;

import org.fasttrackit.hotel.management.model.CleanOperationsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CleanOperationsRepository extends MongoRepository<CleanOperationsEntity, String> {
    List<CleanOperationsEntity> findByRoomId(String roomId);
}
