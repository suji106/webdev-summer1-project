package webdev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Owner;
import webdev.models.Project;
import webdev.repositories.OwnerRepository;
import webdev.repositories.ProjectRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectServices {
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	OwnerRepository ownerRepository;
	
	@GetMapping("/api/{projectId}")
	public Project getProjectById(@PathVariable("projectId") int projectId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			return optionalProject.get();
		}
		return null;
	}
	
	@GetMapping("/api/{ownerId}/projects")
	public List<Project> getProjectsByOwnerId(@PathVariable("ownerId") int ownerId) {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		if(optionalOwner.isPresent()) {
			Owner owner = optionalOwner.get();
			return owner.getProjectsPosted();
		}
		return null;
	}
	
	@DeleteMapping("/api/project/{projectId}")
	public void deleteProject(@PathVariable("projectId") int projectId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			projectRepository.deleteById(projectId);
		}
	}
	
	@DeleteMapping("/api/{ownerId}/projects")
	public void deleteAllProjectsWithOwnertId(@PathVariable("ownerId") int ownerId) {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		Owner owner = optionalOwner.get();
		List<Project> projects= owner.getProjectsPosted();
		for(Project project: projects) {
			deleteProject(project.getId());
		}
	}
	
	@PostMapping("/api/{ownerId}/project")
	public Project addComment(@RequestBody Project project, @PathVariable("ownerId") int ownerId,
			@PathVariable("userId") int userId) {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		if(optionalOwner.isPresent()) {
			project.setOwner(optionalOwner.get());
			return projectRepository.save(project);
		}
		return null;
	}
}
