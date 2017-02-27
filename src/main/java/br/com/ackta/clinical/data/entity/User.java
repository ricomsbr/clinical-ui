package br.com.ackta.clinical.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Version;

@Entity
@Table(name = "ackta_user")
@SQLDelete(sql = "UPDATE ackta_user SET deleted = 1 WHERE id = ? AND version = ?")
@Where(clause="deleted = 0")
public class User implements IUser {

	private static final long serialVersionUID = -3659855708555492709L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@Column(name = "mail", nullable = false)
	private String mail;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "username", nullable = false)
	private String username;

	public User() {
		super();
	}

	public User(String username, String password, String name, boolean isDeleted) {
		this();
		this.username = username;
		this.password = password;
		this.name = name;
		this.deleted = isDeleted;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getMail() {
		return mail;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}

}
