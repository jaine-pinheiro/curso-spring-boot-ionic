package com.jp.cursomc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jp.cursomc.domain.Categoria;
import com.jp.cursomc.domain.Cidade;
import com.jp.cursomc.domain.Cliente;
import com.jp.cursomc.domain.Endereco;
import com.jp.cursomc.domain.Estado;
import com.jp.cursomc.domain.Pagamento;
import com.jp.cursomc.domain.PagamentoComBoleto;
import com.jp.cursomc.domain.PagamentoComCartao;
import com.jp.cursomc.domain.Pedido;
import com.jp.cursomc.domain.Produto;
import com.jp.cursomc.domain.enums.EstadoPagamento;
import com.jp.cursomc.domain.enums.TipoCliente;
import com.jp.cursomc.repositories.CategoriaRepository;
import com.jp.cursomc.repositories.CidadeRepository;
import com.jp.cursomc.repositories.ClienteRepository;
import com.jp.cursomc.repositories.EnderecoRepository;
import com.jp.cursomc.repositories.EstadoRepository;
import com.jp.cursomc.repositories.PagamentoRepository;
import com.jp.cursomc.repositories.PedidoRepository;
import com.jp.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoryRepositpry;
	
	@Autowired
	private ProdutoRepository productRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository ;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// Associação de categorias e produtos: 
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoryRepositpry.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		// Associação de Estados e cidades:
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia", est1); 
		Cidade c2 = new Cidade(null,"São Paulo", est2); 
		Cidade c3 = new Cidade(null,"Campinas", est2); 
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "11111111101", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("2222-2222", "3333-3333"));
		
		Endereco e1 = new Endereco(null, "Rua das Flores", "300", "Apto 20", "Jardins", "08543-210", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "80", null, "Centro", "04545-210", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		//associação de Pedidos e Pagamentos
		SimpleDateFormat dateInstante = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, dateInstante.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, dateInstante.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,
				dateInstante.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
	}

}
