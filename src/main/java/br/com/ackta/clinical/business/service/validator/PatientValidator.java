package br.com.ackta.clinical.business.service.validator;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.ackta.clinical.business.service.IPersonalDataService;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PersonalData;

public class PatientValidator implements Validator{

	private static final int AGE_OF_MAJORITY = 18;
//	private static final String NOT_DUPLICATED_RESP_NAME = "NotDuplicatedResponsibleName";
	private static final String NOT_NULL_RESP = "NotNullPatientResponsible";
	private IPersonalDataService personalDataService;

	public PatientValidator(IPersonalDataService personalDataService1) {
		super();
		this.personalDataService = personalDataService1;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Patient.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Patient patient = (Patient) target;
		List<PersonalData> responsibles = patient.getResponsibles();
		
		PersonalData personalData = patient.getPersonalData();
		long age = personalDataService.calculateAge(personalData);
		if ((age < AGE_OF_MAJORITY) && (responsibles.size() == 0)) {
			errors.rejectValue("responsibles", 
					NOT_NULL_RESP, 
					Arrays.array(age), 
					NOT_NULL_RESP);
		}
	}

}
