package br.com.ackta.clinical.presentation;

import br.com.ackta.clinical.data.entity.FamilyMemberHistory;

public class FamilyMemberHistoryForm extends FamilyMemberHistory {
	private Integer age;

	public FamilyMemberHistoryForm() {
		super();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
