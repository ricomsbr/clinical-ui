package br.com.ackta.clinical.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.Responsible;
import br.com.ackta.clinical.data.repository.ResponsibleRepository;
import br.com.ackta.validation.NotBlankValidator;
import br.com.ackta.validation.ValidatorServiceBuilder;

@Service
@Transactional
public class ResponsibleService implements IResponsibleService {

	private ResponsibleRepository repository;
	
	@Autowired
	public ResponsibleService(ResponsibleRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Responsible save(Responsible responsible) {
		validateName(responsible);
		return repository.save(responsible);
	}

	private void validateName(Responsible responsible) {
		ValidatorServiceBuilder
			.build(responsible, responsible.getClass().getName())
			.append(new NotBlankValidator("name"))
			.validate();
	}
}
