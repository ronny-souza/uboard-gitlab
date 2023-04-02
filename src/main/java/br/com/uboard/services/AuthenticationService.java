package br.com.uboard.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.uboard.model.CredentialsDTO;

@Service
public class AuthenticationService {

	private Map<Long, CredentialsDTO> credentialsPool = new HashMap<>();

	public void process(CredentialsDTO credentialsDTO) {
		if (credentialsDTO.getRemovable()) {
			this.remove(credentialsDTO.getUserUUID());
		} else {
			this.add(credentialsDTO);
		}
	}

	public void add(CredentialsDTO credentialsDTO) {
		if (credentialsPool.containsKey(credentialsDTO.getUserUUID())) {
			credentialsPool.replace(credentialsDTO.getUserUUID(), credentialsDTO);
		} else {
			credentialsPool.put(credentialsDTO.getUserUUID(), credentialsDTO);
		}
	}

	public void remove(Long userUUID) {
		if (credentialsPool.containsKey(userUUID)) {
			credentialsPool.remove(userUUID);
		}
	}
}
