package br.com.ackta.clinical.data.entity;

import java.time.Year;

public interface IFamilyMemberHistory {
	Boolean getAlive();
	Year getBirthYear();
	String getDiseases();
	Kinship getKinship();
	String getMedicines();
	void setBirthYear(Year i);
}
