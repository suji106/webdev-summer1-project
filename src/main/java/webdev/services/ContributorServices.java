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

import webdev.models.Contributor;
import webdev.repositories.ContributorRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials="true")
public class ContributorServices {
	@Autowired
	ContributorRepository contributorRepository;
	
	@GetMapping("/api/contributors")
	public Iterable<Contributor> findAllContributors() {
		return contributorRepository.findAll(); 
	}
	
	@DeleteMapping("/api/contributor/{contributorId}")
	public void deleteContributor(@PathVariable("contributorId") int contributorId) {
		Optional<Contributor> optionalContributor = contributorRepository.findById(contributorId);
		if(optionalContributor.isPresent()) {
			contributorRepository.deleteById(contributorId);
		}
	}
	
	@PostMapping("/api/contributor")
	public Contributor addContributor(@RequestBody Contributor contributor) {
		Optional<Contributor> optionalContributor = contributorRepository.findById(contributor.getId());
		if(!optionalContributor.isPresent()) {
			return contributorRepository.save(contributor);
		}
		return null;
	}
	
	@PutMapping("/api/contributor")
	public Contributor updateContributor(@RequestBody Contributor contributor) {
		Optional<Contributor> optionalContributor = contributorRepository.findById(contributor.getId());
		if(optionalContributor.isPresent()) {
			return contributorRepository.save(contributor);
		}
		return null;
	}
}