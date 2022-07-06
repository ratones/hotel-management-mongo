package org.fasttrackit.hotel.management.repository;

import org.fasttrackit.hotel.management.model.CleaningProcedureEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CleaningProcedureRepository extends MongoRepository<CleaningProcedureEntity, Integer> {
}
