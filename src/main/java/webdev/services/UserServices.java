package webdev.services;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.User;
import webdev.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials="true")
public class UserServices {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/users")
	public Iterable<User> findAllUsers() {
		return userRepository.findAll(); 
	}

	@GetMapping("/api/user/{userId}")
	public User getUser(@PathVariable("userId") int userId, HttpSession session) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}
		return null;
	}
	
	@GetMapping("/api/user")
	public User getUser(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}
		return null;
	}
	
	@DeleteMapping("/api/user/{userId}")
	public void deleteUserWithUserId(@PathVariable("userId") int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			userRepository.deleteById(optionalUser.get().getId());
		}
	}
	
	@DeleteMapping("/api/user")
	public void deleteUser(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
		if(optionalUser.isPresent()) {
			userRepository.deleteById(currentUser.getId());
		}
	}

	@GetMapping("/api/profile/{email}")
	public User findUserByUsername(@PathVariable("email") String email) {
		Optional<User> data = userRepository.findUserByUsername(email);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}

	@PostMapping("/api/user/signup")
	public User signUpUser(@RequestBody User user, HttpSession session) {
		Optional<User> optionalUser = userRepository.findById(user.getId());
		if(!optionalUser.isPresent()) {
			return userRepository.save(user);
		}
		return null;
	}

	@PostMapping("/api/user/normalLogin")
	public User normalLoginUser(@RequestBody String body, HttpSession session) throws JSONException {
		JSONObject bodyObject = new JSONObject(body);
		String userType = (String) bodyObject.get("userType");
		session.setAttribute("userType", userType);
		String email = (String) bodyObject.get("email");
		String password = (String) bodyObject.get("password");
		Optional<Integer> optionalId = userRepository.findUserIdByEmail(email);
		if(optionalId.isPresent()) {
			Optional<User> optionalUser = userRepository.findById(optionalId.get());
			if (optionalUser.get().getPassword().equals(password)) {
				User user = optionalUser.get();
				session.setAttribute("currentUser", user);
				return user;
			}
		}
		return null;
	}

	@PostMapping("/api/user/socialLogin")
	public User socialLoginUser(@RequestBody String body, HttpSession session) throws JSONException {
		JSONObject bodyObject = new JSONObject(body);
		String userType = (String) bodyObject.get("userType");
		session.setAttribute("userType", userType);
		String email = (String) bodyObject.get("email");
		String name = (String) bodyObject.get("name");
		Optional<Integer> optionalId = userRepository.findUserIdByEmail(email);
		if(optionalId.isPresent()) {
			Optional<User> optionalUser = userRepository.findById(optionalId.get());
			User user = optionalUser.get();
			session.setAttribute("currentUser", user);
			return user;
		}
		else {
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setName(name);
			newUser.setCreated(new Date());
			userRepository.save(newUser);
			session.setAttribute("currentUser", newUser);
			return newUser;
		}
	}
	
	@PostMapping("/api/user/admin")
	public User userCreationByAdmin(@RequestBody User user) throws JSONException {
		
		Optional<User> optionalUser = userRepository.findById(user.getId());
		if (!optionalUser.isPresent()) {
			return userRepository.save(user);
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
	
	@PutMapping("/api/user/update/{userId}")
	public User updateUser(@RequestBody User currentUser, @PathVariable("userId") int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			return userRepository.save(currentUser);
		}
		return null;
	}
	
	@GetMapping("/api/user/logout")
	public void logout(HttpSession session) {
		if (session.getId() != null)
			session.invalidate();
	}
	
	@GetMapping("/api/userType")
	public ResponseEntity<String> userType(HttpSession session) {
		JSONObject bodyObject = new JSONObject("{}");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (session.getAttribute("userType") == null) {
			bodyObject.put("userType", "None");
			return new ResponseEntity<String>(bodyObject.toString(), headers, HttpStatus.ACCEPTED);
		}
		else {
			bodyObject.put("userType", (String) session.getAttribute("userType"));
			return new ResponseEntity<String>(bodyObject.toString(), headers, HttpStatus.ACCEPTED);
		}
	}
}
