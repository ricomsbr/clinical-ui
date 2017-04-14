package br.com.ackta.clinical.business.service.validator;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;

@Service
public class NotDuplicatePatientCpfValidator implements Validator {

	private static final String ERROR_CODE_PREFIX = "NotDuplicatePatientCpf";
	private static final String DEFAULT_FIELD_NAME = "cpf";
	private PersonalDataRepository repository;

	@Autowired
	public NotDuplicatePatientCpfValidator(PersonalDataRepository repository1) {
		super();
		this.repository = repository1;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return PersonalData.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PersonalData p = (PersonalData) target;
		String cpf = p.getCpf();
		if (repository.findFirstByCpf(cpf).isPresent()) {
			errors.rejectValue(DEFAULT_FIELD_NAME,
					ERROR_CODE_PREFIX,
					Arrays.array(cpf),
					ERROR_CODE_PREFIX);
		}
	}
}
