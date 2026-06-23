package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> getAll() {
        List<Room> rooms = new ArrayList<>();
        repository.findAll().forEach(rooms::add);
        return rooms;
    }

    public Room get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Room add(Room room) {
        return repository.save(room);
    }

    public Room update(Room room) {
        return repository.save(room);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Room> getByLocation(Location location) {
        return repository.findByLocation(location);
    }
}