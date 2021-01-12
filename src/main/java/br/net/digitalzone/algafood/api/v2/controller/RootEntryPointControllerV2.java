package br.net.digitalzone.algafood.api.v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.net.digitalzone.algafood.api.v2.utils.AlgaLinksV2;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(path = "/v2",produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointControllerV2 {
	
	@Autowired
	private AlgaLinksV2 algaLinks;
	
	@ApiIgnore
	@GetMapping
	public RootEntryPointModelV2 root() {
		RootEntryPointModelV2 rootEntryPointModel = new RootEntryPointModelV2();

		rootEntryPointModel.add(algaLinks.linkToCidades("cidades"));

		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModelV2 extends RepresentationModel<RootEntryPointModelV2>{
		
	}

}
