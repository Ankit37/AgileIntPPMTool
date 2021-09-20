package io.agileintellignece.ppmtool.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.agileintellignece.ppmtool.domain.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	
	User findByUsername(String username);
	User getById(Long id);
	
	Optional<User> findById(Long id);
	
}
