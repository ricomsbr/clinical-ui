package br.com.ackta.clinical.business.service;

import java.time.LocalDate;

import br.com.ackta.clinical.data.entity.PersonalData;

public interface IPersonalDataService {

	PersonalData validateAndSave(PersonalData personalData);
	
	PersonalData save(PersonalData personalData);

	void validate(PersonalData personalData);

	long calculateAge(PersonalData personalData);

	long calculateAge(PersonalData personalData, LocalDate currentDate);

}
