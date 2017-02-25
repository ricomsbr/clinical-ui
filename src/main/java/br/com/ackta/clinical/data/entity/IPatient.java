package br.com.ackta.clinical.data.entity;

import org.springframework.beans.BeanUtils;

public interface IPatient extends IPersistable {
	static final String[] UNMERGED_PROPERTIES = { "id", "active", "version" };

	IPersonalData getPersonalData();

	public default IPatient merge(IPatient user) {
		BeanUtils.copyProperties(user, this, UNMERGED_PROPERTIES);
		return this;
	}
	//TODO
	// List<IPersonalData> getResponsibles();
	//
	// IPersonalBackground getPersonalBackground();
	//
	// IFamilyHistory getFamilyHistory();
	//
	// IConvenant getConvenant();
	//
	// Quantity<Mass> getWeight();
	//
	// Quantity<Length> getHeight();
	//
	// List<IConsult> getConsults();

}