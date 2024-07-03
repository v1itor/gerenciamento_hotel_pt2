package br.udesc.hospedagem.hoteis.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.udesc.hospedagem.hoteis.dao.ReservaDAO;
import br.udesc.hospedagem.hoteis.model.Reserva;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class ReservaDAOImpl implements ReservaDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void atualizarReserva(Reserva reserva) {
		String query = "UPDATE reserva SET data_inicio = :dataInicio, data_fim = :dataFim, data_reserva = :dataReserva, forma_pagamento = :formaPagamento WHERE reserva_id = :reservaId";
		this.entityManager.createNativeQuery(query).setParameter("dataInicio", reserva.getDataInicio())
		.setParameter("dataFim", reserva.getDataFim()).setParameter("dataReserva", reserva.getDataReserva())
		.setParameter("formaPagamento", reserva.getFormaPagamento())
		.setParameter("reservaId", reserva.getReservaId()).executeUpdate();
	}

	@Override
	public Integer buscaReservaIdPorAtributo(Reserva reserva) {
		String queryStr = "SELECT reserva_id FROM reserva WHERE data_inicio = :dataInicio AND data_fim = :dataFim AND data_reserva = :dataReserva AND forma_pagamento = :formaPagamento order by reserva_id desc limit 1";
		Query query = this.entityManager.createNativeQuery(queryStr, Reserva.class);
		query.setParameter("dataInicio", reserva.getDataInicio());
		query.setParameter("dataFim", reserva.getDataFim());
		query.setParameter("dataReserva", reserva.getDataReserva());
		query.setParameter("formaPagamento", reserva.getFormaPagamento());
		List<Reserva> listaResultado = query.getResultList();

		if (listaResultado.isEmpty()) {
			return null;
		}

		return listaResultado.get(0).getReservaId();
	}

	@Override
	@Transactional
	public void cadastrarReserva(Reserva reserva) {
		String query = "INSERT INTO reserva (data_inicio, data_fim, data_reserva, forma_pagamento) "
				+ "VALUES (:dataInicio, :dataFim, :dataReserva, :formaPagamento)";

		this.entityManager.createNativeQuery(query).setParameter("dataInicio", reserva.getDataInicio())
		.setParameter("dataFim", reserva.getDataFim()).setParameter("dataReserva", reserva.getDataReserva())
		.setParameter("formaPagamento", reserva.getFormaPagamento()).executeUpdate();
	}

	@Override
	@Transactional
	public void deletarReserva(Integer reservaId) {
		String queryReservaDetalhe = "DELETE FROM reserva_detalhe WHERE reserva_id = :reservaId";
		this.entityManager.createNativeQuery(queryReservaDetalhe).setParameter("reservaId", reservaId).executeUpdate();

		String query = "DELETE FROM reserva WHERE reserva_id = :reservaId";
		this.entityManager.createNativeQuery(query).setParameter("reservaId", reservaId).executeUpdate();
	}

	@Override
	public List<Reserva> listarReserva() {
		String queryStr = "SELECT * FROM reserva";
		return this.entityManager.createNativeQuery(queryStr, Reserva.class).getResultList();
	}
}
