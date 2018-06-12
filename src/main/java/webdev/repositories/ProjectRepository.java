package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Project;

public interface ProjectRepository
	extends CrudRepository<Project, Integer> {
	
}