package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "The room name must not be empty.")
    @Size(min = 2, max = 60, message = "The room name must be between 2 and 60 characters long.")
    private String name;

    @Min(value = 1, message = "The number of seats must be greater than 0.")
    private int seats;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @OneToMany(targetEntity = Representation.class, mappedBy = "room")
    private List<Representation> representations = new ArrayList<>();

    protected Room() {
    }

    public Room(String name, int seats, Location location) {
        this.name = name;
        this.seats = seats;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Representation> getRepresentations() {
        return representations;
    }

    public Room addRepresentation(Representation representation) {
        if (!this.representations.contains(representation)) {
            this.representations.add(representation);
            representation.setRoom(this);
        }

        return this;
    }

    public Room removeRepresentation(Representation representation) {
        if (this.representations.contains(representation)) {
            this.representations.remove(representation);
            if (representation.getRoom() != null && representation.getRoom().equals(this)) {
                representation.setRoom(null);
            }
        }

        return this;
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", name=" + name + ", seats=" + seats + ", location=" + location + "]";
    }
}