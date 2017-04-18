package br.com.ackta.clinical.business.service.validator;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PatientRepository;

@Service
public class NotDuplicatePatientNameValidator implements Validator {

	private static final String ERROR_CODE_PREFIX = "NotDuplicatedPatientName";
	private static final String DEFAULT_FIELD_NAME = "name";
	private PatientRepository repository;

	@Autowired
	public NotDuplicatePatientNameValidator(PatientRepository repository1) {
		super();
		this.repository = repository1;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Patient.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Patient patient = (Patient) target;
		PersonalData personalData = patient.getPersonalData();
		String name = personalData.getName();
		if (name != null && !name.isEmpty()) {
			List<Patient> findResult = repository.findByPersonalData_NameIgnoreCase(name);
			boolean hasDuplicated = findResult
					.stream()
					.filter(d -> !d.getId().equals(patient.getId()))
					.count() > 0;
			if (hasDuplicated) {
				errors.rejectValue(DEFAULT_FIELD_NAME,
						ERROR_CODE_PREFIX,
						Arrays.array(name),
						ERROR_CODE_PREFIX);
			}
		}
	}
}
