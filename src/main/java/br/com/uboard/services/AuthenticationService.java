package br.com.uboard.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.uboard.model.CredentialsDTO;

@Service
public class AuthenticationService {

	private Map<String, CredentialsDTO> credentialsPool = new HashMap<>();

	public AuthenticationService() {

	}

	public void add(CredentialsDTO credentialsDTO) {
		if (credentialsPool.containsKey(credentialsDTO.getUserUUID())) {
			credentialsPool.replace(credentialsDTO.getUserUUID(), credentialsDTO);
		} else {
			credentialsPool.put(credentialsDTO.getUserUUID(), credentialsDTO);
		}
	}

	public void remove(String userUUID) {
		if (credentialsPool.containsKey(userUUID)) {
			credentialsPool.remove(userUUID);
		}
	}
}
