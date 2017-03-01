package br.com.ackta.clinical.data.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public interface IMedicalHistory {
	Boolean getAllergic();
	String getAllergies();
	LocalDate getDate();
	String getDiseases();
	Boolean getDrinker();
	Integer getDrinkFrequence();
	ChronoUnit getDrinkPeriodUnit();
	IFamilyHistory getFamilyHistory();
	Boolean getHasDiseases();
	Boolean getHasSurgeries();
	Double getHeight();
	String getMedicines();
	Integer getSmokeFrequence();
	ChronoUnit getSmokePeriodUnit();
	Boolean getSmoker();
	String getSurgeries();
	Double getWeight();
}
