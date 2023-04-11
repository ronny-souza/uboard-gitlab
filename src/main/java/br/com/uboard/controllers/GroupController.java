package br.com.uboard.controllers;

import java.util.List;

import javax.security.auth.login.CredentialNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uboard.exceptions.SynchronizeGroupsException;
import br.com.uboard.model.GroupDTO;
import br.com.uboard.services.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {

	private GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@GetMapping("/sync")
	public ResponseEntity<List<GroupDTO>> synchronizeGroups(@RequestHeader("userUUID") String userUUID)
			throws SynchronizeGroupsException, CredentialNotFoundException {
		List<GroupDTO> response = this.groupService.synchronizeGroups(userUUID);
		return ResponseEntity.ok(response);
	}
}
