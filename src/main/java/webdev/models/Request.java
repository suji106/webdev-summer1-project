package webdev.models;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Request {
	public enum RequestStatus {
		accepted, rejected, pending
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String message;
	private Date created;
	private String userType;
	@Enumerated
	private RequestStatus reqStatus;
	@ManyToOne
	private User user;
	@ManyToOne
	private Project project;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public RequestStatus getReqStatus() {
		return reqStatus;
	}
	public void setReqStatus(RequestStatus reqStatus) {
		this.reqStatus = reqStatus;
	}
}
