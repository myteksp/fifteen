package com.gf.fifteen.controllers.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gf.fifteen.entities.user.UserEntity;
import com.gf.fifteen.services.GameService;
import com.gf.fifteen.services.UserService;

@Controller
public final class LobbyController {
	private static final String VIEW = "lobby";
	
	private final UserService users;
	private final GameService games;
	
	@Autowired
	public LobbyController(final UserService users, final GameService games){
		this.users = users;
		this.games = games;
	}
	
	@RequestMapping(value="/lobby", method = RequestMethod.GET)
	public final String lobby(final Model model) {
		final UserEntity user = (UserEntity) users
				.loadUserByUsername(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		model.addAttribute("game", games.getGame(user.gameId));
		return VIEW;
	}
}
