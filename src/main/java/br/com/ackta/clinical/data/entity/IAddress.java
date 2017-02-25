package br.com.ackta.clinical.data.entity;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.BeanUtils;

public interface IAddress extends IPersistable, Comparable<IAddress> {
	static final String[] UNMERGED_PROPERTIES = { "id", "active", "version" };

	@Override
	public default int compareTo(IAddress o) {
		int result = new CompareToBuilder().append(this.getIndex(), o.getIndex()).toComparison();
		return result;
	}

	String getCity();
	String getComplement();
	String getDistrict();
	Integer getIndex();
	String getNumber();

	IPersonalData getPersonalData();
	String getPublicArea();

	public default IAddress merge(IAddress address) {
		BeanUtils.copyProperties(address, this, UNMERGED_PROPERTIES);
		return this;
	}

	void setPersonalData(IPersonalData personalData);
}
