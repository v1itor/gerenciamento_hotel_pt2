package br.udesc.hospedagem.hoteis.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.udesc.hospedagem.hoteis.dao.ReservaDetalheDAO;
import br.udesc.hospedagem.hoteis.model.ReservaDetalhe;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class ReservaDetalheDAOImpl implements ReservaDetalheDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void cadastrarReservaDetalhe(ReservaDetalhe reservaDetalhe) {
		String query = "INSERT INTO reserva_detalhe (reserva_id, quarto_id, cliente_id) "
				+ "VALUES (:reservaId, :quartoId, :clienteId)";

		this.entityManager.createNativeQuery(query).setParameter("reservaId", reservaDetalhe.getReservaId())
		.setParameter("quartoId", reservaDetalhe.getQuartoId())
		.setParameter("clienteId", reservaDetalhe.getClienteId()).executeUpdate();

	}

	@Override
	@Transactional
	public void deletarReservaDetalhe(Integer reservaDetalheId) {
		String query = "DELETE FROM reserva_detalhe WHERE reserva_detalhe_id = :reservaDetalheId";
		this.entityManager.createNativeQuery(query).setParameter("reservaDetalheId", reservaDetalheId).executeUpdate();
	}

	@Override
	public List<ReservaDetalhe> listarReservaDetalhe() {
		String query = "SELECT * FROM reserva_detalhe";
		return this.entityManager.createNativeQuery(query, ReservaDetalhe.class).getResultList();
	}

	@Override
	public List<ReservaDetalhe> listarReservaDetalhePorReservaId(Integer reservaId) {
		String query = "SELECT * FROM reserva_detalhe WHERE reserva_id = :reservaId";
		return this.entityManager.createNativeQuery(query, ReservaDetalhe.class).setParameter("reservaId", reservaId).getResultList();
	}
}
