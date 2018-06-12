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

import webdev.models.Mentor;
import webdev.repositories.MentorRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MentorServices {
	@Autowired
	MentorRepository mentorRepository;
	
	@GetMapping("/api/mentors")
	public Iterable<Mentor> findAllMentors() {
		return mentorRepository.findAll(); 
	}
	
	@DeleteMapping("/api/mentor/{mentorId}")
	public void deleteMentor(@PathVariable("mentorId") int mentorId) {
		Optional<Mentor> optionalMentor = mentorRepository.findById(mentorId);
		if(optionalMentor.isPresent()) {
			mentorRepository.deleteById(mentorId);
		}
	}
	
	@PostMapping("/api/mentor")
	public Mentor addMentor(@RequestBody Mentor mentor) {
		Optional<Mentor> optionalMentor = mentorRepository.findById(mentor.getId());
		if(!optionalMentor.isPresent()) {
			return mentorRepository.save(mentor);
		}
		return null;
	}
	
	@PutMapping("/api/mentor")
	public Mentor updateMentor(@RequestBody Mentor mentor) {
		Optional<Mentor> optionalMentor = mentorRepository.findById(mentor.getId());
		if(optionalMentor.isPresent()) {
			return mentorRepository.save(mentor);
		}
		return null;
	}
}