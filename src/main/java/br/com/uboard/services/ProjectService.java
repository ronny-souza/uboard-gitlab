package br.com.uboard.services;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.CredentialNotFoundException;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.uboard.exceptions.SynchronizeProjectsException;
import br.com.uboard.model.CredentialsDTO;
import br.com.uboard.model.ProjectDTO;
import br.com.uboard.model.enums.GitlabAPIEnum;
import br.com.uboard.model.enums.GitlabPaginationEnum;

@Service
public class ProjectService {

	private WebClientRest webClient;
	private AuthenticationService authenticationService;

	public ProjectService(AuthenticationService authenticationService) {
		this.webClient = new WebClientRest();
		this.authenticationService = authenticationService;
	}

	public List<ProjectDTO> synchronizeProjects(String userUUID)
			throws SynchronizeProjectsException, CredentialNotFoundException {
		List<ProjectDTO> projects = new ArrayList<>();
		CredentialsDTO credentialsDTO = this.authenticationService.getCredentials(Long.valueOf(userUUID));

		int currentPage = 1;

		while (true) {
			HttpEntity<Object> httpEntity = this.webClient.getHttpEntity(credentialsDTO.getToken());

			String url = new StringBuilder().append(credentialsDTO.getAddress())
					.append(this.webClient.getDefaultApiPrefix()).append(GitlabAPIEnum.PROJECTS.getPath())
					.append("?membership=true&").append(this.webClient.getPage(currentPage)).toString();

			ResponseEntity<List<ProjectDTO>> response = this.webClient.get(url, httpEntity,
					new ParameterizedTypeReference<List<ProjectDTO>>() {
					});

			if (response.getStatusCode() != HttpStatus.OK) {
				throw new SynchronizeProjectsException("Error on sync Gitlab Projects");
			}

			if (response.getBody() != null) {
				projects.addAll(response.getBody());
			}

			String totalPagesAsString = response.getHeaders().getFirst(GitlabPaginationEnum.TOTAL_PAGES.getName());
			Integer totalPages = Integer.valueOf(totalPagesAsString);

			if (totalPages == currentPage) {
				break;
			}

			currentPage++;
		}

		return projects;
	}

}
