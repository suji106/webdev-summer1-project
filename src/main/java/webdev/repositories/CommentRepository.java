package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Comment;

public interface CommentRepository
	extends CrudRepository<Comment, Integer> {
	
}