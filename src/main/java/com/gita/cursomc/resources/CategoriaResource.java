package com.gita.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gita.cursomc.domain.Categoria;
import com.gita.cursomc.dto.CategoriaDTO;
import com.gita.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> retrieve(@PathVariable Integer id) {
		
		Categoria cat1 = service.find(id);
		return ResponseEntity.ok().body(cat1);
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id){
		Categoria obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET )
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="lines", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="order", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		Page<Categoria> list = service.findPage(page,linesPerPage, orderBy, direction );
		Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
}
