package br.com.uboard.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.uboard.model.CredentialsDTO;
import br.com.uboard.model.UserDTO;
import br.com.uboard.model.enums.GitlabAPIEnum;

@Service
public class UserService {

	private WebClientRest webClient;

	public UserService() {
		this.webClient = new WebClientRest();
	}

	public UserDTO synchronizeUser(CredentialsDTO credentialsDTO) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer " + credentialsDTO.getToken());

		HttpEntity<Object> httpEntity = this.webClient.getHttpEntity(headers);

		String url = new StringBuilder().append(credentialsDTO.getAddress())
				.append(this.webClient.getDefaultApiPrefix()).append(GitlabAPIEnum.USER.getPath()).toString();

		ResponseEntity<UserDTO> response = this.webClient.get(url, httpEntity, UserDTO.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Error on sync Gitlab User");
		}

		return response.getBody();

	}
}
