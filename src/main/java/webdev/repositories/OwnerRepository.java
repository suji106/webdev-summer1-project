package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Owner;

public interface OwnerRepository
	extends CrudRepository<Owner, Integer> {
	
}