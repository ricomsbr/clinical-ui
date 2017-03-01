package br.com.ackta.clinical.data.entity;

import java.util.List;

import org.springframework.beans.BeanUtils;

public interface IPatient extends IPersistable {

	List<IConvenantMember> getConvenantMembers();

	IMedicalHistory getMedicalHistory();

	String getObservation();

	IPersonalData getPersonalData();

	List<IPersonalData> getResponsibles();

	public default IPatient merge(IPatient user) {
		BeanUtils.copyProperties(user, this, unmergedProperties);
		return this;
	}

	//TODO
	// List<IConsult> getConsults();

}
