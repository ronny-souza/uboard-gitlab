package br.com.uboard.controllers;

import java.util.List;

import javax.security.auth.login.CredentialNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uboard.exceptions.SynchronizeProjectsException;
import br.com.uboard.model.ProjectDTO;
import br.com.uboard.services.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {

	private ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping("/sync")
	public ResponseEntity<List<ProjectDTO>> synchronizeProjects(@RequestHeader("userUUID") String userUUID)
			throws CredentialNotFoundException, SynchronizeProjectsException {

		List<ProjectDTO> response = this.projectService.synchronizeProjects(userUUID);
		return ResponseEntity.ok(response);
	}
}
