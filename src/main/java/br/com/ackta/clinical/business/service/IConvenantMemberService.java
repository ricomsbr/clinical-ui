package br.com.ackta.clinical.business.service;

import java.util.List;
import java.util.Optional;

import br.com.ackta.clinical.data.entity.Convenant;
import br.com.ackta.clinical.data.entity.IConvenantMember;

public interface IConvenantMemberService {

	Optional<IConvenantMember> findByConvenant(List<IConvenantMember> convenantMembers, Convenant convenant);


}
