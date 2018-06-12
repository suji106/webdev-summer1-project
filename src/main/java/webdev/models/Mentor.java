package webdev.models;

import java.util.List;

import javax.persistence.*;

@Entity
public class Mentor extends User {
	@ManyToMany(targetEntity=webdev.models.Project.class)
	private List<Project> acceptedProjects;
	@ManyToMany(targetEntity=webdev.models.Project.class)
	private List<Project> rejectedProjects;
	@ManyToMany(targetEntity=webdev.models.Project.class)
	private List<Project> pendingProjects;
	private String organization;
	public List<Project> getAcceptedProjects() {
		return acceptedProjects;
	}
	public void setAcceptedProjects(List<Project> acceptedProjects) {
		this.acceptedProjects = acceptedProjects;
	}
	public List<Project> getRejectedProjects() {
		return rejectedProjects;
	}
	public void setRejectedProjects(List<Project> rejectedProjects) {
		this.rejectedProjects = rejectedProjects;
	}
	public List<Project> getPendingProjects() {
		return pendingProjects;
	}
	public void setPendingProjects(List<Project> pendingProjects) {
		this.pendingProjects = pendingProjects;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
}