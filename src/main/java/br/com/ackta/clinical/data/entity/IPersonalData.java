package br.com.ackta.clinical.data.entity;

import java.time.LocalDate;
import java.util.SortedSet;

import org.springframework.beans.BeanUtils;

public interface IPersonalData extends IPersistable {

	void addPhone(Phone phones);

	SortedSet<Address> getAddresses();

	LocalDate getBirthDate();

	Integer getChildrenQty();

	String getCpf();

	Gender getGender();

	String getMail();

	MaritalState getMaritalState();

	String getName();

	SortedSet<Phone> getPhones();

	String getRg();

	public default IPersonalData merge(IPersonalData newData) {
		BeanUtils.copyProperties(newData, this, UNMERGED_PROPERTIES);
		this.getAddresses().first().merge(newData.getAddresses().first());
		return this;
	}

	void setAddresses(SortedSet<Address> addresses);

	void setBirthDate(LocalDate birthDate);

	void setChildrenQty(Integer childrenQty);

	void setCpf(String cpf);

	void setGender(Gender gender);

	void setMail(String mail);

	void setMaritalState(MaritalState maritalState);

	void setName(String name);

	void setRg(String rg);
}
