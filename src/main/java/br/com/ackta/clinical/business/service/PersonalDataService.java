package br.com.ackta.clinical.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.ackta.clinical.business.service.validator.PersonalDataValidator;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;
import br.com.ackta.validation.IsNotBirthDateTooOldValidator;
import br.com.ackta.validation.IsNotDateBeforeNowValidator;
import br.com.ackta.validation.IsNotEmptyOrWhitespaceValidator;
import br.com.ackta.validation.IsNotNegativeValidator;
import br.com.ackta.validation.IsNotNullValidator;
import br.com.ackta.validation.ValidatorServiceBuilder;

@Service
@Transactional
public class PersonalDataService implements IPersonalDataService {

	private PersonalDataRepository repository;

	/**
	 * @param repository
	 */
	@Autowired
	public PersonalDataService(PersonalDataRepository repository1) {
		super();
		this.repository = repository1;
	}

	@Override
	public PersonalData insert(PersonalData personalData) {
		Assert.notNull(personalData.getId());
		validateInsert(personalData);
		return repository.save(personalData);

	}

	public PersonalData save(PersonalData data) { //TODO remover esse método quando criar entidade Responsibles
		return repository.save(data);
	}

	public PersonalData update(PersonalData data) {
		return repository.save(data);
	}

	// TODO remover
	@Override
	public void validateInsert(PersonalData personalData) {
		ValidatorServiceBuilder
			.build(personalData, personalData.getClass().getName())
			.append(new IsNotEmptyOrWhitespaceValidator("name"))
			.append(new IsNotNullValidator("gender"))
			.append(new IsNotNullValidator("childrenQty"))
			.append(new IsNotNegativeValidator("childrenQty"))
			.append(new IsNotNullValidator("birthDate"))
			.append(new IsNotDateBeforeNowValidator("birthDate"))
			.append(new IsNotBirthDateTooOldValidator("birthDate", 150))
			.append(new PersonalDataValidator(repository))
			.validate();
	}

}
