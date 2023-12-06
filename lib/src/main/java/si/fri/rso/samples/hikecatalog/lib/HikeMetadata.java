package si.fri.rso.samples.hikecatalog.lib;

import java.time.Instant;
import java.util.List;

public class HikeMetadata {

    private Integer hikeId;
    private Double length; // in kilometers
    private Integer difficulty; // scale from 1 to 10
    private Double startingLatitude;
    private Double startingLongitude;
    private Instant created;

    // Getters and setters for all fields

    public Integer getHikeId() {
        return hikeId;
    }

    public void setHikeId(Integer hikeId) {
        this.hikeId = hikeId;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Double getStartingLatitude() {
        return startingLatitude;
    }

    public void setStartingLatitude(Double startingLatitude) {
        this.startingLatitude = startingLatitude;
    }

    public Double getStartingLongitude() {
        return startingLongitude;
    }

    public void setStartingLongitude(Double startingLongitude) {
        this.startingLongitude = startingLongitude;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
