package org.fasttrackit.hotel.management.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("cleaningOperations")
@Getter
@Setter
@AllArgsConstructor
@With
public class CleanOperationsEntity {
    @Id
    private String id;
    private LocalDate date;
    private List<CleaningProcedureEntity> procedures;
    private String roomId;
}
