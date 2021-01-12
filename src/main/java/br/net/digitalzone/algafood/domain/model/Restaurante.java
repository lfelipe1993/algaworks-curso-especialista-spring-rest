package br.net.digitalzone.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.net.digitalzone.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;

//verifica se taxaFrete é =0. 
//Qual campo tem que estar escrito "Frete Gratis"
//descricaoObrigatoria o que deve estar escrito na obrigatóriedade.
@ValorZeroIncluiDescricao(ValorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@ManyToOne // (fetch = FetchType.LAZY)//Por padrão o Fetch é Eager
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;

	@CreationTimestamp // informa que a propriedade anotado, deve ser atribuida uma data e hora atual
						// no momento que for salva a primeira vez
	@Column(nullable = false, columnDefinition = "DATETIME") // nao cria com a definicao de segundos. Caso queira
																// datatime(6)
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp // informa que a data e hora atual deve ser atribuida a propriedade sempre que
						// for atualizada.
	@Column(nullable = false, columnDefinition = "DATETIME") // nao cria com a definicao de segundos. Caso queira
																// datatime(6)
	private OffsetDateTime dataAtualizacao;

	@Embedded // essa propriedade ela é um tipo incoporado. Esta classe é uma parte da
				// entidade restaurante.
	private Endereco endereco;

	private Boolean ativo = Boolean.TRUE;

	private Boolean aberto = Boolean.FALSE;

	@OneToMany(mappedBy = "restaurante") // Uma cozinha tem muitos restaurantes.
	private List<Produto> produtos = new ArrayList<>();
	// Sufixo Many indica que a a propriedade é uma coleção

	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	// name = configurando nome da tabela, joinColumns define o nome da
	// coluna que faz referencia a propria classe que estamos mapeando.
	// inverseJoinColumns define o nome da coluna do outro lado do relacionamento.
	private Set<FormaPagamento> formasPagamento = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> responsaveis = new HashSet<>();

	public void ativar() {
		setAtivo(true);
	}

	public void inativar() {
		setAtivo(false);
	}

	public void abrir() {
		setAberto(true);
	}

	public void fechar() {
		setAberto(false);
	}

	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}

	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}

	public boolean adicionarResponsavel(Usuario usuario) {
		return getResponsaveis().add(usuario);
	}

	public boolean removerResponsavel(Usuario usuario) {
		return getResponsaveis().remove(usuario);
	}

	public boolean aceitaFormaDePagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().contains(formaPagamento);
	}

	public boolean naoAceitaFormaDePagamento(FormaPagamento formaPagamento) {
		return !aceitaFormaDePagamento(formaPagamento);
	}

	public boolean isAberto() {
		return this.aberto;
	}

	public boolean isFechado() {
		return !isAberto();
	}

	public boolean isInativo() {
		return !isAtivo();
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public boolean aberturaPermitida() {
		return isAtivo() && isFechado();
	}

	public boolean ativacaoPermitida() {
		return isInativo();
	}

	public boolean inativacaoPermitida() {
		return isAtivo();
	}

	public boolean fechamentoPermitido() {
		return isAberto();
	}
}
