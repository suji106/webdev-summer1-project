package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Meeting;

public interface MeetingRepository
	extends CrudRepository<Meeting, Integer> {
}