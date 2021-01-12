package br.net.digitalzone.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonRootName("cozinha")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {
	
//	@NotNull(groups = Groups.CozinhaId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	//@NotBlank(groups = Default.class)
	@Column(nullable = false)
	private String nome;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "cozinha") // Uma cozinha tem muitos restaurantes.
	private List<Restaurante> restaurantes = new ArrayList<>();
	//Sufixo Many indica que a a propriedade é uma coleção
	
}
