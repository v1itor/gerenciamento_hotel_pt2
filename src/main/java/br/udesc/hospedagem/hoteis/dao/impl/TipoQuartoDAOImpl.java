package br.udesc.hospedagem.hoteis.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.udesc.hospedagem.hoteis.dao.TipoQuartoDAO;
import br.udesc.hospedagem.hoteis.model.TipoQuarto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class TipoQuartoDAOImpl implements TipoQuartoDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void atualizarTipoQuarto(TipoQuarto tipoQuarto) {
		String queryStr = "UPDATE tipo_quarto SET descricao = :descricao, capacidade = :capacidade, tipo = :tipo WHERE tipo_quarto_id = :tipoQuartoId";
		Query query = this.entityManager.createNativeQuery(queryStr);
		query.setParameter("descricao", tipoQuarto.getDescricao());
		query.setParameter("capacidade", tipoQuarto.getCapacidade());
		query.setParameter("tipo", tipoQuarto.getTipo());
		query.setParameter("tipoQuartoId", tipoQuarto.getTipoQuartoId());
		query.executeUpdate();
	}

	@Override
	public List<TipoQuarto> buscarTipoQuarto() {
		Query query = this.entityManager.createNativeQuery("SELECT * FROM tipo_quarto", TipoQuarto.class);
		return query.getResultList();
	}

	@Override
	@Transactional
	public void deletarTipoQuarto(int tipoQuartoId) {
		Query query = this.entityManager
				.createNativeQuery("DELETE FROM tipo_quarto WHERE tipo_quarto_id = :tipoQuartoId");
		query.setParameter("tipoQuartoId", tipoQuartoId);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void inserirTipoQuarto(TipoQuarto tipoQuarto) {
		Query query = this.entityManager.createNativeQuery(
				"INSERT INTO tipo_quarto (descricao, capacidade, tipo) VALUES (:descricao, :capacidade, :tipo)");
		query.setParameter("descricao", tipoQuarto.getDescricao());
		query.setParameter("capacidade", tipoQuarto.getCapacidade());
		query.setParameter("tipo", tipoQuarto.getTipo());
		query.executeUpdate();
	}

}
