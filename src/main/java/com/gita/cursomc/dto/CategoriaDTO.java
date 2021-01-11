package com.gita.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.gita.cursomc.domain.Categoria;


public class CategoriaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	
	@NotEmpty (message="Preenchimento obrigatorio")
	@Length (min=5, max=100, message="Tamanho incorreto")
	private String nome;
	
	public CategoriaDTO() {}
	
	public CategoriaDTO(Categoria cat) {
		id = cat.getId();
		nome = cat.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
