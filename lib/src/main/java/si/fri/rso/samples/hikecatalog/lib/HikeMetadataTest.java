package si.fri.rso.samples.hikecatalog.lib;

import java.time.Instant;

public class HikeMetadataTest {

    private Integer hikeId;
    private String name; // Added a name field
    private Double length; // in kilometers
    private Double duration; // in hours
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
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
