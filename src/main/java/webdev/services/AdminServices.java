package webdev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Admin;
import webdev.repositories.AdminRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials="true")
public class AdminServices {
	@Autowired
	AdminRepository adminRepository;
	
	@GetMapping("/api/admins")
	public Iterable<Admin> findAllAdmins() {
		return adminRepository.findAll(); 
	}
	
	@DeleteMapping("/api/admin/{adminId}")
	public void deleteAdmin(@PathVariable("adminId") int adminId) {
		Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
		if(optionalAdmin.isPresent()) {
			adminRepository.deleteById(adminId);
		}
	}
	
	@PostMapping("/api/admin")
	public Admin addAdmin(@RequestBody Admin admin) {
		Optional<Admin> optionalAdmin = adminRepository.findById(admin.getId());
		if(!optionalAdmin.isPresent()) {
			return adminRepository.save(admin);
		}
		return null;
	}
	
	@PutMapping("/api/admin")
	public Admin updateAdmin(@RequestBody Admin admin) {
		Optional<Admin> optionalAdmin = adminRepository.findById(admin.getId());
		if(optionalAdmin.isPresent()) {
			return adminRepository.save(admin);
		}
		return null;
	}
}