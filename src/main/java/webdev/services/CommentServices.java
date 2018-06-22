package webdev.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

import webdev.models.Comment;
import webdev.models.Project;
import webdev.models.Request;
import webdev.models.User;
import webdev.repositories.CommentRepository;
import webdev.repositories.ProjectRepository;
import webdev.repositories.UserRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentServices {
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CommentRepository commentRepository;
	
	@GetMapping("/api/comments")
	public Iterable<Comment> findAllComments() {
		return commentRepository.findAll(); 
	}
	
	@GetMapping("/api/{projectId}/project/comments")
	public Iterable<Comment> findAllCommentsForProjectId(@PathVariable("projectId") int projectId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Comment> comments= project.getComments();
			comments.sort((o1,o2) -> o1.getCreated().compareTo(o2.getCreated()));
			return comments;
		}
		return null;
	}
	
	@GetMapping("/api/{userId}/user/comments")
	public Iterable<Comment> findAllCommentsForUserId(@PathVariable("userId") int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			List<Comment> comments= user.getComments();
			comments.sort((o1,o2) -> o1.getCreated().compareTo(o2.getCreated()));
			return comments;
		}
		return null;
	}
	
	@DeleteMapping("/api/comment/{commentId}")
	public void deleteComment(@PathVariable("commentId") int commentId) {
		Optional<Comment> optionalComment = commentRepository.findById(commentId);
		if(optionalComment.isPresent()) {
			commentRepository.deleteById(commentId);
		}
	}
	
	@DeleteMapping("/api/{projectId}/comments")
	public void deleteAllCommentsWithProjectId(@PathVariable("projectId") int projectId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		Project project = optionalProject.get();
		List<Comment> comments= project.getComments();
		for(Comment comment: comments) {
			deleteComment(comment.getId());
		}
	}
	
	@DeleteMapping("/api/{userId}/comments")
	public void deleteAllCommentsWithUserId(@PathVariable("userId") int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.get();
		List<Request> comments= user.getRequests();
		for(Request comment: comments) {
			deleteComment(comment.getId());
		}
	}
	
	@PutMapping("/api/{projectId}/{userId}/comment")
	public Comment updateComment(@RequestBody Comment comment, @PathVariable("projectId") int projectId,
			@PathVariable("userId") int userId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalProject.isPresent() && optionalUser.isPresent()) {
			comment.setProject(optionalProject.get());
			comment.setUser(optionalUser.get());
			return commentRepository.save(comment);
		}
		return null;
	}
	
	@PostMapping("/api/{projectId}/{userId}/comment")
	public Comment addComment(@RequestBody Comment comment, @PathVariable("projectId") int projectId,
			@PathVariable("userId") int userId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalProject.isPresent() && optionalUser.isPresent()) {
			comment.setProject(optionalProject.get());
			comment.setUser(optionalUser.get());
			return commentRepository.save(comment);
		}
		return null;
	}
}