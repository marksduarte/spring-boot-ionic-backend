package com.marksduarte.cursomc;

import java.text.SimpleDateFormat;
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
import com.marksduarte.cursomc.domain.ItemPedido;
import com.marksduarte.cursomc.domain.Pagamento;
import com.marksduarte.cursomc.domain.PagamentoComBoleto;
import com.marksduarte.cursomc.domain.PagamentoComCartao;
import com.marksduarte.cursomc.domain.Pedido;
import com.marksduarte.cursomc.domain.Produto;
import com.marksduarte.cursomc.domain.enums.EstadoPagamento;
import com.marksduarte.cursomc.domain.enums.TipoCliente;
import com.marksduarte.cursomc.repositories.CategoriaRepository;
import com.marksduarte.cursomc.repositories.CidadeRepository;
import com.marksduarte.cursomc.repositories.ClienteRepository;
import com.marksduarte.cursomc.repositories.EnderecoRepository;
import com.marksduarte.cursomc.repositories.EstadoRepository;
import com.marksduarte.cursomc.repositories.ItemPedidoRepository;
import com.marksduarte.cursomc.repositories.PagamentoRepository;
import com.marksduarte.cursomc.repositories.PedidoRepository;
import com.marksduarte.cursomc.repositories.ProdutoRepository;


@SpringBootApplication
public class CursomcApplication implements CommandLineRunner  {
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {		
		
	}

}
