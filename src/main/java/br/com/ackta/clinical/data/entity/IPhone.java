package br.com.ackta.clinical.data.entity;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.BeanUtils;

public interface IPhone extends Comparable<IPhone> {

	@Override
	public default int compareTo(IPhone o) {
		int result = new CompareToBuilder().append(o.getIndex(), this.getIndex()).toComparison();
		return result;
	}

	Integer getCountryCode();

	String getDescription();

	Integer getIndex();

	String getNumber();

	Integer getRegionalCode();

	PhoneType getType();

	public default IPhone merge(IPhone address) {
		BeanUtils.copyProperties(address, this);
		return this;
	}

}
