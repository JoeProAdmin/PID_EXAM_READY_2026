package be.iccbxl.pid.model;

import org.springframework.data.repository.CrudRepository;

public interface LocalityRepository extends CrudRepository<Locality, Long> {
	Locality findByPostalCode(String postalCode);
	Locality findByLocality(String locality);
}
