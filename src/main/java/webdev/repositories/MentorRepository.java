package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Mentor;

public interface MentorRepository
	extends CrudRepository<Mentor, Integer> {
	
}