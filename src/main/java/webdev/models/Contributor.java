package webdev.models;

import javax.persistence.*;

enum SpecType {
	frontendEngineer, backendEngineer, dataScienceEngineer, qualityEngineer
}

@Entity
public class Contributor extends User {
	@Enumerated(EnumType.STRING)
	private SpecType specifications;
	public SpecType getSpecifications() {
		return specifications;
	}
	public void setSpecifications(SpecType specifications) {
		this.specifications = specifications;
	}
}