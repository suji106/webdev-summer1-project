package webdev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import webdev.models.Project;
import webdev.models.Request;
import webdev.models.User;

public interface RequestRepository
	extends CrudRepository<Request, Integer> {
	@Query("SELECT id FROM Request r WHERE r.user=:user and r.project=:project and r.userType = :userType")
	int findIdByUserIdAndProjectId(
		@Param("user") User user, @Param("project") Project project, @Param("userType") String userType);
}