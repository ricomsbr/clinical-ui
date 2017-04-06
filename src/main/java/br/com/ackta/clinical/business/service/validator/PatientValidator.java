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
		responsibles.forEach(d -> {
			String name = d.getName();
			if (personalDataService.findByNameIgnoreCase(name).isPresent()) {
				String errorCode1 = "responsable_name.already_exists";
				errors.rejectValue("responsable_name", errorCode1, Arrays.array(name), errorCode1);
			}
		});
		
		PersonalData personalData = patient.getPersonalData();
		if ((personalDataService.calculateAge(personalData) < AGE_OF_MAJORITY) && (responsibles.size() == 0)) {
			String errorCode2 = "responsibles.is_empty";
			errors.rejectValue("responsibles", errorCode2, errorCode2);
		}
	}

}
