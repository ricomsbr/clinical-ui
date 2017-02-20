/*
 * RequestController.java		14/10/2015
 *
 * Copyright (C) 2015 Ackta. All Rights Reserved.
 */
package br.com.ackta.clinical.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.ackta.clinical.model.entity.Gender;
import br.com.ackta.clinical.model.entity.Patient;
import br.com.ackta.clinical.model.entity.PersonalData;
import br.com.ackta.clinical.model.repository.PatientRepository;
import br.com.ackta.clinical.model.repository.PersonalDataRepository;

/**
 *
 *
 * @author RMendonca
 * @version @version@
 * @since @since@
 */
@Controller
@RequestMapping(value = "/patient")
public class PatientController {
	private static Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

	private PatientRepository patientRepository;
	private PersonalDataRepository personalDataRepository;

	@Autowired
	public PatientController(PatientRepository patientRepository1, PersonalDataRepository personalDataRepository1) {
		super();
		this.patientRepository = patientRepository1;
		this.personalDataRepository = personalDataRepository1;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String add(Form form, Model model) {
		LOGGER.info("Method add initialized.");

		PersonalData data = new PersonalData();
//		data.setBirthDate(LocalDate.parse(text, formatter)(form.getBirthDate()));
		data.setCpf(form.getCpf());
		data.setGender(Gender.MALE);
		data.setName(form.getName());
		data.setRg("rg1");
		Patient patient = new Patient(data);
		personalDataRepository.save(data);
		patientRepository.save(patient);
		model.addAttribute("record", new Form());
		model.addAttribute("page", "patient/search");
		return "index";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String goToAdd(final Model model) {
		model.addAttribute("record", new Form());
		model.addAttribute("page", "patient/add");
		return "index";
	}

	@RequestMapping(value= "/search", method = RequestMethod.POST)
	public String search(Form form, Model model, Pageable pageable) {
		LOGGER.info("Method search initialized.");
		Patient probe = new Patient();
//		probe.setCpf(form.getCpf());
//		probe.setName(form.getName());
		Example<Patient> example = Example.of(probe);
		Page<Patient> patients = patientRepository.findAll(example, pageable);
		model.addAttribute("list", patients);
		model.addAttribute("page", "patient/list");
		return "index";
	}
}
