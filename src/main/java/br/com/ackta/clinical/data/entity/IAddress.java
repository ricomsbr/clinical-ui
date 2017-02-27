package br.com.ackta.clinical.data.entity;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.BeanUtils;

public interface IAddress extends Comparable<IAddress> {

	@Override
	public default int compareTo(IAddress o) {
		int result = new CompareToBuilder().append(o.getIndex(), this.getIndex()).toComparison();
		return result;
	}

	String getCity();
	String getComplement();
	String getDescription();
	String getDistrict();
	Integer getIndex();
	String getNumber();

	String getPublicArea();

	AddressType getType();

	String getZipCode();

	public default IAddress merge(IAddress address) {
		BeanUtils.copyProperties(address, this);
		return this;
	}

}
