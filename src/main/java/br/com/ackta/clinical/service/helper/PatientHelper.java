package br.com.ackta.clinical.service.helper;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.model.entity.IPatient;
import br.com.ackta.clinical.model.entity.Patient;
import br.com.ackta.clinical.model.entity.PersonalData;
import br.com.ackta.clinical.model.repository.IPatientDao;
import br.com.ackta.clinical.model.repository.PatientRepository;
import br.com.ackta.clinical.presentation.Form;

@Service
@Transactional
public class PatientHelper implements IPatientHelper {
	private IPatientDao patientDao;
	PatientRepository patientRepository;
	
	/**
	 * @param dao
	 */
	public PatientHelper(IPatientDao dao, PatientRepository repository) {
		super();
		this.patientDao = dao;
		this.patientRepository = repository;
	}
	
	@Override
	public IPatient insert(Form form) {
		PersonalData data = new PersonalData();
		BeanUtils.copyProperties(form, data);
		Patient patient = new Patient(data);
//		IPatient result = patientDao.insert(patient);
		IPatient result = patientRepository.save(patient);
		return result;
	}
	
	@Override
	public IPatient update(Long id, Form form) {
		PersonalData data = new PersonalData();
		BeanUtils.copyProperties(form, data);
		
		Patient probe = new Patient(data);
		probe.setId(id);
		IPatient result = patientDao.update(probe);
		return result;
	}

	@Override
	public Page<IPatient> search(Form form, Pageable pageable) {
		PersonalData data = new PersonalData();
		
		String cpf = form.getCpf();
		if(!cpf.isEmpty()) {
			data.setCpf(cpf);
		}
		String name = form.getName();
		if (!name.isEmpty()) {
			data.setName(name);
		}
		LocalDate birthDate = form.getBirthDate();
		if (birthDate != null) {
			data.setBirthDate(birthDate);
		}
		Patient probe = new Patient(data);
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreCase()
				.withMatcher("personalData.name", match -> match.stringMatcher(StringMatcher.CONTAINING));
		Example<Patient> example = Example.of(probe, matcher);
		
		Page<IPatient> result = findAll(example, pageable);

		return result;
	}
	
	private Page<IPatient> findAll(Example<Patient> example, Pageable pageable) {
		
		@SuppressWarnings("unchecked")
		Page<IPatient> result = (Page<IPatient>)(Page<?>) patientRepository.findAll(example, pageable);
		return result;
	}

	@Override
	public IPatient findOne(Long id) {
		Optional<IPatient> result = patientDao.findOne(id);
		return result.get();
	}
}
