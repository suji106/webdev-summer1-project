package webdev.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	private String summary;
	private String languages;
	private Date created;
	private String token;
	@JsonIgnore
	@ManyToOne
	private User owner;
	private String url;
	@OneToMany(mappedBy="project", orphanRemoval = true, cascade = CascadeType.PERSIST)
	@JsonIgnore
	private List<Comment> comments;
	@OneToMany(mappedBy="project", orphanRemoval = true, cascade = CascadeType.PERSIST)
	@JsonIgnore
	private List<Meeting> meetings;
	@OneToMany(mappedBy="project", orphanRemoval = true, cascade = CascadeType.PERSIST)
	@JsonIgnore
	private List<Request> requests;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLanguages() {
		return languages;
	}
	public void setLanguages(String languages) {
		this.languages = languages;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Meeting> getMeetings() {
		return meetings;
	}
	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}
	public List<Request> getRequests() {
		return requests;
	}
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
}