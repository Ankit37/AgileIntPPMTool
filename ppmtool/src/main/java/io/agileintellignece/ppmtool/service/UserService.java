package io.agileintellignece.ppmtool.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.agileintellignece.ppmtool.Repository.UserRepo;
import io.agileintellignece.ppmtool.domain.User;
import io.agileintellignece.ppmtool.exceptions.UserNameAlreadyExistsException;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser)
	{
		try {
		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
		newUser.setUsername(newUser.getUsername());
		
		return userRepo.save(newUser);
		}
		catch(Exception ex)
		{
			throw new UserNameAlreadyExistsException("Username " + newUser.getUsername()+" Already exists"); 
		}
		
				
	}
}
