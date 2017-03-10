package br.com.ackta.clinical.data.entity;

import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "personal_data")
@SQLDelete(sql = "UPDATE personal_data SET deleted = 1 WHERE id = ? AND version = ?")
@Where(clause="deleted = 0")
public class PersonalData implements IPersonalData {

	private static final long serialVersionUID = -2383673733659048451L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "birth_dt", nullable = true)
	private LocalDate birthDate;

	@Column(name = "gender_id", nullable = true)
	private Gender gender;

	@Column(name = "cpf", nullable = true)
	private String cpf;

	@Column(name = "rg", nullable = true)
	private String rg;

	@Column(name = "profession", nullable = true)
	private String profession;

	@OneToMany(targetEntity=Address.class, mappedBy = "personalData", orphanRemoval=true, cascade={CascadeType.ALL})
	@OrderBy("index DESC")
	private SortedSet<Address> addresses = new TreeSet<Address>();

	@OneToMany(targetEntity=Phone.class, mappedBy = "personalData", orphanRemoval=true, cascade={CascadeType.ALL})
	@OrderBy("index DESC")
	private SortedSet<Phone> phones = new TreeSet<Phone>();

	@Column(name = "children_qty", nullable = true)
	private Integer childrenQty;

	@Column(name = "mail", nullable = true)
	private String mail;

	@Column(name = "maritalState", nullable = true)
	private MaritalState maritalState;

	public PersonalData() {
		super();
	}

	@Override
	public void addPhone(Phone phone) {
		this.phones.add(phone);
	}

	@Override
	public SortedSet<Address> getAddresses() {
		return addresses;
	}

	@Override
	public LocalDate getBirthDate() {
		return birthDate;
	}

	@Override
	public Integer getChildrenQty() {
		return childrenQty;
	}

	@Override
	public String getCpf() {
		return cpf;
	}

	@Override
	public Gender getGender() {
		return gender;
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
	public MaritalState getMaritalState() {
		return maritalState;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SortedSet<Phone> getPhones() {
		return phones;
	}

	public String getProfession() {
		return profession;
	}

	@Override
	public String getRg() {
		return rg;
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
	public void setAddresses(SortedSet<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public void setChildrenQty(Integer childrenQty) {
		this.childrenQty = childrenQty;
	}

	@Override
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public void setMaritalState(MaritalState maritalState) {
		this.maritalState = maritalState;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Override
	public void setRg(String rg) {
		this.rg = rg;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}


}
