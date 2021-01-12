package br.net.digitalzone.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.net.digitalzone.algafood.api.v1.assembler.PermissaoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.model.PermissaoDTO;
import br.net.digitalzone.algafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.model.Permissao;
import br.net.digitalzone.algafood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {
	@Autowired
    private PermissaoRepository permissaoRepository;
    
    @Autowired
    private PermissaoDtoAssembler permissaoDtoAssembler;
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
	@GetMapping
    public CollectionModel<PermissaoDTO> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        
        return permissaoDtoAssembler.toCollectionModel(todasPermissoes);
    }   
}
