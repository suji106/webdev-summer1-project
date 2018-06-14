package webdev.services;

import java.util.ArrayList;
import java.util.List;
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

import webdev.models.Request;
import webdev.models.Project;
import webdev.models.User;
import webdev.repositories.RequestRepository;
import webdev.repositories.ProjectRepository;
import webdev.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RequestServices {
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RequestRepository requestRepository;
	
	@GetMapping("/api/requests")
	public Iterable<Request> findAllRequests() {
		return requestRepository.findAll(); 
	}
	
	@GetMapping("/api/{projectId}/requests")
	public Iterable<Request> findAllRequestsForProjectId(@PathVariable("projectId") int projectId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			return project.getRequests();
		}
		return null;
	}
	
	@GetMapping("/api/{userId}/requests")
	public Iterable<Request> findAllRequestsForUserId(@PathVariable("userId") int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			return user.getRequests();
		}
		return null;
	}
	
	@DeleteMapping("/api/request/{requestId}")
	public void deleteRequest(@PathVariable("requestId") int requestId) {
		Optional<Request> optionalRequest = requestRepository.findById(requestId);
		if(optionalRequest.isPresent()) {
			requestRepository.deleteById(requestId);
		}
	}
	
	@DeleteMapping("/api/{userId}/requests")
	public void deleteAllRequestsWithUserId(@PathVariable("userId") int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.get();
		List<Request> requests= user.getRequests();
		for(Request request: requests) {
			deleteRequest(request.getId());
		}
	}
	
	@DeleteMapping("/api/{projectId}/requests")
	public void deleteAllRequestsWithProjectId(@PathVariable("projectId") int projectId) {
		Optional<Project> optionalUser = projectRepository.findById(projectId);
		Project project = optionalUser.get();
		List<Request> requests= project.getRequests();
		for(Request request: requests) {
			deleteRequest(request.getId());
		}
	}
	
	@PostMapping("/api/{projectId}/{userId}/request")
	public Request addRequest(@RequestBody Request request, @PathVariable("projectId") int projectId,
			@PathVariable("userId") int userId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		Optional<User> optionalUser = userRepository.findById(userId);
		request.setProject(optionalProject.get());
		request.setUser(optionalUser.get());
		return requestRepository.save(request);	
	}
	
	@PutMapping("/api/{projectId}/{userId}/request")
	public Request updateRequest(@RequestBody Request request, @PathVariable("projectId") int projectId,
			@PathVariable("userId") int userId) {
		Optional<Request> optionalRequest = requestRepository.findById(request.getId());
		if(optionalRequest.isPresent()) {
			requestRepository.save(request);
		}
		return null;
	}
	
	@GetMapping("/api/{projectId}/requests/contributors/accepted")
	public List<User> getAcceptedContributorsForProjects(@PathVariable("projectId") int projectId){
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Request> requests = project.getRequests();
			List<User> users = new ArrayList<User>();
			
			for(Request request: requests) {
				if(request.getReqStatus().equals(Request.RequestStatus.accepted)) {
					if(request.getUserType().equals(Request.UserType.contributor))
						users.add(request.getUser());
				}
			}
			return users;
		}
		return null;
	}
	
	@GetMapping("/api/{projectId}/requests/mentors/pending")
	public List<User> getAcceptedMentorsForProjects(@PathVariable("projectId") int projectId){
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Request> requests = project.getRequests();
			List<User> users = new ArrayList<User>();
			
			for(Request request: requests) {
				if(request.getReqStatus().equals(Request.RequestStatus.accepted)) {
					if(request.getUserType().equals(Request.UserType.mentor))
						users.add(request.getUser());
				}
			}
			return users;
		}
		return null;
	}
	
	@GetMapping("/api/{projectId}/requests/mentors/pending")
	public List<User> getPendingContributorsForProjects(@PathVariable("projectId") int projectId){
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Request> requests = project.getRequests();
			List<User> users = new ArrayList<User>();
			
			for(Request request: requests) {
				if(request.getReqStatus().equals(Request.RequestStatus.pending)) {
					if(request.getUserType().equals(Request.UserType.mentor))
						users.add(request.getUser());
				}
			}
			return users;
		}
		return null;
	}
	
	@GetMapping("/api/{projectId}/requests/mentor/pending")
	public List<User> getPendingMentorsForProjects(@PathVariable("projectId") int projectId){
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Request> requests = project.getRequests();
			List<User> users = new ArrayList<User>();
			
			for(Request request: requests) {
				if(request.getReqStatus().equals(Request.RequestStatus.pending)) {
					if(request.getUserType().equals(Request.UserType.contributor))
						users.add(request.getUser());
				}
			}
			return users;
		}
		return null;
	}
	
	
	@GetMapping("/api/{userId}/requests/accepted")
	public List<Project> getAcceptedProjectsForUser(@PathVariable("userId") int userId){
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			List<Request> requests = user.getRequests();
			List<Project> projects = new ArrayList<Project>();
			
			for(Request request: requests) {
				if(request.getReqStatus().equals(Request.RequestStatus.accepted)) {
					projects.add(request.getProject());
				}
			}
			return projects;
		}
		return null;
	}
	
	@GetMapping("/api/{userId}/requests/rejected")
	public List<Project> getRejectedProjectsForUser(@PathVariable("userId") int userId){
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			List<Request> requests = user.getRequests();
			List<Project> projects = new ArrayList<Project>();
			
			for(Request request: requests) {
				if(request.getReqStatus().equals(Request.RequestStatus.rejected)) {
					projects.add(request.getProject());
				}
			}
			return projects;
		}
		return null;
	}
	
	@GetMapping("/api/{userId}/requests/pending")
	public List<Project> getPendingProjectsForUser(@PathVariable("userId") int userId){
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			List<Request> requests = user.getRequests();
			List<Project> projects = new ArrayList<Project>();
			
			for(Request request: requests) {
				if(request.getReqStatus().equals(Request.RequestStatus.pending)) {
					projects.add(request.getProject());
				}
			}
			return projects;
		}
		return null;
	}
}