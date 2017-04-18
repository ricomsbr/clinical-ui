package br.com.ackta.clinical.data.entity;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

public interface IResponsible extends Serializable {

	static final String[] UNMERGED_PROPERTIES = { "id", "deleted", "version" };

	String getName();

	String getPhoneNumber();

	public default IResponsible merge(IResponsible user) {
		BeanUtils.copyProperties(user, this, UNMERGED_PROPERTIES);
		return this;
	}

}
