package io.agileintellignece.ppmtool.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.agileintellignece.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
	
	@Query("select p from Project p where p.projectIdentifier=:projectId")
	public Project findByprojectIdentifier(@Param("projectId") String projectId);
	
	
	 List<Project> findAll();
	

}
