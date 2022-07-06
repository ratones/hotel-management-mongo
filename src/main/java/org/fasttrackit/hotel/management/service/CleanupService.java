package org.fasttrackit.hotel.management.service;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.hotel.management.exceptions.ResourceNotFoundException;
import org.fasttrackit.hotel.management.model.CleanOperationsEntity;
import org.fasttrackit.hotel.management.model.CleaningProcedureEntity;
import org.fasttrackit.hotel.management.repository.CleanOperationsRepository;
import org.fasttrackit.hotel.management.repository.CleaningProcedureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CleanupService {
    private final CleanOperationsRepository repository;
    private final CleaningProcedureRepository cleaningProcedureRepository;

    public List<CleanOperationsEntity> getRoomCleanups(String roomId) {
        return repository.findByRoomId(roomId);
    }


    public Optional<CleanOperationsEntity> insertCleanOperation(String roomId, CleanOperationsEntity cleanOperation) {
        return Optional.of(repository.save(new CleanOperationsEntity(
                UUID.randomUUID().toString(),
                cleanOperation.getDate(),
                cleanOperation.getProcedures().stream().map(
                        procedure -> new CleaningProcedureEntity(
                                UUID.randomUUID().toString(),
                                procedure.getName(),
                                procedure.getOutcome())
                ).toList(),
                roomId
        )));
    }

    public CleanOperationsEntity updateCleanOperation(JsonPatch jsonPatch, String id) {
        return repository.findById(id)
                .map(op -> (CleanOperationsEntity)Utils.applyPatch(op, jsonPatch))
                .map(op -> applyChanges(id, op))
                .orElseThrow(() -> new ResourceNotFoundException("Could not find operation with id %s".formatted(id)));
    }

    private CleanOperationsEntity applyChanges(String id, CleanOperationsEntity op) {
        CleanOperationsEntity operationEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find room with id %s".formatted(id)));
        return repository.save(operationEntity
                .withDate(op.getDate())
                .withProcedures(op.getProcedures())
        );
    }

    public Optional<CleanOperationsEntity> deleteCleanOperation(String id) {
        var operation = repository.findById(id);
        operation.ifPresent(repository::delete);
        return operation;
    }
}
