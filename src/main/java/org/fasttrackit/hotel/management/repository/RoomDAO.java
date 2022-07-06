package org.fasttrackit.hotel.management.repository;

import lombok.RequiredArgsConstructor;
import org.fasttrackit.hotel.management.model.RoomEntity;
import org.fasttrackit.hotel.management.model.RoomFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class RoomDAO {
    private final MongoTemplate mongo;

    public Page<RoomEntity> findRooms(RoomFilter filter, Pageable pageable) {
        Criteria criteria = new Criteria();
        ofNullable(filter.name())
                .ifPresent(name ->criteria.and("number").regex(".*%s.*".formatted(filter.name())));
        ofNullable(filter.hotel())
                .ifPresent(hotel -> criteria.and("hotelName").regex(".*%s.*".formatted(filter.hotel())));
        ofNullable(filter.floor())
                .ifPresent(floor->criteria.and("floor").is(filter.floor()));
        ofNullable(filter.tv())
                .ifPresent(hasTv->criteria.and("facilities.hasTV").is(true));
        ofNullable(filter.doubleBed())
                .ifPresent(hasDoubleBed->criteria.and("facilities.hasDoubleBed").is(true));
        Query query = Query.query(criteria);
        query.with(pageable);
        List<RoomEntity> rooms = mongo.find(
                query,
                RoomEntity.class);
        return PageableExecutionUtils.getPage(
                rooms,
                pageable,
                () -> mongo.count(Query.query(criteria), RoomEntity.class));
    }
}
