package com.marksduarte.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.marksduarte.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	/**
	 * Numa aplicação real, trocar esse método por uma chamada ao serviço webservice.
	 * @param pgto
	 * @param instanteDoPedido
	 */
	public void preencherPagamentoComBoleto(PagamentoComBoleto pgto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pgto.setDataVencimento(cal.getTime());
	}
}
