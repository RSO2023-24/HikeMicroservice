package si.fri.rso.samples.hikecatalog.models.converters;

import si.fri.rso.samples.hikecatalog.lib.HikeMetadata;
import si.fri.rso.samples.hikecatalog.models.entities.HikeEntity;

import java.util.stream.Collectors;

public class HikeConverter {

    public static HikeMetadata toDto(HikeEntity entity) {
        HikeMetadata dto = new HikeMetadata();
        dto.setHikeId(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setLength(entity.getLength());
        dto.setDifficulty(entity.getDifficulty());
        dto.setStartingLatitude(entity.getStartingLatitude());
        dto.setStartingLongitude(entity.getStartingLongitude());

        return dto;
    }

    public static HikeEntity toEntity(HikeMetadata dto) {
        HikeEntity entity = new HikeEntity();
        entity.setCreated(dto.getCreated());
        entity.setLength(dto.getLength());
        entity.setDifficulty(dto.getDifficulty());
        entity.setStartingLatitude(dto.getStartingLatitude());
        entity.setStartingLongitude(dto.getStartingLongitude());

        return entity;
    }
}
