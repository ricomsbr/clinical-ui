package br.com.ackta.clinical.data.entity;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.assertj.core.util.Lists;

@Embeddable
public class FamilyHistory implements IFamilyHistory {

	@Embedded
	private List<FamilyMemberHistory> memberHistories = Lists.newArrayList();

	public FamilyHistory() {
		super();
	}

	@Override
	public void addMemberHistories(FamilyMemberHistory memberHistory) {
		memberHistories.add(memberHistory);
	}

	@Override
	public List<FamilyMemberHistory> getMemberHistories() {
		return memberHistories;
	}

}
