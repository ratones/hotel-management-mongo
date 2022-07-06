package org.fasttrackit.hotel.management.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class RoomReviewEntity {
    @Id
    private String id;
    private String message;
    private int rating;
    private String guest;
    private String roomId;

}
