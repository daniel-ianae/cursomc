package com.gita.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gita.cursomc.domain.Categoria;
import com.gita.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = categoriaRepo.findById(id);
		return obj.orElse(null);
	}

}
