package io.agileintellignece.ppmtool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import io.agileintellignece.ppmtool.Repository.ProjectRepository;
import io.agileintellignece.ppmtool.domain.Project;
import io.agileintellignece.ppmtool.exceptions.ProjectIdException;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepo;

	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepo.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project Id " + project.getProjectIdentifier().toUpperCase() + " already exists");
		}

	}
	
	
	public Project findProjectByIdentifier( String projectId)
	{
		Project project = projectRepo.findByprojectIdentifier(projectId.trim().toUpperCase());
		if(project==null)
		{
			throw new ProjectIdException("Project does"+projectId+" not exists");
		}
		return project;
	}
	
	
	public List<Project> findAllProject()
	{
		return projectRepo.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectId)
	{
		Project project = projectRepo.findByprojectIdentifier(projectId.trim().toUpperCase());
		if(project==null)
		{
			throw new ProjectIdException("Project does"+projectId+" not exists");
		}
		projectRepo.delete(project);
		
	}
	
}
