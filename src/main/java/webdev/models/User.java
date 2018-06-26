package webdev.models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String password;
	private String githubId;
	private String imageUrl;
	private String socialType;
	private Date created;
	private String linkedinUrl;
	private String githubUrl;
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<Comment> comments;
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<Request> requests;
	@JsonIgnore
	@OneToMany(mappedBy="mentor")
	private List<Meeting> meetings;
	private String organization;
	@OneToMany(mappedBy="owner")
	@JsonIgnore
	private List<Project> projectsPosted;
	@JsonIgnore
	@ManyToMany(mappedBy="usersFollowed")
	private List<User> usersFollowing;
	@JsonIgnore
	@ManyToMany
	private List<User> usersFollowed;

	public List<Project> getProjectsPosted() {
		return projectsPosted;
	}
	public void setProjectsPosted(List<Project> projectsPosted) {
		this.projectsPosted = projectsPosted;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public int getId() {
		return id;
	}
	public List<Request> getRequests() {
		return requests;
	}
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getSocialType() {
		return socialType;
	}
	public void setSocialType(String socialType) {
		this.socialType = socialType;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getLinkedinUrl() {
		return linkedinUrl;
	}

	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}

	public String getGithubUrl() {
		return githubUrl;
	}

	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}
	public String getGithubId() {
		return githubId;
	}
	public void setGithubId(String githubId) {
		this.githubId = githubId;
	}
	public List<User> getUsersFollowing() {
		return usersFollowing;
	}
	public void setUsersFollowing(List<User> usersFollowing) {
		this.usersFollowing = usersFollowing;
	}
	public List<User> getUsersFollowed() {
		return usersFollowed;
	}
	public void setUsersFollowed(List<User> usersFollowed) {
		this.usersFollowed = usersFollowed;
	}
}