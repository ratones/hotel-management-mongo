package org.fasttrackit.hotel.management.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rooms")
@Builder
@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity {
    @Id
    private String id;
    private String number;
    private int floor;
    private String hotelName;

    private RoomFacilityEntity facilities;
}
