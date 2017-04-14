/*
 * RequestController.java		14/10/2015
 *
 * Copyright (C) 2015 Ackta. All Rights Reserved.
 */
package br.com.ackta.clinical.presentation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.ackta.clinical.business.helper.IPatientHelper;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.Kinship;
import br.com.ackta.clinical.data.entity.MaritalState;
import br.com.ackta.clinical.data.entity.MedicalHistory;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PeriodicityUnit;
import br.com.ackta.validation.ValidatorServiceException;

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
	private static final String DOT = ".";
	private static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	private static Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

	private IPatientHelper helper;

	@Autowired
	public PatientController(IPatientHelper helper1) {
		super();
		this.helper = helper1;
	}

	/**
	 * @param bindingResult
	 * @param ex
	 */
	private void bindExceptionErrors(String objectName, BindingResult bindingResult, ValidatorServiceException ex) {
		ex.getErrors().getFieldErrors()
			.forEach(error -> {
				String str = error.getCode() +  DOT + objectName + DOT + error.getField();
				ArrayList<String> list = new ArrayList<String>();
				list.add(str);
				list.addAll(Lists.newArrayList(error.getCodes()));
			FieldError fieldError = new FieldError(objectName,
					error.getObjectName(),
					error.getRejectedValue(),
					false,
					list.toArray(new String[]{}),
					error.getArguments(),
					error.getDefaultMessage());
			bindingResult.addError(fieldError);
		});
		ex.getErrors().getGlobalErrors()
			.forEach(error -> bindingResult.addError(error));
	}

	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id, Model model) {
		LOGGER.info("Method delete initialized.");
		helper.delete(id);
		model.addAttribute("form", new Form());
		model.addAttribute("page", "patient/search");
		return "index";
	}

	@RequestMapping(value = "/{id}/report", method = RequestMethod.GET)
    public void generatePdf(@PathVariable Long id, Model model, HttpServletResponse response) {
        LOGGER.info(String.format("Method geraPlanilha initialized"));
        byte[] pdfBytes = helper.generatePdf(id);
		model.addAttribute("form", new Form());
		model.addAttribute("page", "patient/search");

        response.setContentType("application/pdf");
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS));
        response.setHeader("Content-Disposition", "inline; filename=" + fileName);
        ServletOutputStream outStream;
		try {
			outStream = response.getOutputStream();
	        outStream.write(pdfBytes);
	        outStream.close();
	        outStream.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String goToAdd(final Model model) {
		LOGGER.info("Method goToAdd initialized");
		Form form = new Form();
		MedicalHistory medicalHistory = new MedicalHistory();
		FamilyMemberHistoryForm motherHistory = new FamilyMemberHistoryForm();
		motherHistory.setKinship(Kinship.MOTHER);
		form.setMotherHistory(motherHistory);
		FamilyMemberHistoryForm fatherHistory = new FamilyMemberHistoryForm();
		fatherHistory.setKinship(Kinship.FATHER);
		form.setFatherHistory(fatherHistory);

		form.setMedicalHistory(medicalHistory);
		model.addAttribute("form", form);
		model.addAttribute("allGenders", Gender.values());
		model.addAttribute("allMaritalStates", MaritalState.values());
		model.addAttribute("allPeriodicityUnits", PeriodicityUnit.values() );
		model.addAttribute("page", "patient/insert");
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String insert(@Valid Form form, BindingResult bindingResult, Model model) {
		LOGGER.info("Method insert initialized.");
		if (!bindingResult.hasErrors()) {
			try {
				helper.insert(form);
				model.addAttribute("form", new Form());
				model.addAttribute("page", "patient/search");
			} catch (ValidatorServiceException ex) {
				model.addAttribute("form", form);
				model.addAttribute("allGenders", Gender.values());
				model.addAttribute("allMaritalStates", MaritalState.values());
				model.addAttribute("allPeriodicityUnits", PeriodicityUnit.values() );
				model.addAttribute("page", "patient/insert");
				bindExceptionErrors("form", bindingResult, ex);
			}
		} else {
			model.addAttribute("form", form);
			model.addAttribute("allGenders", Gender.values());
			model.addAttribute("allMaritalStates", MaritalState.values());
			model.addAttribute("allPeriodicityUnits", PeriodicityUnit.values() );
			model.addAttribute("page", "patient/insert");
		}
		return "index";
	}

	@RequestMapping(value= "/search", method = RequestMethod.POST)
	public String search(Form form, Model model, Pageable pageable) {
		LOGGER.info("Method search initialized.");
		Page<Patient> patients = helper.search(form, pageable);
		model.addAttribute("list", patients);
		model.addAttribute("page", "patient/list");
		return "index";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showDetails(@PathVariable Long id, final Model model) {
		LOGGER.info(String.format("Method showDetails initialized for id = %1s.", id));
		Form form = helper.findOne(id);
		model.addAttribute("form", form);
		model.addAttribute("allGenders", Gender.values());
		model.addAttribute("allMaritalStates", MaritalState.values());
		model.addAttribute("allPeriodicityUnits", PeriodicityUnit.values() );
		model.addAttribute("page", "patient/details");
		return "index";
	}


    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable Long id, Form form, Model model, BindingResult bindingResult) {
		LOGGER.info("Method update initialized.");
		if (!bindingResult.hasErrors()) {
			try {
				helper.update(id, form);
				model.addAttribute("form", new Form());
				model.addAttribute("page", "patient/search");
			} catch (ValidatorServiceException ex) {
				model.addAttribute("form", form);
				model.addAttribute("allGenders", Gender.values());
				model.addAttribute("allMaritalStates", MaritalState.values());
				model.addAttribute("allPeriodicityUnits", PeriodicityUnit.values() );
				model.addAttribute("page", "patient/details");
				bindExceptionErrors("form", bindingResult, ex);
			}
		} else {
			model.addAttribute("form", form);
			model.addAttribute("allGenders", Gender.values());
			model.addAttribute("allMaritalStates", MaritalState.values());
			model.addAttribute("allPeriodicityUnits", PeriodicityUnit.values() );
			model.addAttribute("page", "patient/details");
		}
		return "index";
	}

}
