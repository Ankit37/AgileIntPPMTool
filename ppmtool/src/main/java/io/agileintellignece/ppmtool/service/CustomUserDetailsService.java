package io.agileintellignece.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.agileintellignece.ppmtool.Repository.UserRepo;
import io.agileintellignece.ppmtool.domain.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);

		if (user == null) {
			new UsernameNotFoundException("user not found");
		}

		return user;
	}

	
	@Transactional
	public User loadUserById(Long id) {
		User user = userRepo.getById(id);
		if (user == null) {
			new UsernameNotFoundException("user not found");
		}

		return user;
	}

}