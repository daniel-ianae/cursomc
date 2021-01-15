package com.gita.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gita.cursomc.domain.Cidade;
import com.gita.cursomc.domain.Cliente;
import com.gita.cursomc.domain.Endereco;
import com.gita.cursomc.domain.enums.Perfil;
import com.gita.cursomc.domain.enums.TipoCliente;
import com.gita.cursomc.dto.ClienteDTO;
import com.gita.cursomc.dto.ClienteNewDTO;
import com.gita.cursomc.repositories.ClienteRepository;
import com.gita.cursomc.repositories.EnderecoRepository;
import com.gita.cursomc.security.UserSS;
import com.gita.cursomc.services.exceptions.AuthorizationException;
import com.gita.cursomc.services.exceptions.DataIntegrityException;
import com.gita.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository endRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj =  repo.save(obj);
		endRepo.saveAll(obj.getEnderecos());
		
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque ha relacionamentos com o cliente");
		}
	}
	
	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente (obj.getId(), obj.getNome(), obj.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO obj) {
		Cliente cli = new Cliente (null, obj.getNome(), obj.getEmail(), obj.getCpfOuCnpj(),
				TipoCliente.toEnum(obj.getTipo()), pe.encode(obj.getSenha()));
		Cidade cidade = new Cidade(obj.getCidadeId(), null, null);
		Endereco end = new Endereco (null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(),
				obj.getBairro(), obj.getCep(), cli, cidade);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(obj.getTelefone1());
		
		if(obj.getTelefone2()!=null)
			cli.getTelefones().add(obj.getTelefone2());
		if(obj.getTelefone3()!=null)
			cli.getTelefones().add(obj.getTelefone3());
		
		return cli;
		
	}
	
	private void updateData (Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
