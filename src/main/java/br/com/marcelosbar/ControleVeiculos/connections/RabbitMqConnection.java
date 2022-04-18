package br.com.marcelosbar.ControleVeiculos.connections;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import br.com.marcelosbar.RabbitMqLibrary.model.RabbitMqQueues;

@Component
public class RabbitMqConnection {
	private static final String EXCHANGE = "amq.direct";
	private AmqpAdmin amqpAdmin;

	public RabbitMqConnection(AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
	}

	private Queue newQueue(RabbitMqQueues queue) {
		return new Queue(queue.toString(), true, false, false);
	}

	private Binding bind(Queue queue, DirectExchange exchange) {
		return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
	}

	@PostConstruct
	private void add() {
		Queue airplanesQueue = newQueue(RabbitMqQueues.AIRPLANES);
		Queue shipsQueue = newQueue(RabbitMqQueues.SHIPS);

		DirectExchange exchange = new DirectExchange(EXCHANGE);

		Binding airplanesBinding = bind(airplanesQueue, exchange);
		Binding shipsBinding = bind(airplanesQueue, exchange);

		this.amqpAdmin.declareQueue(shipsQueue);
		this.amqpAdmin.declareQueue(airplanesQueue);

		this.amqpAdmin.declareExchange(exchange);

		this.amqpAdmin.declareBinding(airplanesBinding);
		this.amqpAdmin.declareBinding(shipsBinding);
	}
}