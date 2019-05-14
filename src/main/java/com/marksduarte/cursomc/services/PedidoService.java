package com.marksduarte.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marksduarte.cursomc.domain.ItemPedido;
import com.marksduarte.cursomc.domain.PagamentoComBoleto;
import com.marksduarte.cursomc.domain.Pedido;
import com.marksduarte.cursomc.domain.enums.EstadoPagamento;
import com.marksduarte.cursomc.repositories.ItemPedidoRepository;
import com.marksduarte.cursomc.repositories.PagamentoRepository;
import com.marksduarte.cursomc.repositories.PedidoRepository;
import com.marksduarte.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private ProdutoService produtoService;	
	@Autowired
	private PedidoRepository repo;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EmailService emailService;
	
	public Pedido find(final Integer id) {
		Optional<Pedido> pedido = repo.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj); //o pagamento deve conhecer o pedido dele
		/**
		 * Consulta o cliente no DB e o associa ao objeto Pedido.
		 */
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		/**
		 * Como não temos um webservice que gera o boleto e retorna a data de vencimento
		 * vamo gerar uma para teste.
		 */
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
		}
		//Salva o Pedido
		obj = repo.save(obj);
		//Salva o Pagamento vinculado ao Pedido
		pagamentoRepository.save(obj.getPagamento());
		//Salva os itens do pedido
		for(ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);
			item.setPreco(produtoService.find(item.getProduto().getId()).getPreco());
			item.setPedido(obj);
			/**
			 * Consulta o produto no DB e o associa ao ItemPedido.
			 */
			item.setProduto(produtoService.find(item.getProduto().getId()));
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}

}
