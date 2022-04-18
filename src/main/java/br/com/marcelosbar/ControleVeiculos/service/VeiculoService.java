package br.com.marcelosbar.ControleVeiculos.service;

import org.opensky.model.StateVector;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcelosbar.RabbitMqLibrary.controller.dto.VeiculoDto;
import br.com.marcelosbar.RabbitMqLibrary.model.RabbitMqQueues;

@Service
public class VeiculoService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void enviaRabbitMq(RabbitMqQueues queue, StateVector state) {
		rabbitTemplate.convertAndSend(queue.toString(), new VeiculoDto(state));
	}

}