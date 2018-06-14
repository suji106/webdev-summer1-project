package webdev.models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Owner extends User{
	private String token;
	@OneToMany(mappedBy="owner")
	@JsonIgnore
	private List<Project> projectsPosted;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	public List<Project> getProjectsPosted() {
		return projectsPosted;
	}

	public void setProjectsPosted(List<Project> projectsPosted) {
		this.projectsPosted = projectsPosted;
	}
}