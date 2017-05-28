package com.gf.fifteen.controllers.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {
	private static final String VIEW = "error";
	
	@RequestMapping(value="/errorView", method = RequestMethod.GET)
	public final String error(
			@RequestParam(name="message", required=true)final String message,
			final Model model) {
		System.out.println(message);
		model.addAttribute("message", message);
		return VIEW;
	}
}
