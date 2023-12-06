package si.fri.rso.samples.hikecatalog.models.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "hike_metadata")
@NamedQueries(value =
        {
                @NamedQuery(name = "HikeEntity.getAll",
                        query = "SELECT h FROM HikeEntity h")
        })
public class HikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "length")
    private Double length; // in kilometers

    @Column(name = "difficulty")
    private Integer difficulty; // scale from 1 to 10

    @Column(name = "starting_latitude")
    private Double startingLatitude;

    @Column(name = "starting_longitude")
    private Double startingLongitude;

    @Column(name = "created")
    private Instant created;

    // Getters and setters for all fields

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
