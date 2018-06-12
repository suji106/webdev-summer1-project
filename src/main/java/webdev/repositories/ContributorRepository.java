package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Contributor;

public interface ContributorRepository
	extends CrudRepository<Contributor, Integer> {
	
}