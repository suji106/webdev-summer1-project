package webdev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.User;
import webdev.repositories.UserRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserServices {
	@Autowired
	UserRepository userRepository;
	
	private String loginType;
	
	@GetMapping("/api/users")
	public Iterable<User> findAllUsers() {
		return userRepository.findAll(); 
	}
	
	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			userRepository.deleteById(userId);
		}
	}
	
	@PostMapping("/api/login")
	public void setLoginType(@RequestBody String loginType) {
		this.loginType = loginType;
	}
	
	public String getLoginType() {
		return this.loginType;
	}
}