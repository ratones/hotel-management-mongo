package org.fasttrackit.hotel.management.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.fasttrackit.hotel.management.model.RoomReviewEntity;

public class Utils {
    public static <T> T applyPatch(Object dbEntity, JsonPatch jsonPatch) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.findAndRegisterModules();
            JsonNode jsonNode = jsonMapper.convertValue(dbEntity, JsonNode.class);
            JsonNode patchedJson = jsonPatch.apply(jsonNode);
            return (T)jsonMapper.treeToValue(patchedJson,dbEntity.getClass());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
