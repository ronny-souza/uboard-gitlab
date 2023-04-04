package br.com.uboard.rabbitmq;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfiguration {

	private AmqpAdmin amqpAdmin;

	public RabbitMQConfiguration(AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
	}

	@Bean
	public Jackson2JsonMessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue syncGitlabProjectsQueue() {
		Queue queue = QueueBuilder.durable(RabbitQueues.GITLAB_SYNC_PROJECTS)
				.withArgument("x-message-ttl", TimeUnit.MINUTES.toMillis(10)).build();
		this.amqpAdmin.declareQueue(queue);
		return queue;
	}
}
