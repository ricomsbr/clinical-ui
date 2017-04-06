package br.com.ackta.clinical.business.service;

import java.time.LocalDate;
import java.util.Optional;

import br.com.ackta.clinical.data.entity.PersonalData;

public interface IPersonalDataService {

	PersonalData validateAndSave(PersonalData personalData);
	
	PersonalData save(PersonalData personalData);

	void validate(PersonalData personalData);

	Optional<PersonalData> findByNameIgnoreCase(String name);

	Optional<PersonalData> findByMailIgnoreCase(String mail);

	Optional<PersonalData> findByCpf(String cpf);

	long calculateAge(PersonalData personalData);

	long calculateAge(PersonalData personalData, LocalDate currentDate);

	void validateName(PersonalData personalData);

}
