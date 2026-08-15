package be.iccbxl.pid.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RepresentationService {

	private final RepresentationRepository repository;

	public RepresentationService(RepresentationRepository repository) {
		this.repository = repository;
	}

	public List<Representation> getAll() {

		List<Representation> representations = new ArrayList<>();

		repository.findAll().forEach(representations::add);

		return representations;
	}

	public Representation get(String id) {

		Long indice = (long) Integer.parseInt(id);

		Optional<Representation> representation =
				repository.findById(indice);

		return representation.isPresent()
				? representation.get()
				: null;
	}

	public void add(Representation representation) {
		repository.save(representation);
	}

	public void update(String id, Representation representation) {
		repository.save(representation);
	}

	public void delete(String id) {

		Long indice = (long) Integer.parseInt(id);

		repository.deleteById(indice);
	}

	public List<Representation> getFromRoom(Room room) {
		return repository.findByRoom(room);
	}

	public List<Representation> getFromShow(Show show) {
		return repository.findByShow(show);
	}

	public boolean isRoomOccupied(Room room, LocalDateTime when) {
		return repository.existsByRoomAndWhen(room, when);
	}
}
