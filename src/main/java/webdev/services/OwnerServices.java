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

import webdev.models.Owner;
import webdev.repositories.OwnerRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials="true")
public class OwnerServices {
	@Autowired
	OwnerRepository ownerRepository;
	
	@GetMapping("/api/owners")
	public Iterable<Owner> findAllOwners() {
		return ownerRepository.findAll(); 
	}
	
	@DeleteMapping("/api/owner/{ownerId}")
	public void deleteOwner(@PathVariable("ownerId") int ownerId) {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		if(optionalOwner.isPresent()) {
			ownerRepository.deleteById(ownerId);
		}
	}
	
	@PostMapping("/api/owner")
	public Owner addOwner(@RequestBody Owner owner) {
		Optional<Owner> optionalOwner = ownerRepository.findById(owner.getId());
		if(!optionalOwner.isPresent()) {
			return ownerRepository.save(owner);
		}
		return null;
	}
	
	@PutMapping("/api/owner")
	public Owner updateOwner(@RequestBody Owner owner) {
		Optional<Owner> optionalOwner = ownerRepository.findById(owner.getId());
		if(optionalOwner.isPresent()) {
			return ownerRepository.save(owner);
		}
		return null;
	}
}