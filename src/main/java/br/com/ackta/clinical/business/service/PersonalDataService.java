package br.com.ackta.clinical.business.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import br.com.ackta.clinical.business.service.validator.IdValidator;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;

@Service
@Transactional
public class PersonalDataService implements IPersonalDataService {

	private PersonalDataRepository repository;
	private IdValidator idValidator;

	/**
	 * @param repository
	 */
	@Autowired
	public PersonalDataService(PersonalDataRepository repository) {
		super();
		this.repository = repository;
	}




	@Override
	public PersonalData insert(PersonalData personalData) {
		validateInsert(personalData);
		return repository.save(personalData);

	}

	public PersonalData update(PersonalData data) {
		return repository.save(data);
	}

	@Override
	public void validateInsert(PersonalData personalData) {
		BindException bindException = new BindException(personalData, personalData.getClass().getName());
		ValidationUtils.invokeValidator(idValidator, personalData, bindException );
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty"); // TODO fazer a valicação de data de nascimento, gender, et

		if (Objects.nonNull(personalData.getId())) {
			throw new RuntimeException("Id should be null");
		}
		Objects.requireNonNull(personalData.getBirthDate(), "BirthDate should not be null");
		Objects.requireNonNull(personalData.getName(), "Name should not be null");
		Objects.requireNonNull(personalData.getGender(), "Gender should not be null");
		Integer childrenQty = personalData.getChildrenQty();
		Objects.requireNonNull(childrenQty, "ChildrenQty should not be null");
		if (childrenQty < 0) {
			throw new RuntimeException("ChildrenQty should be a nonNegative value");
		}
		PersonalData repeatedElement = repository.findByNameOrCpfOrMail(personalData.getName(),
				personalData.getCpf(), personalData.getMail());
		if (Objects.nonNull(repeatedElement)) {
			throw new RuntimeException("Name, CPF or email already registered.");
		}
	}



}
