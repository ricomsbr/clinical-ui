package br.com.ackta.clinical.data.entity;

public interface IUser extends IPersistable {
	static final String[] UNMERGED_PROPERTIES = { "id", "active", "version" };

	String getMail();

	String getName();

	String getPassword();

	String getUsername();

	void setName(String name);

}
