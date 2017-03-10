package br.com.ackta.clinical.data.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public interface IMedicalHistory {
	Boolean getAllergic();
	String getAllergies();
	LocalDate getDate();
	String getDiseases();
	Boolean getDrinker();
	Integer getDrinkFrequence();
	ChronoUnit getDrinkPeriodUnit();
	Boolean getHasDiseases();
	Boolean getHasSurgeries();
	Double getHeight();
	String getMedicines();
	Integer getSmokeFrequence();
	ChronoUnit getSmokePeriodUnit();
	Boolean getSmoker();
	String getSurgeries();
	Double getWeight();
	void addFamilyMembers(FamilyMember memberHistory);
	List<FamilyMember> getFamilyMembers();
}
