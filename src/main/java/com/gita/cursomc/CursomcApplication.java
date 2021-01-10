package com.gita.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gita.cursomc.domain.Categoria;
import com.gita.cursomc.domain.Cidade;
import com.gita.cursomc.domain.Cliente;
import com.gita.cursomc.domain.Endereco;
import com.gita.cursomc.domain.Estado;
import com.gita.cursomc.domain.ItemPedido;
import com.gita.cursomc.domain.Pagamento;
import com.gita.cursomc.domain.PagamentoComBoleto;
import com.gita.cursomc.domain.PagamentoComCartao;
import com.gita.cursomc.domain.Pedido;
import com.gita.cursomc.domain.Produto;
import com.gita.cursomc.domain.enums.EstadoPagamento;
import com.gita.cursomc.domain.enums.TipoCliente;
import com.gita.cursomc.repositories.CategoriaRepository;
import com.gita.cursomc.repositories.CidadeRepository;
import com.gita.cursomc.repositories.ClienteRepository;
import com.gita.cursomc.repositories.EnderecoRepository;
import com.gita.cursomc.repositories.EstadoRepository;
import com.gita.cursomc.repositories.ItemPedidoRepository;
import com.gita.cursomc.repositories.PagamentoRepository;
import com.gita.cursomc.repositories.PedidoRepository;
import com.gita.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepo;
	
	@Autowired
	ProdutoRepository produtoRepo;
	
	@Autowired
	EstadoRepository estadoRepo;
	
	@Autowired
	CidadeRepository cidadeRepo;
	
	@Autowired
	ClienteRepository clienteRepo;
	
	@Autowired
	EnderecoRepository enderecoRepo;
	
	@Autowired
	PedidoRepository pedidoRepo;
	
	@Autowired
	PagamentoRepository pagamentoRepo;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		
		
		Categoria cat1 = new Categoria (null, "informatica");
		Categoria cat2 = new Categoria (null, "escritorio");
		
		Produto p1 = new Produto(null, "computador", 2000.00);
		Produto p2 = new Produto(null, "impressora", 800.00);
		Produto p3 = new Produto(null, "mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		

		categoriaRepo.saveAll(Arrays.asList(cat1,cat2));
		produtoRepo.saveAll(Arrays.asList(p1,p2,p3));
		
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
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, 
				sdf.parse("20/10/2017 00:00"), null);
		
		ped2.setPagamento(pagto2);
				
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepo.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepo.saveAll(Arrays.asList(pagto1, pagto2));
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped1.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepo.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		
	}

}
