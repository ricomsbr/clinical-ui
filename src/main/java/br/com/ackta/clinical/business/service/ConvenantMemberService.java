package br.com.ackta.clinical.business.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.Convenant;
import br.com.ackta.clinical.data.entity.IConvenantMember;

@Service
@Transactional
public class ConvenantMemberService implements IConvenantMemberService {

	@Override
	public Optional<IConvenantMember> findByConvenant(List<IConvenantMember> convenantMembers, Convenant convenant) {
		Optional<IConvenantMember> result = convenantMembers.stream()
			.filter(c -> c.getConvenantKey().equals(convenant))
			.findAny();
		return result;
	}

}
