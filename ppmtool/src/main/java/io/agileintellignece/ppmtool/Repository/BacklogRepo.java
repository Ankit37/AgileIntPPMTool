package io.agileintellignece.ppmtool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import io.agileintellignece.ppmtool.domain.Backlog;
@Repository
public interface BacklogRepo  extends   JpaRepository<Backlog, Long>{

	
	
	Backlog findByProjectIdentifier(String Identifier);
}
