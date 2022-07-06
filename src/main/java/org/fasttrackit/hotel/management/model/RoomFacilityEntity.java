package org.fasttrackit.hotel.management.model;

import lombok.*;

@AllArgsConstructor
@With
@Getter
@NoArgsConstructor
public class RoomFacilityEntity {
    private String id;
    private boolean hasTV;
    private boolean hasDoubleBed;
}
