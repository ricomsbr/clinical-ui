package br.com.ackta.clinical.business.service;

import br.com.ackta.clinical.data.entity.PersonalData;

public interface IPersonalDataService {

	PersonalData insert(PersonalData personalData);

	void validateInsert(PersonalData personalData);

}
