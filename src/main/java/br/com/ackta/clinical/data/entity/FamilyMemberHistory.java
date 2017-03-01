package br.com.ackta.clinical.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FamilyMemberHistory implements IFamilyMemberHistory {

	@Column(name = "medicines", nullable= true)
	private String medicines;

	@Column(name = "kinship", nullable= false)
	private Kinship kinship;

	@Column(name = "diseases", nullable= true)
	private String diseases;

	@Column(name = "alive", nullable= false)
	private Boolean alive = true;

	@Column(name = "birth_year", nullable= true)
	private Integer birthYear;

	public FamilyMemberHistory() {
		super();
	}

	@Override
	public Boolean getAlive() {
		return alive;
	}

	@Override
	public Integer getBirthYear() {
		return birthYear;
	}

	@Override
	public String getDiseases() {
		return diseases;
	}

	@Override
	public Kinship getKinship() {
		return kinship;
	}

	@Override
	public String getMedicines() {
		return medicines;
	}

	public void setAlive(Boolean alive) {
		this.alive = alive;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	public void setDiseases(String diseases) {
		this.diseases = diseases;
	}

	public void setKinship(Kinship kinship) {
		this.kinship = kinship;
	}

	public void setMedicines(String medicines) {
		this.medicines = medicines;
	}

}
