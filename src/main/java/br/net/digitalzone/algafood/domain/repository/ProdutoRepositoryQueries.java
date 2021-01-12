package br.net.digitalzone.algafood.domain.repository;

import br.net.digitalzone.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {
	FotoProduto save(FotoProduto foto);
	void delete(FotoProduto foto);
	
}
