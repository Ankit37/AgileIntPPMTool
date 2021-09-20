package io.agileintellignece.ppmtool.controller;


import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.agileintellignece.ppmtool.domain.Project;
import io.agileintellignece.ppmtool.service.MapValidationErrorService;
import io.agileintellignece.ppmtool.service.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin

public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("/create")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult, Principal principal  ) {

		ResponseEntity<?> error = mapValidationErrorService.MapValidationService(bindingResult);
		if (error != null) {
			return error;
		}

		Project project1 = projectService.saveOrUpdateProject(project,principal.getName());
		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<Project> getProjectById(@PathVariable String projectId, Principal principal) {
		Project project1 = projectService.findProjectByIdentifier(projectId,principal.getName());
		
		return new ResponseEntity<Project>(project1, HttpStatus.OK);
	}

	
	@GetMapping("/all")
	@Transactional
	public Iterable<Project> getAllProject(Principal principal)
	{
		return projectService.findAllProject(principal.getName());
	}
	
	@GetMapping("/delete/{projectId}")
	public String deleteById(@PathVariable String  projectId,Principal principal)
	{
		projectService.deleteProjectByIdentifier(projectId,principal.getName());
		return "Deleted successfully";
	}
}
