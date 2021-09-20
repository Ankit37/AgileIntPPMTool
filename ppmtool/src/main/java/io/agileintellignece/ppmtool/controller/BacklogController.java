package io.agileintellignece.ppmtool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.agileintellignece.ppmtool.domain.ProjectTask;
import io.agileintellignece.ppmtool.service.MapValidationErrorService;
import io.agileintellignece.ppmtool.service.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private MapValidationErrorService mapValidationService;

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id) {
		ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
		if (errorMap != null) {
			return errorMap;
		}

		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);
		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

	}

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id) {
		return projectTaskService.findBacklogById(backlog_id);
	}

	@GetMapping("/{backlog}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog, @PathVariable String pt_id) {
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog, pt_id);
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}

	@PatchMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> updatedProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,
					@Valid @RequestBody ProjectTask projectTask , BindingResult  result)
			{
		ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
		if (errorMap != null) {
			return errorMap;
		}
		
		ProjectTask updatedProjectTask= projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id);
		
		return new  ResponseEntity<ProjectTask>(updatedProjectTask, HttpStatus.OK);

			}

	
	@DeleteMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id)
	{
		projectTaskService.deletePTByProjectSequence(backlog_id, pt_id);
		
		return new ResponseEntity<String>("Project taks "+pt_id+" deleted succesfully",HttpStatus.OK);
	}
	
	
}
