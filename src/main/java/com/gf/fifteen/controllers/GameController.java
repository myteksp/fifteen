package com.gf.fifteen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gf.fifteen.converters.GameConverter;
import com.gf.fifteen.entities.dto.game.CanMoveResponseDTO;
import com.gf.fifteen.entities.dto.game.GameDTO;
import com.gf.fifteen.entities.general.GeneralOptionResponse;
import com.gf.fifteen.exceptions.InvalidMoveAtemtException;
import com.gf.fifteen.services.GameService;

@RestController
public final class GameController {
	private final GameService service;
	private final GameConverter converter;

	@Autowired
	public GameController(final GameService service, final GameConverter converter){
		this.service = service;
		this.converter = converter;
	}


	@GetMapping(path="/game/get", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final GameDTO getGame(@RequestParam(name="id", required=true)final String gameId){
		return converter.convert(service.getGame(gameId));
	}
	
	@GetMapping(path="/game/canMove", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final CanMoveResponseDTO canMove(
			@RequestParam(name="id", required=true)final String gameId,
			@RequestParam(name="num", required=true)final int num){
		return new CanMoveResponseDTO(service.getNextMove(gameId, num));
	}

	@GetMapping(path="/game/reset", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final GameDTO resetGame(@RequestParam(name="id", required=true)final String gameId){
		return converter.convert(service.resetGame(gameId));
	}

	@PostMapping(path="/game/position", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final GeneralOptionResponse<GameDTO> updateGame(
			@RequestParam(name="id", required=true)final String gameId, 
			@RequestBody(required=true)final int[] position){
		try{
			return new GeneralOptionResponse<GameDTO>(converter.convert(service.updatePosition(gameId, position)));
		}catch(final InvalidMoveAtemtException ex){
			return new GeneralOptionResponse<GameDTO>();
		}
	}

	@GetMapping(path="/game/start", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final GameDTO startGame(
			@RequestParam(name="id", required=true)final String gameId, 
			@RequestParam(name="size", required=true)final int size){
		return converter.convert(service.startGame(gameId, size));
	}
}
