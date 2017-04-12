package br.com.ackta.clinical.presentation;

import java.time.Year;
import java.util.Objects;

import br.com.ackta.clinical.data.entity.FamilyMember;

public class FamilyMemberHistoryForm extends FamilyMember {
	private Integer age;

	public FamilyMemberHistoryForm() {
		super();
	}

	public FamilyMemberHistoryForm(FamilyMember familyMember) {
		this();
		Year birthYear = familyMember.getBirthYear();
		if (Objects.nonNull(birthYear)) {
			this.age = Year.now().minusYears(birthYear.getValue()).getValue();
		}
		this.setAlive(familyMember.getAlive());
		this.setBirthYear(birthYear);
		this.setDiseases(familyMember.getDiseases());
		this.setKinship(familyMember.getKinship());
		this.setMedicines(familyMember.getMedicines());
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
