package br.com.ackta.clinical.business.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.business.service.validator.PersonalDataCpfValidator;
import br.com.ackta.clinical.business.service.validator.PersonalDataMailValidator;
import br.com.ackta.clinical.business.service.validator.PersonalDataNameValidator;
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
	public PersonalData validateAndSave(PersonalData personalData) {
		validate(personalData);
		return repository.save(personalData);
	}

	@Override
	public PersonalData save(PersonalData data) { 
		return repository.save(data);
	}

	@Override
	public void validate(PersonalData personalData) {
		ValidatorServiceBuilder
			.build(personalData, personalData.getClass().getName())
			.append(new IsNotEmptyOrWhitespaceValidator("name"))
			.append(new IsNotNullValidator("gender"))
			.append(new IsNotNullValidator("childrenQty"))
			.append(new IsNotNegativeValidator("childrenQty"))
			.append(new IsNotNullValidator("birthDate"))
			.append(new IsNotDateBeforeNowValidator("birthDate"))
			.append(new IsNotBirthDateTooOldValidator("birthDate", 150))
			.append(new PersonalDataNameValidator(repository))
			.append(new PersonalDataMailValidator(repository))
			.append(new PersonalDataCpfValidator(repository))
			.validate();
	}

	@Override
	public Optional<PersonalData> findByNameIgnoreCase(String name) {
		return repository.findByNameIgnoreCase(name);
	}

	@Override
	public Optional<PersonalData> findByMailIgnoreCase(String mail) {
		return repository.findByMailIgnoreCase(mail);
	}

	@Override
	public Optional<PersonalData> findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	@Override
	public void validateName(PersonalData personalData) {
		ValidatorServiceBuilder
			.build(personalData, personalData.getClass().getName())
			.append(new IsNotEmptyOrWhitespaceValidator("name"))
			.append(new PersonalDataNameValidator(repository))
			.validate();
	}

	@Override
	public long calculateAge(PersonalData personalData) {
		return calculateAge(personalData, LocalDate.now());
	}

	@Override
	public long calculateAge(PersonalData personalData, LocalDate currentDate) {
		Period period = Period.between(personalData.getBirthDate(), currentDate);
		long result = period.getYears();
		return result;
	}

}
