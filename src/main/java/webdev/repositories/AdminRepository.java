package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Admin;

public interface AdminRepository
	extends CrudRepository<Admin, Integer> {
	
}