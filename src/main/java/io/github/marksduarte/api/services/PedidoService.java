package io.github.marksduarte.api.services;

import java.util.Date;
import java.util.Optional;

import io.github.marksduarte.api.services.exceptions.AuthorizationException;
import io.github.marksduarte.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.github.marksduarte.api.domain.Cliente;
import io.github.marksduarte.api.domain.ItemPedido;
import io.github.marksduarte.api.domain.PagamentoComBoleto;
import io.github.marksduarte.api.domain.Pedido;
import io.github.marksduarte.api.domain.enums.EstadoPagamento;
import io.github.marksduarte.api.repositories.ItemPedidoRepository;
import io.github.marksduarte.api.repositories.PagamentoRepository;
import io.github.marksduarte.api.repositories.PedidoRepository;
import io.github.marksduarte.api.security.UserSS;

@Service
public class PedidoService {
	
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private ProdutoService produtoService;	
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EmailService emailService;
	
	public Pedido find(final Integer id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
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
		obj = pedidoRepository.save(obj);
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

	public Page<Pedido> findPage(Integer page, Integer linesPerPage,  String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if(user == null)
			throw new AuthorizationException("Acesso negado");
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
}
