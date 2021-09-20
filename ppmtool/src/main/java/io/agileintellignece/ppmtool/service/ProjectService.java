package io.agileintellignece.ppmtool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import io.agileintellignece.ppmtool.Repository.BacklogRepo;
import io.agileintellignece.ppmtool.Repository.ProjectRepository;
import io.agileintellignece.ppmtool.Repository.UserRepo;
import io.agileintellignece.ppmtool.domain.Backlog;
import io.agileintellignece.ppmtool.domain.Project;
import io.agileintellignece.ppmtool.domain.User;
import io.agileintellignece.ppmtool.exceptions.ProjectIdException;
import io.agileintellignece.ppmtool.exceptions.ProjectNotFoundException;

@Service

public class ProjectService {

	@Autowired
	private ProjectRepository projectRepo;

	@Autowired
	private BacklogRepo backlogRepo;

	@Autowired
	private UserRepo userRepo;

	public Project saveOrUpdateProject(Project project, String username) {

		if (project.getId() != null) {
			Project existingProject = projectRepo.findByprojectIdentifier(project.getProjectIdentifier());

			if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found exceptioin");
			} else if (existingProject == null) {
				throw new ProjectNotFoundException("Project with ID: " + project.getProjectIdentifier()
						+ " cannot be updated because its doesnt exists");
			}

		}

		try {

			User user = userRepo.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());

			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}

			if (project.getId() != null) {
				project.setBacklog(backlogRepo.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}

			return projectRepo.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project Id " + project.getProjectIdentifier().toUpperCase() + " already exists");
		}

	}

	public Project findProjectByIdentifier(String projectId, String username) {
		Project project = projectRepo.findByprojectIdentifier(projectId.trim().toUpperCase());
		if (project == null) {
			throw new ProjectIdException("Project does" + projectId + " not exists");
		}

		if (!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project not found in your account");
		}

		return project;
	}

	public Iterable<Project> findAllProject(String username) {
		return projectRepo.findAllByprojectLeader(username);
	}

	public void deleteProjectByIdentifier(String projectId, String username) {

		projectRepo.delete(findProjectByIdentifier(projectId, username));

	}

}
