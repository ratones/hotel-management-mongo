package org.fasttrackit.hotel.management.repository;

import org.fasttrackit.hotel.management.model.RoomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoomRepository extends MongoRepository<RoomEntity, String> {

}
