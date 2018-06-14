package webdev.models;

import javax.persistence.*;

@Entity
public class Contributor extends User {
	private String languages;

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}
}