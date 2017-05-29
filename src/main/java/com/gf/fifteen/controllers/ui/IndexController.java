package com.gf.fifteen.controllers.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gf.fifteen.entities.dao.game.GameEntity;
import com.gf.fifteen.entities.dao.user.UserEntity;
import com.gf.fifteen.services.GameService;
import com.gf.fifteen.services.UserService;

@Controller
public final class IndexController {
	private static final String VIEW = "index";
	
	private final UserService users;
	private final GameService games;
	
	@Autowired
	public IndexController(final UserService users, final GameService games){
		this.users = users;
		this.games = games;
	}
	

	@RequestMapping(value="/", method = RequestMethod.GET)
	public final String index(final Model model) {
		final UserEntity user = (UserEntity) users
				.loadUserByUsername(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		final GameEntity game = games.getGame(user.gameId);
		model.addAttribute("game", game);
		model.addAttribute("game_id", game == null?"":game.id);
		model.addAttribute("allowedSizes", games.getAllowedGameSizes());
		model.addAttribute("empty", new int[]{});
		return VIEW;
	}
}
