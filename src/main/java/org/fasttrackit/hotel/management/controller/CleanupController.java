package org.fasttrackit.hotel.management.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.hotel.management.exceptions.BadRequestException;
import org.fasttrackit.hotel.management.exceptions.ResourceNotFoundException;
import org.fasttrackit.hotel.management.model.CleanOperationsEntity;
import org.fasttrackit.hotel.management.model.CleaningProcedureEntity;
import org.fasttrackit.hotel.management.service.CleanupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cleanups")
@RequiredArgsConstructor
public class CleanupController {
    private final CleanupService service;

    // Mutate in RoomController
//    @GetMapping
//    List<CleanOperationsEntity> getRoomCleanups(@PathVariable String roomId){
//        return service.getRoomCleanups(roomId);
//    }
//
//    @PostMapping
//    CleanOperationsEntity insertCleanOperation(@PathVariable String roomId,@RequestBody CleanOperationsEntity cleanOperation){
//        return service.insertCleanOperation(roomId,cleanOperation).orElseThrow(() -> new BadRequestException("Could not add clean operation to the room with id " + roomId));
//    }

    @PatchMapping("{id}")
    CleanOperationsEntity updateCleanOperation(@PathVariable String id, @RequestBody JsonPatch jsonPatch){
        return service.updateCleanOperation(jsonPatch, id);
    }

    @DeleteMapping("{id}")
    CleanOperationsEntity deleteCleanOperation(@PathVariable String id){
        return service.deleteCleanOperation(id)
                .orElseThrow(() ->new ResourceNotFoundException("Could not find record with id " + id));
    }

}
