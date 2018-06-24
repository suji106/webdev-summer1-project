package webdev.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Meeting;
import webdev.models.Project;
import webdev.models.User;
import webdev.repositories.MeetingRepository;
import webdev.repositories.ProjectRepository;
import webdev.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MeetingServices {
	@Autowired
	MeetingRepository meetingRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/api/meetings")
	public Iterable<Meeting> findAllMeetings() {
		return meetingRepository.findAll(); 
	}
	
	@DeleteMapping("/api/meeting/{meetingId}")
	public void deleteMeeting(@PathVariable("meetingId") int meetingId) {
		Optional<Meeting> optionalRequest = meetingRepository.findById(meetingId);
		if(optionalRequest.isPresent()) {
			meetingRepository.deleteById(meetingId);
		}
	}
	
	@PostMapping("/api/{projectId}/meeting")
	public Meeting addMeeting(@RequestBody Meeting meeting, @PathVariable("projectId") int projectId,
			HttpSession session) {
		User currentMentor = (User) session.getAttribute("currentUser");
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		Optional<User> optionalUser = userRepository.findById(currentMentor.getId());
		meeting.setProject(optionalProject.get());
		meeting.setMentor(optionalUser.get());
		return meetingRepository.save(meeting);	
	}
	
	@PutMapping("/api/{projectId}/meeting")
	public Meeting updateRequest(@RequestBody Meeting meeting, @PathVariable("projectId") int projectId,
			HttpSession session) {
		User currentMentor = (User) session.getAttribute("currentUser");
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		Optional<User> optionalUser = userRepository.findById(currentMentor.getId());
		meeting.setProject(optionalProject.get());
		meeting.setMentor(optionalUser.get());
		return meetingRepository.save(meeting);
	}
	
	@GetMapping("/api/{projectId}/meetings")
	public List<Meeting> getMeetingsForProjects(@PathVariable("projectId") int projectId){
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Meeting> meetings = project.getMeetings();
			return meetings;
		}
		return null;
	}
	
	@GetMapping("/api/user/meetings")
	public List<Meeting> getMeetingsForMentor(HttpSession session) {
		User currentMentor = (User) session.getAttribute("currentUser");
		Optional<User> optionalMentor = userRepository.findById(currentMentor.getId());
		if(optionalMentor.isPresent()) {
			List<Meeting> meetings = currentMentor.getMeetings();
			return meetings;
		}
		return null;
	}
}