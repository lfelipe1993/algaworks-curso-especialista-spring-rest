package br.net.digitalzone.algafood.domain.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {
	
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String senha;
	@CreationTimestamp//informa que a propriedade anotado, deve ser atribuida uma data e hora atual no momento que for salva a primeira vez
	@Column(nullable = false, columnDefinition = "DATETIME")
	private LocalDateTime dataCadastro;

	@ManyToMany
	@JoinTable(name = "usuario_grupo",
				joinColumns = @JoinColumn(name = "usuario_id"),
				inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	//name = configurando nome da tabela, joinColumns define o nome da 
	//coluna que faz referencia a propria classe que estamos mapeando. 
	//inverseJoinColumns define o nome da coluna do outro lado do relacionamento.
	private Set<Grupo> grupos = new HashSet<>();
	
	public boolean isNovo() {
	    return getId() == null;
	}
	
	public boolean removerGrupo(Grupo grupo) {
		return getGrupos().remove(grupo);
	}
	
	public boolean adicionarGrupo(Grupo grupo) {
		return getGrupos().add(grupo);
	}
	
}
