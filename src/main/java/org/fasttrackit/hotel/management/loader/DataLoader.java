package org.fasttrackit.hotel.management.loader;

import lombok.RequiredArgsConstructor;
import org.fasttrackit.hotel.management.config.AppConfiguration;
import org.fasttrackit.hotel.management.model.*;
import org.fasttrackit.hotel.management.repository.CleanOperationsRepository;
import org.fasttrackit.hotel.management.repository.CleaningProcedureRepository;
import org.fasttrackit.hotel.management.repository.ReviewRepository;
import org.fasttrackit.hotel.management.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final Map<String, Integer> messages;
    private final RoomRepository roomRepo;
    private final CleaningProcedureRepository cleaningProcedureRepository;
    private final CleanOperationsRepository cleanOperationsRepository;
    private final ReviewRepository reviewRepository;

    private final AppConfiguration config;

    private List<CleaningProcedureEntity> procedures;

    @Override
    public void run(String... args) throws Exception {
       boolean dataGenerated =  roomRepo.count() > 0;
       if(!dataGenerated){
           generateData();
       }
    }

    private void generateData() {
        System.out.println(config.getHotelName());
        this.messages.put("Clean room", 5);
        this.messages.put("Great view", 5);
        this.messages.put("Very noisy", 3);
        this.messages.put("No fridge", 2);

        this.procedures = createCleanProcedures();
        // create rooms
        IntStream.range(0, config.getNoOfFloors()).forEach(this::createRooms);
    }

    private List<CleaningProcedureEntity> createCleanProcedures() {
        return List.of(
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Clean Bathroom", ProcedureOutcome.DONE),
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Clean Bedroom", ProcedureOutcome.DONE),
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Change sheets", ProcedureOutcome.DONE),
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Change towels", ProcedureOutcome.DONE),
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Clean Bathroom", ProcedureOutcome.STARTED),
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Clean Bedroom", ProcedureOutcome.STARTED),
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Change sheets", ProcedureOutcome.STARTED),
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Change towels", ProcedureOutcome.STARTED),
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Clean Bathroom", ProcedureOutcome.INPROGRESS),
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Clean Bedroom", ProcedureOutcome.INPROGRESS),
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Change sheets", ProcedureOutcome.INPROGRESS),
                new CleaningProcedureEntity(UUID.randomUUID().toString(), "Change towels", ProcedureOutcome.INPROGRESS)
        );
    }

    private void createRooms(int i) {
        var rooms = roomRepo.saveAll(IntStream.range(1, 10)
                .mapToObj(index -> RoomEntity
                        .builder()
                        .id(UUID.randomUUID().toString())
                        .facilities(new RoomFacilityEntity(UUID.randomUUID().toString(), index % 2 == 0, index % 4 == 0))
                        .floor(i+1)
                        .hotelName(config.getHotelName())
                        .number("Room no. %s0%s".formatted(i+1, index))
                        .build())
                .toList());
        rooms.forEach(room -> {
            generateReviews(room);
            generateCleanupOperations(room);
        });
    }

    private void generateReviews(RoomEntity roomEntity) {
        int randSize = new Random().nextInt(1, 5);
        IntStream.range(1, randSize).forEach(
                index -> {
                    var reviewData = messages.entrySet().stream().toList().get(new Random().nextInt(0, messages.size() - 1));
                    reviewRepository.save(new RoomReviewEntity(UUID.randomUUID().toString(),reviewData.getKey(), reviewData.getValue(), "Guest %s".formatted(index), roomEntity.getId()));
                }
        );
    }

    //TODO: Refactor more randon procedures
    private void generateCleanupOperations(RoomEntity room) {
        int startSublist = new Random().nextInt(0, procedures.size() - 1);
        int endSublist = procedures.size() - 1;
        cleanOperationsRepository.saveAll(IntStream.range(1, startSublist).mapToObj(
                (index -> new CleanOperationsEntity(UUID.randomUUID().toString(),
                        LocalDate.now().minusDays(new Random().nextInt(365)),
                        procedures.subList(new Random().nextInt(0, procedures.size() - 1), endSublist), room.getId()))
        ).toList());
    }
}
