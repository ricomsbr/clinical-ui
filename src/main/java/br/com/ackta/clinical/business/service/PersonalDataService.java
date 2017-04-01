package br.com.ackta.clinical.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import br.com.ackta.clinical.business.service.validator.HasLengthValidator;
import br.com.ackta.clinical.business.service.validator.IsDateAfterNowValidator;
import br.com.ackta.clinical.business.service.validator.IsNotBirthDateTooOldValidator;
import br.com.ackta.clinical.business.service.validator.ValidatorServiceBuilder;
import br.com.ackta.clinical.business.service.validator.ValidatorServiceException;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;

@Service
@Transactional
public class PersonalDataService implements IPersonalDataService {

	private PersonalDataRepository repository;
	private PersonalDataValidator personalDataValidator;

	/**
	 * @param repository
	 */
	@Autowired
	public PersonalDataService(PersonalDataRepository repository1,
			PersonalDataValidator personalDataValidator1) {
		super();
		this.repository = repository1;
		this.personalDataValidator = personalDataValidator1;
	}

	public PersonalData save(PersonalData data) { //TODO remover esse método quando criar entidade Responsibles
		return repository.save(data);
	}

	public PersonalData update(PersonalData data) {
		return repository.save(data);
	}

	@Override
	public PersonalData insert(PersonalData personalData) {
		Assert.notNull(personalData.getId());
		validateInsertOld(personalData);
		return repository.save(personalData);

	}
	
	// TODO remover
	@Override 
	public void validateInsert(PersonalData personalData) {
		ValidatorServiceBuilder
		.build(personalData, personalData.getClass().getName())
		.append(new IsDateAfterNowValidator("birthDate"))
		.append(new IsNotBirthDateTooOldValidator("birthDate", 150))
		.append(new HasLengthValidator("name"))
		.validate();
	}

	// TODO remover
	public void validateInsertOld(PersonalData personalData) {
		BindException bindException = new BindException(personalData, personalData.getClass().getName());
		ValidationUtils.invokeValidator(personalDataValidator, personalData, bindException );
		if(!bindException.getAllErrors().isEmpty()) {
			throw new ValidatorServiceException(bindException);
		}
	}

}
