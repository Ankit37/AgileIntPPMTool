package io.agileintellignece.ppmtool.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.agileintellignece.ppmtool.domain.ProjectTask;

@Repository
public interface ProjectTaskRepo  extends JpaRepository<ProjectTask, Long>{

	 List<ProjectTask> findByProjectIdentifierOrderByPriority(String backlog_id) ;
	 
	 ProjectTask findByProjectSequence(String sequence);
}
