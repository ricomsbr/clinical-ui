package br.com.ackta.clinical.business.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.Convenant;
import br.com.ackta.clinical.data.entity.ConvenantMember;

@Service
@Transactional
public class ConvenantMemberService implements IConvenantMemberService {

	@Override
	public Optional<ConvenantMember> findByConvenant(List<ConvenantMember> convenantMembers, Convenant convenant) {
		Optional<ConvenantMember> result = convenantMembers.stream()
			.filter(c -> c.getConvenant().equals(convenant))
			.findAny();
		return result;
	}

}
