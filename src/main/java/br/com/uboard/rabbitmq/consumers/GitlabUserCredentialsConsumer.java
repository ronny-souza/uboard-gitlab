package br.com.uboard.rabbitmq.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.uboard.model.CredentialsDTO;
import br.com.uboard.rabbitmq.RabbitQueues;
import br.com.uboard.services.AuthenticationService;

@Component
public class GitlabUserCredentialsConsumer {

	private AuthenticationService authenticationService;

	public GitlabUserCredentialsConsumer(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@RabbitListener(queues = RabbitQueues.GITLAB_USER_CREDENTIALS)
	public void receive(CredentialsDTO credentialsDTO) {
		this.authenticationService.add(credentialsDTO);
	}
}
