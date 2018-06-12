package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.User;

public interface UserRepository
	extends CrudRepository<User, Integer> {
	
}