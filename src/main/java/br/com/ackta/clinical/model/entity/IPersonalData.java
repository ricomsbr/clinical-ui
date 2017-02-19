package br.com.ackta.clinical.model.entity;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

public interface IPersonalData extends IPersistable {
	static final String[] UNMERGED_PROPERTIES = { "id", "active", "version" };

	LocalDate getBirthDate();

	String getCpf();

	Gender getGender();

	String getName();

	String getRg();

	public default IPersonalData merge(IPersonalData newData) {
		BeanUtils.copyProperties(this, newData, UNMERGED_PROPERTIES);
		return newData;
	}
	//
	// IAddress getAddress();
	//
	// List<IPhone> getPhones();
	//
	// MaritalState getMaritalState();
	//
	// Integer getSonQty();
	//
	// String getMail();
}
