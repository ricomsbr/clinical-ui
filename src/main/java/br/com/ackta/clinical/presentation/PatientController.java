/*
 * RequestController.java		14/10/2015
 *
 * Copyright (C) 2015 Ackta. All Rights Reserved.
 */
package br.com.ackta.clinical.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.ackta.clinical.business.helper.IPatientHelper;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.IPatient;

/**
 *
 *
 * @author RMendonca
 * @version @version@
 * @since @since@
 */
@Controller
@RequestMapping(value = "/patients")
public class PatientController {
	private static Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

	private IPatientHelper helper;


	@Autowired
	public PatientController(IPatientHelper helper1) {
		super();
		this.helper = helper1;
	}


	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id, Model model) {
		LOGGER.info("Method delete initialized.");
		helper.delete(id);
		model.addAttribute("record", new Form());
		model.addAttribute("page", "patient/search");
		return "index";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String goToAdd(final Model model) {
		model.addAttribute("record", new Form());
		model.addAttribute("allGenders", Gender.values());
		model.addAttribute("page", "patient/insert");
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String insert(Form form, Model model) {
		LOGGER.info("Method insert initialized.");
		helper.insert(form);
		model.addAttribute("record", new Form());
		model.addAttribute("page", "patient/search");
		return "index";
	}

	@RequestMapping(value= "/search", method = RequestMethod.POST)
	public String search(Form form, Model model, Pageable pageable) {
		LOGGER.info("Method search initialized.");
		Page<IPatient> patients = helper.search(form, pageable);
		model.addAttribute("list", patients);
		model.addAttribute("page", "patient/list");
		return "index";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showDetails(@PathVariable Long id, final Model model) {
		IPatient patient = helper.findOne(id);
		model.addAttribute("patient", new Form(patient));
		model.addAttribute("allGenders", Gender.values());
		model.addAttribute("page", "patient/details");
		return "index";
	}

	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable Long id, Form form, Model model) {
		LOGGER.info("Method update initialized.");
		helper.update(id, form);
		model.addAttribute("record", new Form());
		model.addAttribute("page", "patient/search");
		return "index";
	}
}
