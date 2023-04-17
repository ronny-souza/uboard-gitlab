package br.com.uboard.services;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.CredentialNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.uboard.exceptions.SynchronizeMilestonesException;
import br.com.uboard.model.CredentialsDTO;
import br.com.uboard.model.MilestoneDTO;
import br.com.uboard.model.SyncMilestoneDTO;
import br.com.uboard.model.enums.GitlabAPIEnum;
import br.com.uboard.model.enums.GitlabPaginationEnum;
import br.com.uboard.rabbitmq.MessageService;
import br.com.uboard.rabbitmq.RabbitQueues;

@Service
public class MilestoneService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MilestoneService.class);

	private WebClientRest webClient;
	private AuthenticationService authenticationService;
	private MessageService messageService;

	public MilestoneService(AuthenticationService authenticationService, MessageService messageService) {
		this.webClient = new WebClientRest();
		this.authenticationService = authenticationService;
		this.messageService = messageService;
	}

	public SyncMilestoneDTO synchronizeGroupMilestones(SyncMilestoneDTO syncMilestoneDTO)
			throws SynchronizeMilestonesException, CredentialNotFoundException {
		List<MilestoneDTO> milestones = new ArrayList<>();
		CredentialsDTO credentialsDTO = this.authenticationService.getCredentials(syncMilestoneDTO.getUserUUID());

		int currentPage = 1;

		while (true) {
			HttpEntity<Object> httpEntity = this.webClient.getHttpEntity(credentialsDTO.getToken());

			String url = new StringBuilder().append(credentialsDTO.getAddress())
					.append(this.webClient.getDefaultApiPrefix()).append(GitlabAPIEnum.GROUPS.getPath()).append("/")
					.append(syncMilestoneDTO.getContextID()).append(GitlabAPIEnum.MILESTONES.getPath()).append("?")
					.append(this.webClient.getPage(currentPage)).toString();

			ResponseEntity<List<MilestoneDTO>> response = this.webClient.get(url, httpEntity,
					new ParameterizedTypeReference<List<MilestoneDTO>>() {
					});

			if (response.getStatusCode() != HttpStatus.OK) {
				throw new SynchronizeMilestonesException("Error on sync Gitlab Group Milestones");
			}

			if (response.getBody() != null) {
				milestones.addAll(response.getBody());
			}

			String totalPagesAsString = response.getHeaders().getFirst(GitlabPaginationEnum.TOTAL_PAGES.getName());
			Integer totalPages = Integer.valueOf(totalPagesAsString);

			if (totalPages == currentPage) {
				break;
			}

			currentPage++;
		}

		LOGGER.debug(String.format("Returning %d group milestones", milestones.size()));
		syncMilestoneDTO.setMilestones(milestones);
		this.messageService.enqueue(RabbitQueues.GITLAB_SYNC_GROUP_MILESTONES, syncMilestoneDTO);
		return syncMilestoneDTO;
	}

	public SyncMilestoneDTO synchronizeProjectMilestones(SyncMilestoneDTO syncMilestoneDTO)
			throws SynchronizeMilestonesException, CredentialNotFoundException {
		List<MilestoneDTO> milestones = new ArrayList<>();
		CredentialsDTO credentialsDTO = this.authenticationService.getCredentials(syncMilestoneDTO.getUserUUID());

		int currentPage = 1;

		while (true) {
			HttpEntity<Object> httpEntity = this.webClient.getHttpEntity(credentialsDTO.getToken());

			String url = new StringBuilder().append(credentialsDTO.getAddress())
					.append(this.webClient.getDefaultApiPrefix()).append(GitlabAPIEnum.PROJECTS.getPath()).append("/")
					.append(syncMilestoneDTO.getContextID()).append(GitlabAPIEnum.MILESTONES.getPath()).append("?")
					.append(this.webClient.getPage(currentPage)).toString();

			ResponseEntity<List<MilestoneDTO>> response = this.webClient.get(url, httpEntity,
					new ParameterizedTypeReference<List<MilestoneDTO>>() {
					});

			if (response.getStatusCode() != HttpStatus.OK) {
				throw new SynchronizeMilestonesException("Error on sync Gitlab Project Milestones");
			}

			if (response.getBody() != null) {
				milestones.addAll(response.getBody());
			}

			String totalPagesAsString = response.getHeaders().getFirst(GitlabPaginationEnum.TOTAL_PAGES.getName());
			Integer totalPages = Integer.valueOf(totalPagesAsString);

			if (totalPages == currentPage) {
				break;
			}

			currentPage++;
		}

		LOGGER.debug(String.format("Returning %d project milestones", milestones.size()));
		syncMilestoneDTO.setMilestones(milestones);
		this.messageService.enqueue(RabbitQueues.GITLAB_SYNC_PROJECT_MILESTONES, syncMilestoneDTO);
		return syncMilestoneDTO;
	}
}
