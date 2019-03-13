package com.marksduarte.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marksduarte.cursomc.domain.Categoria;
import com.marksduarte.cursomc.domain.Cidade;
import com.marksduarte.cursomc.domain.Cliente;
import com.marksduarte.cursomc.domain.Endereco;
import com.marksduarte.cursomc.domain.Estado;
import com.marksduarte.cursomc.domain.Produto;
import com.marksduarte.cursomc.domain.enums.TipoCliente;
import com.marksduarte.cursomc.repositories.CategoriaRepository;
import com.marksduarte.cursomc.repositories.CidadeRepository;
import com.marksduarte.cursomc.repositories.ClienteRepository;
import com.marksduarte.cursomc.repositories.EnderecoRepository;
import com.marksduarte.cursomc.repositories.EstadoRepository;
import com.marksduarte.cursomc.repositories.ProdutoRepository;


@SpringBootApplication
public class CursomcApplication implements CommandLineRunner  {
	
	@Autowired
	private CategoriaRepository categoriaRepo;	
	@Autowired
	private ProdutoRepository produtoRepo;	
	@Autowired
	private CidadeRepository cidadeRepo;	
	@Autowired
	private EstadoRepository estadoRepo;
	@Autowired
	private ClienteRepository clienteRepo;
	@Autowired
	private EnderecoRepository enderecoRepo;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// O atributo id é passado como nulo, pois o campo no banco é autoincrement
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");		

		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Mouse", 80.0);
		Produto p3 = new Produto(null, "Impressora", 800.0);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepo.saveAll(Arrays.asList(cat1, cat2));
		produtoRepo.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Distrito Federal");
		
		Cidade cid1 = new Cidade(null, "Belo Horizonte", est1);
		Cidade cid2 = new Cidade(null, "Patos", est1);
		Cidade cid3 = new Cidade(null, "Samambaia", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1, cid2));
		est2.getCidades().addAll(Arrays.asList(cid3));
		
		estadoRepo.saveAll(Arrays.asList(est1, est2));
		cidadeRepo.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678910", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepo.saveAll(Arrays.asList(cli1));
		enderecoRepo.saveAll(Arrays.asList(e1, e2));
		
		
	}

}
