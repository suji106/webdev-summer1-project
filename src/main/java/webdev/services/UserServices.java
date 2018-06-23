package webdev.services;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.User;
import webdev.repositories.UserRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserServices {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/api/users")
	public Iterable<User> findAllUsers() {
		return userRepository.findAll(); 
	}
	
	@DeleteMapping("/api/user")
	public void deleteUser(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
		if(optionalUser.isPresent()) {
			userRepository.deleteById(currentUser.getId());
		}
	}
	
	@PostMapping("/api/user/signup")
	public User signUpUser(@RequestBody User user, HttpSession session) throws JSONException {
		Optional<User> optionalUser = userRepository.findById(user.getId());
		if(!optionalUser.isPresent()) {
			return userRepository.save(user);
		}
		return null;
	}
	
	@PostMapping("/api/user/login")
	public User loginUser(@RequestBody String body, HttpSession session) throws JSONException {
		JSONObject bodyObject = new JSONObject(body);
		String userType = (String) bodyObject.get("userType");
		session.setAttribute("currentUser", userType);
		String email = (String) bodyObject.get("email");
		int id = userRepository.findUserIdByEmail(email);
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			session.setAttribute("currentUser", user);
			return user;
		}
		return null;
	}
	
	@PutMapping("/api/user/update")
	public User updateUser(@RequestBody User currentUser, HttpSession session) throws JSONException {
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
		if(optionalUser.isPresent()) {
			return userRepository.save(currentUser);
		}
		return null;
	}
}
