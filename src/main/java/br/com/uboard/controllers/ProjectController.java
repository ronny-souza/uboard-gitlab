package br.com.uboard.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uboard.exceptions.SynchronizeProjectsException;
import br.com.uboard.model.CredentialsDTO;
import br.com.uboard.model.SyncProjectDTO;
import br.com.uboard.services.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {

	private ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@PostMapping("/sync")
	public ResponseEntity<SyncProjectDTO> synchronizeProjects(@RequestBody CredentialsDTO credentialsDTO)
			throws SynchronizeProjectsException {

		SyncProjectDTO response = this.projectService.synchronizeProjects(credentialsDTO);
		return ResponseEntity.ok(response);
	}
}
