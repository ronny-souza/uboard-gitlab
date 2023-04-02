package br.com.uboard.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.uboard.exceptions.SynchronizeGroupsException;
import br.com.uboard.model.CredentialsDTO;
import br.com.uboard.model.GroupDTO;
import br.com.uboard.model.SyncGroupDTO;
import br.com.uboard.model.enums.GitlabAPIEnum;
import br.com.uboard.model.enums.GitlabPaginationEnum;

@Service
public class GroupService {

	private WebClientRest webClient;

	public GroupService() {
		this.webClient = new WebClientRest();
	}

	public SyncGroupDTO synchronizeGroups(CredentialsDTO credentialsDTO) throws SynchronizeGroupsException {
		List<GroupDTO> groups = new ArrayList<>();
		int currentPage = 1;

		while (true) {
			HttpEntity<Object> httpEntity = this.webClient.getHttpEntity(credentialsDTO.getToken());

			String url = new StringBuilder().append(credentialsDTO.getAddress())
					.append(this.webClient.getDefaultApiPrefix()).append(GitlabAPIEnum.GROUPS.getPath()).append("?")
					.append(this.webClient.getPage(currentPage)).toString();

			ResponseEntity<List<GroupDTO>> response = this.webClient.get(url, httpEntity,
					new ParameterizedTypeReference<List<GroupDTO>>() {
					});

			if (response.getStatusCode() != HttpStatus.OK) {
				throw new SynchronizeGroupsException("Error on sync Gitlab Groups");
			}

			if (response.getBody() != null) {
				groups.addAll(response.getBody());
			}

			String totalPagesAsString = response.getHeaders().getFirst(GitlabPaginationEnum.TOTAL_PAGES.getName());
			Integer totalPages = Integer.valueOf(totalPagesAsString);

			if (totalPages == currentPage) {
				break;
			}

			currentPage++;
		}

		return new SyncGroupDTO(credentialsDTO.getUserUUID(), groups);
	}
}