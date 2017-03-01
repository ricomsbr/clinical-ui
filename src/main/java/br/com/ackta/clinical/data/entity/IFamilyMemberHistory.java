package br.com.ackta.clinical.data.entity;

public interface IFamilyMemberHistory {
	Boolean getAlive();
	Integer getBirthYear();
	String getDiseases();
	Kinship getKinship();
	String getMedicines();
}
