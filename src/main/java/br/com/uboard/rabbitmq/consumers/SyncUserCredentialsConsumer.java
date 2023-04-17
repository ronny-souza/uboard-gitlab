package br.com.uboard.rabbitmq.consumers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.uboard.model.CredentialsDTO;
import br.com.uboard.rabbitmq.RabbitQueues;
import br.com.uboard.services.AuthenticationService;

@Component
public class SyncUserCredentialsConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(SyncUserCredentialsConsumer.class);

	private AuthenticationService authenticationService;

	public SyncUserCredentialsConsumer(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@RabbitListener(queues = RabbitQueues.GITLAB_USER_CREDENTIALS)
	public void receive(List<CredentialsDTO> credentialsList) {
		try {
			this.authenticationService.process(credentialsList);
			LOGGER.debug(String.format("Receiving %d credentials to synchronize pool", credentialsList.size()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
