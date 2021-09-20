package io.agileintellignece.ppmtool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.agileintellignece.ppmtool.domain.User;
import io.agileintellignece.ppmtool.payload.JWTLoginSucessResponse;
import io.agileintellignece.ppmtool.payload.LoginRequest;
import io.agileintellignece.ppmtool.security.JwtTokenProvider;
import io.agileintellignece.ppmtool.service.MapValidationErrorService;
import io.agileintellignece.ppmtool.service.UserService;
import io.agileintellignece.ppmtool.validator.UserValidator;
import static io.agileintellignece.ppmtool.security.SecurityConstant.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
		userValidator.validate(user, result);

		ResponseEntity<?> errormap = mapValidationErrorService.MapValidationService(result);
		if (errormap != null)
			return errormap;

		User newUser = userService.saveUser(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result)
	{ResponseEntity<?> errormap = mapValidationErrorService.MapValidationService(result);
	if (errormap != null)
		return errormap;

	
	Authentication authentication = authenticationManager.
			authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())); 
	
	SecurityContextHolder.getContext().setAuthentication(authentication);
	String jwt= TOKEN_PREFIX + tokenProvider.generateToken(authentication);
	
	 return ResponseEntity.ok(new JWTLoginSucessResponse(true, jwt));
	}
	
}
