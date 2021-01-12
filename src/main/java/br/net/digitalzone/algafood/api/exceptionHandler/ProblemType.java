package br.net.digitalzone.algafood.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel","Mensagem Incompreensível"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	ACESSO_NEGADO("/acesso-negado", "Acesso negado"),
	PARAMETRO_INVALIDO("/parametro-invalido","parametro inválido");  
	
	private String title;
	private String uri;
	
	
	private ProblemType(String path,String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}
	
}
