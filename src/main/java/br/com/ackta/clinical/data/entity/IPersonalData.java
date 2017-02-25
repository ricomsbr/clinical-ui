package br.com.ackta.clinical.data.entity;

import java.time.LocalDate;
import java.util.SortedSet;

import org.springframework.beans.BeanUtils;

public interface IPersonalData extends IPersistable {
	static final String[] UNMERGED_PROPERTIES = { "id", "active", "version" };

	SortedSet<Address> getAddresses();

	LocalDate getBirthDate();

	Integer getChildrenQty();

	String getCpf();

	Gender getGender();

	String getName();

	String getRg();

//	 List<IPhone> getPhones();

	// MaritalState getMaritalState();
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

	void setName(String name);

	void setRg(String rg);

//	 String getMail();
}
