package br.com.ackta.clinical.data.entity;

import java.time.Year;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FamilyMember implements IFamilyMemberHistory {

	@Column(name = "medicines", nullable= true)
	private String medicines;

	@Column(name = "kinship", nullable= false)
	private Kinship kinship;

	@Column(name = "diseases", nullable= true)
	private String diseases;

	@Column(name = "alive", nullable= false)
	private Boolean alive = true;

	@Column(name = "birth_year", nullable= true)
	private Year birthYear;

	public FamilyMember() {
		super();
	}

	public FamilyMember(Kinship kinship1) {
		this();
		this.kinship = kinship1;
	}
	
	@Override
	public Boolean getAlive() {
		return alive;
	}

	@Override
	public Year getBirthYear() {
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

	@Override
	public void setBirthYear(Year i) {
		this.birthYear = i;
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
