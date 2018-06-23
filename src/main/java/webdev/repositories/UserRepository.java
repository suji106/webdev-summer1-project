package webdev.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import webdev.models.User;

public interface UserRepository
	extends CrudRepository<User, Integer> {
	@Query("SELECT id FROM User u WHERE u.email=:email")
	int findUserIdByEmail(
		@Param("email") String email);
	
	@Query("SELECT u FROM User u WHERE u.username=:username")
	Optional<User> findUserByUsername(
		@Param("username") String username);
}