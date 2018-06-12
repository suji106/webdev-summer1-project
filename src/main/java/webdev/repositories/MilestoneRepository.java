package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Milestone;

public interface MilestoneRepository
	extends CrudRepository<Milestone, Integer> {
	
}