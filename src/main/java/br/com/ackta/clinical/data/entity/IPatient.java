package br.com.ackta.clinical.data.entity;

import java.util.List;

import org.springframework.beans.BeanUtils;

public interface IPatient extends IPersistable {

	List<ConvenantMember> getConvenantMembers();

	MedicalHistory getMedicalHistory();

	String getObservation();

	PersonalData getPersonalData();

	List<Responsible> getResponsibles();

	public default IPatient merge(IPatient user) {
		BeanUtils.copyProperties(user, this, UNMERGED_PROPERTIES);
		return this;
	}

}
