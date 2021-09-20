package io.agileintellignece.ppmtool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agileintellignece.ppmtool.Repository.BacklogRepo;
import io.agileintellignece.ppmtool.Repository.ProjectRepository;
import io.agileintellignece.ppmtool.Repository.ProjectTaskRepo;
import io.agileintellignece.ppmtool.domain.Backlog;
import io.agileintellignece.ppmtool.domain.Project;
import io.agileintellignece.ppmtool.domain.ProjectTask;
import io.agileintellignece.ppmtool.exceptions.ProjectNotFoundException;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepo backLogRepo;

	@Autowired
	private ProjectRepository projectRepo;

	@Autowired
	private ProjectTaskRepo projectTaskRepo;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		try {
			Backlog backlog = backLogRepo.findByProjectIdentifier(projectIdentifier);
			projectTask.setBacklog(backlog);

			Integer BacklogSequence = backlog.getPTSequence();
			BacklogSequence++;
			backlog.setPTSequence(BacklogSequence);
			projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}

			if (projectTask.getPriority() == null) {
				projectTask.setPriority(3);
			}

			return projectTaskRepo.save(projectTask);
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}

	}

	public Iterable<ProjectTask> findBacklogById(String id) {
		Project project = projectRepo.findByprojectIdentifier(id);
		if (project == null) {
			throw new ProjectNotFoundException("Project with ID:'" + id + "' does not exists");
		}

		return projectTaskRepo.findByProjectIdentifierOrderByPriority(id);
	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		Backlog backlog = backLogRepo.findByProjectIdentifier(backlog_id);
		if (backlog == null) {
			throw new ProjectNotFoundException("Project with ID:'" + backlog_id + "' does not exists");
		}

		ProjectTask projectTask = projectTaskRepo.findByProjectSequence(pt_id);
		if (projectTask == null) {
			throw new ProjectNotFoundException("Project task" + pt_id + " not found");
		}

		if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project task " + pt_id + "does not exists in project" + backlog_id);
		}
		return projectTaskRepo.findByProjectSequence(pt_id);

	}

	
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id )
	{
		ProjectTask projectTask= findPTByProjectSequence(backlog_id,pt_id);
		
		projectTask= updatedTask;
		return projectTaskRepo.save(projectTask);
	}
	
	
	public void deletePTByProjectSequence(String backlog_id, String pt_id )
	{
		ProjectTask projectTask= findPTByProjectSequence(backlog_id,pt_id);
		Backlog backlog= projectTask.getBacklog();
		List<ProjectTask> pts= backlog.getProjectTasks();
		pts.remove(projectTask);
		backLogRepo.save(backlog);
		projectTaskRepo.delete(projectTask);
	}

}
