package br.com.uboard.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.uboard.exceptions.SynchronizeUserException;
import br.com.uboard.model.CredentialsDTO;
import br.com.uboard.model.UserDTO;
import br.com.uboard.model.enums.GitlabAPIEnum;

@Service
public class UserService {

	private WebClientRest webClient;

	public UserService() {
		this.webClient = new WebClientRest();
	}

	public UserDTO synchronizeUser(CredentialsDTO credentialsDTO) throws SynchronizeUserException {
		HttpEntity<Object> httpEntity = this.webClient.getHttpEntity(credentialsDTO.getToken());

		String url = new StringBuilder().append(credentialsDTO.getAddress())
				.append(this.webClient.getDefaultApiPrefix()).append(GitlabAPIEnum.USER.getPath()).toString();

		ResponseEntity<UserDTO> response = this.webClient.get(url, httpEntity, UserDTO.class);

		if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
			throw new SynchronizeUserException("Error on sync Gitlab User");
		}

		UserDTO responseBody = response.getBody();
		if (responseBody != null) {
			responseBody.setAddress(credentialsDTO.getAddress());
			responseBody.setToken(credentialsDTO.getToken());
		}
		return responseBody;
	}
}
