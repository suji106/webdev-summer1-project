package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Request;

public interface RequestRepository
	extends CrudRepository<Request, Integer> {
	
}