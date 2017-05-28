package com.gf.fifteen.controllers.ui;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gf.fifteen.entities.UserEntity;
import com.gf.fifteen.services.UserService;

@Controller
public final class IndexController {
	private static final String VIEW = "index";
	
	private final UserService users;
	
	@Autowired
	public IndexController(final UserService users){
		this.users = users;
	}
	

	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public final String index(final Model model) {
		final UserEntity user = (UserEntity) users
				.loadUserByUsername(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		
		model.addAttribute("message", "");
		return VIEW;
	}
}
