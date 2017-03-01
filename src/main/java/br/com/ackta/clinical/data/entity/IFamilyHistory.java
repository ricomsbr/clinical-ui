package br.com.ackta.clinical.data.entity;

import java.util.List;

public interface IFamilyHistory {
	void addMemberHistories(IFamilyMemberHistory memberHistory);

	List<IFamilyMemberHistory> getMemberHistories();
}
