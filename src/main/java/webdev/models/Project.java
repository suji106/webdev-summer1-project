package webdev.models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Project {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	private String summary;
	@JsonIgnore
	@ManyToOne
	private Owner owner;
	@JsonIgnore
	@ManyToMany(targetEntity=webdev.models.Contributor.class, mappedBy="acceptedProjetcs")
	private List<Contributor> acceptedContributors;
	@JsonIgnore
	@ManyToMany(targetEntity=webdev.models.Contributor.class, mappedBy="rejectedContributors")
	private List<Contributor> rejectedContributors;
	@JsonIgnore
	@ManyToMany(targetEntity=webdev.models.Contributor.class, mappedBy="pendingContributors")
	private List<Contributor> pendingContributors;
	@JsonIgnore
	@ManyToMany(targetEntity=webdev.models.Mentor.class, mappedBy="acceptedMentors")
	private List<Mentor> acceptedMentors;
	@JsonIgnore
	@ManyToMany(targetEntity=webdev.models.Mentor.class, mappedBy="rejectedMentors")
	private List<Mentor> rejectedMentors;
	@JsonIgnore
	@ManyToMany(targetEntity=webdev.models.Mentor.class, mappedBy="pendingMentors")
	private List<Mentor> pendingMentors;
	private String url;
	private List<String> languages;
	@OneToMany(mappedBy="project")
	@JsonIgnore
	private List<Comment> comments;
	@OneToMany(mappedBy="project")
	@JsonIgnore
	private List<Milestone> milestones;
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
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public List<Contributor> getAcceptedContributors() {
		return acceptedContributors;
	}
	public void setAcceptedContributors(List<Contributor> acceptedContributors) {
		this.acceptedContributors = acceptedContributors;
	}
	public List<Contributor> getRejectedContributors() {
		return rejectedContributors;
	}
	public void setRejectedContributors(List<Contributor> rejectedContributors) {
		this.rejectedContributors = rejectedContributors;
	}
	public List<Contributor> getPendingContributors() {
		return pendingContributors;
	}
	public void setPendingContributors(List<Contributor> pendingContributors) {
		this.pendingContributors = pendingContributors;
	}
	public List<Mentor> getAcceptedMentors() {
		return acceptedMentors;
	}
	public void setAcceptedMentors(List<Mentor> acceptedMentors) {
		this.acceptedMentors = acceptedMentors;
	}
	public List<Mentor> getRejectedMentors() {
		return rejectedMentors;
	}
	public void setRejectedMentors(List<Mentor> rejectedMentors) {
		this.rejectedMentors = rejectedMentors;
	}
	public List<Mentor> getPendingMentors() {
		return pendingMentors;
	}
	public void setPendingMentors(List<Mentor> pendingMentors) {
		this.pendingMentors = pendingMentors;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<String> getLanguages() {
		return languages;
	}
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Milestone> getMilestones() {
		return milestones;
	}
	public void setMilestones(List<Milestone> milestones) {
		this.milestones = milestones;
	}
}