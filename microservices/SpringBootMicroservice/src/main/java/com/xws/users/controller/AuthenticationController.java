package com.xws.users.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.xws.users.dto.*;
import com.xws.users.service.IAgentService;
import com.xws.users.service.IRegularUserService;
import com.xws.users.users.model.roles.Agent;
import com.xws.users.users.model.roles.RegularUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xws.users.service.IRegularUserRegistrationService;
import com.xws.users.service.IUserService;
import com.xws.users.users.model.roles.UserAccount;

import java.util.List;

// Controller in charge of user authentication
@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

	@Autowired
	private Environment env;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRegularUserRegistrationService regularUserRegistrationService;

	@Autowired
	private IRegularUserService regularUserService;

	@Autowired
	private IAgentService agentService;

	// The first endpoint that affects the user when logging in.
	// Then he only knows his username and password and forwards it to the backend.
	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(
			@RequestBody @Valid JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

		UserTokenState token = userService.logIn(authenticationRequest.getUsername(),
				authenticationRequest.getPassword());

		return ResponseEntity.ok(token);
	}

	// Endpoint to register a new user
	@PostMapping("/signup")
	public ResponseEntity<UserAccount> addUser(@RequestBody @Valid UserRegisterDTO userRequest) {
		UserAccount addedAccount = regularUserRegistrationService.registerRegularUser(userRequest);
		addedAccount.setPassword(null);
		return new ResponseEntity<>(addedAccount, HttpStatus.CREATED);
	}

	// TODO: Add access only to the administrator
	@PostMapping("/deactivate")
	public ResponseEntity<RegularUser> deactivateUser(@RequestBody @Valid AgentAcceptRequestDTO usernameDTO) {
		RegularUser acceptedAccount = regularUserService.deactivateUser(usernameDTO.getUsername());
		acceptedAccount.setPassword(null);
		return new ResponseEntity<>(acceptedAccount, HttpStatus.CREATED);
	}


	// Endpoint to register a new user
	@PostMapping("/agent/signup")
	public ResponseEntity<Agent> addAgent(@RequestBody @Valid AgentRegisterDTO userRequest) {
		Agent addedAccount = agentService.registerAgent(userRequest);
		addedAccount.setPassword(null);
		return new ResponseEntity<>(addedAccount, HttpStatus.CREATED);
	}
	// TODO: Add access only to the administrator
	@GetMapping("/agent/request")
	public ResponseEntity<List<Agent>> getAllAgentRegistrationRequest() {
		return new ResponseEntity<>(agentService.findAllAgentRequestRegistration(), HttpStatus.CREATED);
	}

	// TODO: Add access only to the administrator
	@PostMapping("/agent/accept")
	public ResponseEntity<Agent> acceptAgentRequest(@RequestBody @Valid AgentAcceptRequestDTO usernameDTO) {
		Agent acceptedAccount = agentService.acceptRequest(usernameDTO.getUsername());
		acceptedAccount.setPassword(null);
		return new ResponseEntity<>(acceptedAccount, HttpStatus.CREATED);
	}

}
