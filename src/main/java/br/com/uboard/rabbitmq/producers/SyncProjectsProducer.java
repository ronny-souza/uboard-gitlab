package br.com.uboard.rabbitmq.producers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.uboard.model.CredentialsDTO;
import br.com.uboard.model.SyncProjectDTO;
import br.com.uboard.rabbitmq.MessageService;
import br.com.uboard.rabbitmq.RabbitQueues;
import br.com.uboard.services.AuthenticationService;
import br.com.uboard.services.ProjectService;

@Component
//@EnableAsync
public class SyncProjectsProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(SyncProjectsProducer.class);

	private ProjectService projectService;

	private AuthenticationService authenticationService;

	private MessageService messageService;

	public SyncProjectsProducer(ProjectService projectService, AuthenticationService authenticationService,
			MessageService messageService) {
		this.projectService = projectService;
		this.authenticationService = authenticationService;
		this.messageService = messageService;
	}

//	@Async
//	@Scheduled(initialDelay = 60000, fixedDelay = 180000)
	public void send() {
		try {

			List<CredentialsDTO> credentials = this.authenticationService.listCredentials();

			if (credentials != null && !credentials.isEmpty()) {
				for (CredentialsDTO credentialsDTO : credentials) {
//					SyncProjectDTO syncProjectDTO = this.projectService.synchronizeProjects(credentialsDTO);
//					this.messageService.enqueue(RabbitQueues.GITLAB_SYNC_PROJECTS, syncProjectDTO);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
