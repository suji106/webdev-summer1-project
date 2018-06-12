package webdev.models;

import javax.persistence.*;

@Entity
public class Mentor extends User {
	private String organization;

	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
}