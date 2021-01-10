package com.gita.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gita.cursomc.domain.Cidade;
import com.gita.cursomc.domain.Cliente;
import com.gita.cursomc.domain.Endereco;
import com.gita.cursomc.domain.Estado;
import com.gita.cursomc.domain.enums.TipoCliente;
import com.gita.cursomc.repositories.CidadeRepository;
import com.gita.cursomc.repositories.ClienteRepository;
import com.gita.cursomc.repositories.EnderecoRepository;
import com.gita.cursomc.repositories.EstadoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	EstadoRepository estadoRepo;
	
	@Autowired
	CidadeRepository cidadeRepo;
	
	@Autowired
	ClienteRepository clienteRepo;
	
	@Autowired
	EnderecoRepository enderecoRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepo.saveAll(Arrays.asList(est1, est2));
		cidadeRepo.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "366666666666", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("278788888", "38839288293"));
		
		Endereco e1 = new Endereco (null, "Rua flores", "200", "ap 300", "Jardim", "33222", cli1, c1);
		Endereco e2 = new Endereco (null, "Rua matos", "200", "ap 300", "Jardim", "33222", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepo.saveAll(Arrays.asList(cli1));
		enderecoRepo.saveAll(Arrays.asList(e1, e2));
		
		
		
	}

}
