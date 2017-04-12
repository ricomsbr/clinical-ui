package br.com.ackta.clinical.data.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

import org.springframework.beans.BeanUtils;

@Embeddable
public class MedicalHistory implements IMedicalHistory {

	@Column(name = "date", nullable=false)
	private LocalDate date;

	@Column(name = "weight", nullable=false)
	private Double weight; //TODO Quantity<Mass>

	@Column(name = "height", nullable=false)
	private Double height; // TODO Quantity<Length>

	@Column(name = "smoker", nullable=false)
	private Boolean smoker;

	@Column(name = "smoke_frequence", nullable=true)
	private Integer smokeFrequence;

	@Column(name = "smoke_period_unit", nullable=true)
	private ChronoUnit smokePeriodUnit;

	@Column(name = "drinker", nullable=false)
	private Boolean drinker;

	@Column(name = "drink_frequence", nullable=true)
	private Integer drinkFrequence;

	@Column(name = "drink_period_unit", nullable=true)
	private ChronoUnit drinkPeriodUnit;

	@Column(name = "allergic", nullable=false)
	private Boolean allergic;

	@Column(name = "has_diseases", nullable=false)
	private Boolean hasDiseases;

	@Column(name = "has_surgeries", nullable=false)
	private Boolean hasSurgeries;

	@Column(name = "diseases", nullable=true)
	private String diseases;

	@Column(name = "surgeries", nullable=true)
	private String surgeries;

	@Column(name = "allergies", nullable=true)
	private String allergies;

	@Column(name = "medicines", nullable=true)
	private String medicines;

	@ElementCollection(targetClass=FamilyMember.class)
	@CollectionTable(
	        name="family_member",
	        joinColumns=@JoinColumn(name="family_member_id")
	  )
	private List<FamilyMember> familyMembers = new ArrayList<>();

	public MedicalHistory() {
		super();
		this.date = LocalDate.now();
	}

	public MedicalHistory(IMedicalHistory medicalHistory) {
		this();
		BeanUtils.copyProperties(medicalHistory, this);
	}

	@Override
	public void addFamilyMembers(FamilyMember memberHistory) {
		familyMembers.add(memberHistory);
	}

	@Override
	public Boolean getAllergic() {
		return allergic;
	}

	@Override
	public String getAllergies() {
		return allergies;
	}

	@Override
	public LocalDate getDate() {
		return date;
	}

	@Override
	public String getDiseases() {
		return diseases;
	}

	@Override
	public Boolean getDrinker() {
		return drinker;
	}

	@Override
	public Integer getDrinkFrequence() {
		return drinkFrequence;
	}

	@Override
	public ChronoUnit getDrinkPeriodUnit() {
		return drinkPeriodUnit;
	}

	@Override
	public List<FamilyMember> getFamilyMembers() {
		return familyMembers;
	}

	@Override
	public Boolean getHasDiseases() {
		return hasDiseases;
	}

	@Override
	public Boolean getHasSurgeries() {
		return hasSurgeries;
	}

	@Override
	public Double getHeight() {
		return height;
	}

	@Override
	public String getMedicines() {
		return medicines;
	}

	@Override
	public Integer getSmokeFrequence() {
		return smokeFrequence;
	}

	@Override
	public ChronoUnit getSmokePeriodUnit() {
		return smokePeriodUnit;
	}

	@Override
	public Boolean getSmoker() {
		return smoker;
	}

	@Override
	public String getSurgeries() {
		return surgeries;
	}

	@Override
	public Double getWeight() {
		return weight;
	}

	public void setAllergic(Boolean allergic) {
		this.allergic = allergic;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setDiseases(String diseases1) {
		this.diseases = diseases1;
	}

	public void setDrinker(Boolean drinker) {
		this.drinker = drinker;
	}

	public void setDrinkFrequence(Integer drinkFrequence) {
		this.drinkFrequence = drinkFrequence;
	}

	public void setDrinkPeriodUnit(ChronoUnit drinkPeriodUnit) {
		this.drinkPeriodUnit = drinkPeriodUnit;
	}

	public void setFamilyMembers(List<FamilyMember> members) {
		this.familyMembers = members;
	}

	public void setHasDiseases(Boolean hasDiseases) {
		this.hasDiseases = hasDiseases;
	}

	public void setHasSurgeries(Boolean hasSurgeries) {
		this.hasSurgeries = hasSurgeries;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public void setMedicines(String medicines) {
		this.medicines = medicines;
	}

	public void setSmokeFrequence(Integer smokeFrequence) {
		this.smokeFrequence = smokeFrequence;
	}

	public void setSmokePeriodUnit(ChronoUnit smokePeriodUnit) {
		this.smokePeriodUnit = smokePeriodUnit;
	}

	public void setSmoker(Boolean smoker) {
		this.smoker = smoker;
	}

	public void setSurgeries(String surgeries) {
		this.surgeries = surgeries;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
}
