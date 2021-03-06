package webdev.services;

import java.sql.Date;
import java.util.Calendar;
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

import webdev.models.Comment;
import webdev.models.Project;
import webdev.models.User;
import webdev.repositories.CommentRepository;
import webdev.repositories.ProjectRepository;
import webdev.repositories.UserRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials="true")
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
		System.out.println(projectId);
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Comment> comments= project.getComments();
			System.out.println(comments.size());
			comments.sort((o1,o2) -> o1.getCreated().compareTo(o2.getCreated()));
			return comments;
		}
		return null;
	}
	
	@GetMapping("/api/user/comments")
	public Iterable<Comment> findAllCommentsForUserId(@PathVariable("userId") int userId, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
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
	
	@PutMapping("/api/{projectId}/comment")
	public Comment updateComment(@RequestBody Comment comment, HttpSession session,
			@PathVariable("projectId") int projectId) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent() && optionalUser.isPresent()) {
			comment.setProject(optionalProject.get());
			comment.setUser(optionalUser.get());
			return commentRepository.save(comment);
		}
		return null;
	}
	
	@PostMapping("/api/{projectId}/comment")
	public Comment addComment(@RequestBody Comment comment, @PathVariable("projectId") int projectId,
			HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> optionalUser = userRepository.findById(currentUser.getId());
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent() && optionalUser.isPresent()) {
			comment.setCreated(new Date(Calendar.getInstance().getTime().getTime()));
			comment.setProject(optionalProject.get());
			comment.setUserType((String) session.getAttribute("userType"));
			comment.setUser(optionalUser.get());
			return commentRepository.save(comment);
		}
		return null;
	}
}