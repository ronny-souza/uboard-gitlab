package br.com.uboard.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.CredentialNotFoundException;

import org.springframework.stereotype.Service;

import br.com.uboard.model.CredentialsDTO;

@Service
public class AuthenticationService {

	private Map<Long, CredentialsDTO> credentialsPool = new HashMap<>();

	public void process(List<CredentialsDTO> credentialsList) {

		for (CredentialsDTO credentialsDTO : credentialsList) {
			if (credentialsDTO.getRemovable()) {
				this.remove(credentialsDTO.getUserUUID());
			} else {
				this.add(credentialsDTO);
			}
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

	public List<CredentialsDTO> listCredentials() {
		return this.credentialsPool.values().stream().toList();
	}

	public CredentialsDTO getCredentials(Long userUUID) throws CredentialNotFoundException {
		CredentialsDTO credentialsDTO = this.credentialsPool.getOrDefault(userUUID, null);

		if (credentialsDTO == null) {
			throw new CredentialNotFoundException(String.format("Credentials for user %d is not found", userUUID));
		}

		return credentialsDTO;
	}
}
