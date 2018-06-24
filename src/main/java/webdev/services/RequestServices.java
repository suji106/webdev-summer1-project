package webdev.services;

import java.util.ArrayList;
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
	
	@GetMapping("/api/requests/user")
	public Iterable<Request> findAllRequestsForUserId(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
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
	
	@DeleteMapping("/api/request")
	public void deleteAllRequestsWithUserId(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
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
	
	@GetMapping("/api/{projectId}/request")
	public String getRequestStatus(@PathVariable("projectId") int projectId, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		int requestId = requestRepository.findIdByUserIdAndProjectId(currentUser, projectRepository.findById(projectId).get(),
				(String) session.getAttribute("currentUserType"));
		Optional<Request> optionalRequest = requestRepository.findById(requestId);
		if (optionalRequest.isPresent()) {
			Request request = optionalRequest.get();
			return request.getMessage();	
		}
		return "";
	}
	
	@PostMapping("/api/{projectId}/request")
	public Request addRequest(@RequestBody Request request, @PathVariable("projectId") int projectId,
			HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
		request.setProject(optionalProject.get());
		request.setUser(optionalUser.get());
		return requestRepository.save(request);	
	}
	
	@PutMapping("/api/{projectId}/request")
	public Request updateRequest(@RequestBody Request request, @PathVariable("projectId") int projectId,
			HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
		request.setProject(optionalProject.get());
		request.setUser(optionalUser.get());
		return requestRepository.save(request);	
	}
	
	@GetMapping("/api/{projectId}/requests/contributors/accepted")
	public List<Request> getAcceptedContributorRequestsForProjects(@PathVariable("projectId") int projectId){
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Request> requests = project.getRequests();
			List<Request> newRequests = new ArrayList<Request>();
			
			for(Request request: requests) {
				if(request.getReqStatus().equals(Request.RequestStatus.accepted)) {
					if(request.getUserType().equals("contributor"))
						newRequests.add(request);
				}
			}
			return newRequests;
		}
		return null;
	}
	
	@GetMapping("/api/{projectId}/requests/contributors/pending")
	public List<Request> getPendingContributorRequestsForProjects(@PathVariable("projectId") int projectId){
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Request> requests = project.getRequests();
			List<Request> newRequests = new ArrayList<Request>();
			
			for(Request request: requests) {
				if(request.getReqStatus().equals(Request.RequestStatus.pending)) {
					if(request.getUserType().equals("contributor"))
						newRequests.add(request);
				}
			}
			return newRequests;
		}
		return null;
	}
	
	@GetMapping("/api/{projectId}/requests/mentors/accepted")
	public List<Request> getAcceptedMentorRequestsForProjects(@PathVariable("projectId") int projectId){
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Request> requests = project.getRequests();
			List<Request> newRequests = new ArrayList<Request>();
			
			for(Request request: requests) {
				if(request.getReqStatus().equals(Request.RequestStatus.accepted)) {
					if(request.getUserType().equals("mentor"))
						newRequests.add(request);
				}
			}
			return newRequests;
		}
		return null;
	}
	
	@GetMapping("/api/{projectId}/requests/mentors/pending")
	public List<Request> getPendingMentorRequestsForProjects(@PathVariable("projectId") int projectId){
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Request> requests = project.getRequests();
			List<Request> newRequests = new ArrayList<Request>();
			
			for(Request request: requests) {
				if(request.getReqStatus().equals(Request.RequestStatus.pending)) {
					if(request.getUserType().equals("mentor"))
						newRequests.add(request);
				}
			}
			return newRequests;
		}
		return null;
	}
	
	@GetMapping("/api/requests/accepted")
	public List<Project> getAcceptedProjectsForUser(HttpSession session){
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
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
	
	@GetMapping("/api/requests/rejected")
	public List<Project> getRejectedProjectsForUser(HttpSession session){
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
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
	
	@GetMapping("/api/requests/pending")
	public List<Project> getPendingProjectsForUser(HttpSession session){
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
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
