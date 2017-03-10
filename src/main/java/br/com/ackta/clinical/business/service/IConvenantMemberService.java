package br.com.ackta.clinical.business.service;

import java.util.List;
import java.util.Optional;

import br.com.ackta.clinical.data.entity.Convenant;
import br.com.ackta.clinical.data.entity.ConvenantMember;

public interface IConvenantMemberService {

	Optional<ConvenantMember> findByConvenant(List<ConvenantMember> convenantMembers, Convenant convenant);


}
