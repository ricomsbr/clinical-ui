package br.com.ackta.clinical.business.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.business.service.validator.NotDuplicatePatientNameValidator;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;
import br.com.ackta.validation.NonNegativeValidator;
import br.com.ackta.validation.NotBeforeNowValidator;
import br.com.ackta.validation.NotBlankValidator;
import br.com.ackta.validation.NotNullValidator;
import br.com.ackta.validation.NotTooOldValidator;
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
			.append(new NotBlankValidator("name"))
			.append(new NotNullValidator("gender"))
			.append(new NotNullValidator("childrenQty"))
			.append(new NonNegativeValidator("childrenQty"))
			.append(new NotNullValidator("birthDate"))
			.append(new NotBeforeNowValidator("birthDate"))
			.append(new NotTooOldValidator("birthDate", 150))
			.append(new NotDuplicatePatientNameValidator(repository))
//			.append(new NotDuplicatePatientMailValidator(repository))
//			.append(new NotDuplicatePatientCpfValidator(repository))
			.validate();
	}

	@Override
	public void validateName(PersonalData personalData) {
		ValidatorServiceBuilder
		.build(personalData, personalData.getClass().getName())
		.append(new NotBlankValidator("name"))
//		.append(new NotEmptyValidator("phones"))
//		.append(new NotDuplicatePatientNameValidator(repository))
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
