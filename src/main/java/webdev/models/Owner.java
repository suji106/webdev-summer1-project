package webdev.models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Owner extends User{
	@OneToMany(mappedBy="owner")
	@JsonIgnore
	private List<Project> projectsPosted;

	public List<Project> getProjetcsPosted() {
		return projectsPosted;
	}

	public void setProjetcsPosted(List<Project> projectsPosted) {
		this.projectsPosted = projectsPosted;
	}
}