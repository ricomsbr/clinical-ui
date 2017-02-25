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
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "personal_data")
@SQLDelete(sql = "UPDATE personal_data SET deleted = 1 WHERE id = ? AND version = ?")
@Where(clause="deleted = 0")
public class PersonalData implements IPersonalData {

	private static final long serialVersionUID = -2383673733659048451L;

//TODO	Comparator<IAddress> comparator = new Comparator<IAddress>() {
//
//		@Override
//		public int compare(IAddress o1, IAddress o2) {
//			return o1.getIndex().compareTo(o2.getIndex());
//		}
//
//	};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "birth_dt", nullable = false)
	private LocalDate birthDate;

	@Column(name = "gender_id", nullable = true)
	private Gender gender;

	@Column(name = "cpf", nullable = true)
	private String cpf;

	@Column(name = "rg", nullable = true)
	private String rg;

	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Address.class, mappedBy="personalData", orphanRemoval=true)
//	@JoinTable(name="address",
//		joinColumns={@JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)}
//TODO	)
	@OrderBy("index DESC")
	private SortedSet<Address> addresses = new TreeSet<Address>();

	@Column(name = "children_qty", nullable = true)
	private Integer childrenQty;

	public PersonalData() {
		super();
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
	public String getName() {
		return name;
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

	/**
	 * Updates a binded object.
	 *
	 * @param personalData
	 * @return
	 */
	@Override
	public IPersonalData merge(IPersonalData personalData) {
		BeanUtils.copyProperties(this, personalData, UNMERGED_PROPERTIES);

		return personalData;
	}

	public void setAddresses(SortedSet<Address> addresses) {
		this.addresses = addresses;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setChildrenQty(Integer childrenQty) {
		this.childrenQty = childrenQty;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}


}
