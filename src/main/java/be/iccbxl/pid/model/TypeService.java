package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeService {
	@Autowired
	private TypeRepository repository;
	
	public List<Type> getAll() {
		List<Type> types = new ArrayList<>();
		
		repository.findAll().forEach(types::add);
		
		return types;
	}
	
	public Type get(String id) {
		Long indice = (long) Integer.parseInt(id);
		
		Optional<Type> type = repository.findById(indice);
		
		return type.isPresent() ? type.get() : null; 

	}

	public void add(Type type) {
		repository.save(type);
	}

	public void update(String id, Type type) {
		repository.save(type);
	}

	public void delete(String id) {
		Long indice = (long) Integer.parseInt(id);
		
		repository.deleteById(indice);
	}
}
