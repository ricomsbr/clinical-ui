package br.com.ackta.clinical.business.service;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;

@Service
public class PersonalDateValidator implements Validator {

	private static final long MAX_AGE = 200;

	private PersonalDataRepository repository;

	@Autowired
	public PersonalDateValidator(PersonalDataRepository repository1) {
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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.empty");
        ValidationUtils.rejectIfEmpty(errors, "birthDate", "birthDate.empty");
        if (Objects.nonNull(p.getBirthDate())) {
        	if (LocalDate.now().isBefore(p.getBirthDate())) {
	            errors.rejectValue("birthDate", "birthDate.after_now");
	        } else if (LocalDate.now().minusYears(MAX_AGE).isAfter(p.getBirthDate())) {
	        	errors.rejectValue("birthDate", "birthDate.too_old");
	        }
        }
        ValidationUtils.rejectIfEmpty(errors, "childrenQty", "childrenQty.empty");
        if (Objects.nonNull(p.getChildrenQty()) && p.getChildrenQty() < 0) {
            errors.rejectValue("childrenQty", "childrenQty.negative");
        }
		if (repository.findFirstByCpf(p.getCpf()).isPresent()) {
			errors.rejectValue("cpf", "cpf.repeated");
		}
		if (repository.findFirstByName(p.getName()).isPresent()) {
			errors.rejectValue("name", "name.repeated");
		}
		if (repository.findFirstByMail(p.getMail()).isPresent()) {
			errors.rejectValue("mail", "mail.repeated");
		}
	}


}
