package br.com.ackta.clinical.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;

@Service
@Transactional
public class PersonalDataService implements IPersonalDataService {

	private PersonalDataRepository repository;
	private PersonalDateValidator personalDataValidator;

	/**
	 * @param repository
	 */
	@Autowired
	public PersonalDataService(PersonalDataRepository repository1,
			PersonalDateValidator personalDataValidator1) {
		super();
		this.repository = repository1;
		this.personalDataValidator = personalDataValidator1;
	}





	@Override
	public PersonalData insert(PersonalData personalData) {
		validateInsert(personalData);
		return repository.save(personalData);

	}

	public PersonalData save(PersonalData data) { //TODO remover esse método quando criar entidade Responsibles
		return repository.save(data);
	}

	public PersonalData update(PersonalData data) {
		return repository.save(data);
	}

	@Override
	public void validateInsert(PersonalData personalData) {
		Assert.notNull(personalData.getId());
		BindException bindException = new BindException(personalData, personalData.getClass().getName());
		ValidationUtils.invokeValidator(personalDataValidator, personalData, bindException );
		if(!bindException.getAllErrors().isEmpty()) {
			throw new ServiceException(bindException);
		}
	}

}
