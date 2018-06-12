package webdev.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Milestone {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
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
