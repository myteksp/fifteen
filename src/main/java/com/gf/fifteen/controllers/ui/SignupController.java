package com.gf.fifteen.controllers.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gf.fifteen.services.UserService;

@Controller
public final class SignupController {
	private static final String VIEW = "signup";
	@Autowired
	public SignupController(final UserService users){
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public final String login(final Model model) {
		return VIEW;
	}
}
