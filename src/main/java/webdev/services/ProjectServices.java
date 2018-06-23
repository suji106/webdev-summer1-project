package webdev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import webdev.models.Project;
import webdev.models.User;
import webdev.repositories.ProjectRepository;
import webdev.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectServices {
	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/api/projects")
	public Iterable<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	@GetMapping("/api/project/{projectId}")
	public Project getProjectById(@PathVariable("projectId") int projectId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			return optionalProject.get();
		}
		return null;
	}

	@GetMapping("/api/projects/owner")
	public List<Project> getProjectsByOwnerId(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			return user.getProjectsPosted();
		}
		return null;
	}
	
	@GetMapping("/api/project/{projectId}/owner")
	public String projectOwnedByOwner(@PathVariable("projectId") int projectId, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			if (currentUser.getId() == project.getOwner().getId()) {
				return "true";
			}
		}
		return "false";
	}

	@DeleteMapping("/api/project/{projectId}")
	public void deleteProject(@PathVariable("projectId") int projectId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			projectRepository.deleteById(projectId);
		}
	}

	@PostMapping("/api/project")
	public Project addProject(@RequestBody Project project, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
		if(optionalUser.isPresent()) {
			project.setOwner(optionalUser.get());
			return projectRepository.save(project);
		}
		return null;
	}
	
	@PutMapping("/api/project")
	public Project updateProject(@RequestBody Project project, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
		if(optionalUser.isPresent()) {
			project.setOwner(optionalUser.get());
			return projectRepository.save(project);
		}
		return null;
	}

	@PostMapping("/api/specialized")
	public List<Project> getSpecializedProjects(@RequestBody String langs) {
		langs = langs.replaceAll("\"", "");
		List<Project> projects = (List<Project>) getAllProjects();
		if (langs.trim().length() > 0) {
			List<Project> specialProjects = new ArrayList<Project>();
			String[] reqLangs = langs.split(" ", langs.length());
			for(String reqLang: reqLangs) {
				for(Project project: projects) {
					String contLanguages = project.getLanguages();
					String[] languages = contLanguages.split(" ", contLanguages.length());
					for(String language: languages) {
						System.out.println(reqLang + " " + language);
						if(reqLang.toLowerCase().equals(language.toLowerCase())) {
							specialProjects.add(project);
						}
					}
				}
			}
		return specialProjects.stream().distinct().collect(Collectors.toList());
		}
		else
			return projects;
	}
}
