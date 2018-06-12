package webdev.models;

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
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<Comment> comments;
	
	public int getId() {
		return id;
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
}