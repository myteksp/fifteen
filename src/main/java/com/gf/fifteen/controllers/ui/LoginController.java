package com.gf.fifteen.controllers.ui;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gf.fifteen.entities.dto.user.SignupUserRequest;
import com.gf.fifteen.services.UserService;
import com.gf.util.string.JSON;

@Controller
public final class LoginController {
	private static final String VIEW = "login";
	@Autowired
	public LoginController(final UserService users){
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public final String login(
			final Model model,
			@RequestParam(name="p", required=false, defaultValue="") final String p) throws UnsupportedEncodingException {
		if (p.length() > 1){
			final SignupUserRequest user = JSON.fromJson(new String(Base64.getUrlDecoder().decode(p), "UTF-8"), SignupUserRequest.class);
			model.addAttribute("user", user);
			model.addAttribute("autologin", true);
		}else{
			final SignupUserRequest user = new SignupUserRequest("", "");
			model.addAttribute("user", user);
			model.addAttribute("autologin", false);
		}
		return VIEW;
	}
}
