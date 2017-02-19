package br.com.ackta.clinical.model.entity;

public interface IUser extends IPersistable {
	static final String[] UNMERGED_PROPERTIES = { "id", "active", "version" };

	String getName();

	String getPassword();

	String getUsername();

	void setName(String name);

	String getMail();

}
