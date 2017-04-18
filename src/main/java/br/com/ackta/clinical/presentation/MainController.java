/*
 * RequestController.java		14/10/2015
 *
 * Copyright (C) 2015 Ackta. All Rights Reserved.
 */
package br.com.ackta.clinical.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.ackta.clinical.data.entity.Gender;

/**
 *
 *
 * @author RMendonca
 * @version @version@
 * @since @since@
 */
@Controller
@RequestMapping(value = "/")
public class MainController {
	private static Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	public MainController() {
		super();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getIndex(
			final Model model) {
		LOGGER.info("Method getIndex initialized.");
		model.addAttribute("allGenders", Gender.values());
		model.addAttribute("page", "patient/search");
		return "index";
	}

}
