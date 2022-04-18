package br.com.marcelosbar.ControleVeiculos.controller;

import java.io.IOException;

import org.opensky.api.OpenSkyApi;
import org.opensky.model.OpenSkyStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcelosbar.ControleVeiculos.service.VeiculoService;
import br.com.marcelosbar.RabbitMqLibrary.model.RabbitMqQueues;

@RestController
@RequestMapping("/")
public class ControleVeiculosController {

	@Autowired
	private VeiculoService veiculoService;

	@GetMapping
	@Scheduled(fixedRate = 15000)
	public void getAvioes() throws IOException {
		OpenSkyApi openSkyApi = new OpenSkyApi();
		OpenSkyStates states = openSkyApi.getStates(0, null);

		states.getStates().forEach(state -> veiculoService.enviaRabbitMq(RabbitMqQueues.AIRPLANES, state));
	}

}