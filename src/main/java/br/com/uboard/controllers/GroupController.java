package br.com.uboard.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uboard.exceptions.SynchronizeGroupsException;
import br.com.uboard.model.CredentialsDTO;
import br.com.uboard.model.SyncGroupDTO;
import br.com.uboard.services.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {

	private GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@PostMapping("/sync")
	public ResponseEntity<SyncGroupDTO> synchronizeGroups(@RequestBody CredentialsDTO credentialsDTO)
			throws SynchronizeGroupsException {
		SyncGroupDTO response = this.groupService.synchronizeGroups(credentialsDTO);
		return ResponseEntity.ok(response);
	}
}
