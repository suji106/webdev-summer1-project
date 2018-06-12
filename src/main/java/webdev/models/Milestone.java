package webdev.models;

import java.sql.Date;

import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Milestone {
	private String milestone;
	private Date created;
	@JsonIgnore
	@ManyToOne
	private Project project;
	
	public String getComment() {
		return milestone;
	}
	public void setComment(String milestone) {
		this.milestone = milestone;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
}
