package br.com.ackta.clinical.data.entity;

import org.springframework.beans.BeanUtils;

public interface IUser extends IPersistable {

	String getMail();

	String getName();

	String getPassword();

	String getUsername();

	public default IUser merge(IPatient user) {
		BeanUtils.copyProperties(user, this, UNMERGED_PROPERTIES);
		return this;
	}
	void setName(String name);
}
