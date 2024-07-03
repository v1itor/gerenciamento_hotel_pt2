package br.udesc.hospedagem.hoteis.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.udesc.hospedagem.hoteis.dao.EnderecoDAO;
import br.udesc.hospedagem.hoteis.model.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class EnderecoDAOImpl implements EnderecoDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void atualizarEndereco(Endereco endereco) {
		String query = "UPDATE endereco SET rua = :rua, numero = :numero, bairro = :bairro, cidade = :cidade, estado = :estado, pais = :pais, cep = :cep WHERE endereco_id = :enderecoId";
		this.entityManager.createNativeQuery(query).setParameter("rua", endereco.getRua())
		.setParameter("numero", endereco.getNumero()).setParameter("bairro", endereco.getBairro())
		.setParameter("cidade", endereco.getCidade()).setParameter("estado", endereco.getEstado())
		.setParameter("pais", endereco.getPais()).setParameter("cep", endereco.getCep())
		.setParameter("enderecoId", endereco.getEnderecoId()).executeUpdate();
	}

	public Endereco buscarEnderecoPorId(Integer enderecoId) {
		String query = "SELECT * FROM endereco WHERE endereco_id = :enderecoId";
		List<Endereco> listaRetorno = this.entityManager.createNativeQuery(query, Endereco.class)
				.setParameter("enderecoId", enderecoId).getResultList();
		if (listaRetorno.size() == 0) {
			return null;
		}
		return listaRetorno.get(0);
	}

	@Override
	public Integer buscarIdEnderecoPorAributos(Endereco endereco) {
		String query = "SELECT endereco_id FROM endereco WHERE rua = :rua AND numero = :numero AND bairro = :bairro AND cidade = :cidade AND estado = :estado AND pais = :pais AND cep = :cep order by endereco_id desc limit 1";

		List<Integer> listaRetorno = this.entityManager.createNativeQuery(query).setParameter("rua", endereco.getRua())
				.setParameter("numero", endereco.getNumero()).setParameter("bairro", endereco.getBairro())
				.setParameter("cidade", endereco.getCidade()).setParameter("estado", endereco.getEstado())
				.setParameter("pais", endereco.getPais()).setParameter("cep", endereco.getCep()).getResultList();


		if (listaRetorno.size() == 0) {
			return null;
		}

		return listaRetorno.get(0);
	}

	@Override
	@Transactional
	public void cadastrarEndereco(Endereco endereco) {
		String query = "INSERT INTO endereco (rua, numero, bairro, cidade, estado, pais, cep) "
				+ "VALUES (:rua, :numero, :bairro, :cidade, :estado, :pais, :cep)";

		this.entityManager.createNativeQuery(query).setParameter("rua", endereco.getRua())
		.setParameter("numero", endereco.getNumero()).setParameter("bairro", endereco.getBairro())
		.setParameter("cidade", endereco.getCidade()).setParameter("estado", endereco.getEstado())
		.setParameter("pais", endereco.getPais()).setParameter("cep", endereco.getCep()).executeUpdate();
	}

	@Override
	@Transactional
	public void deletarEndereco(Integer enderecoId) {
		String query = "DELETE FROM endereco WHERE endereco_id = :enderecoId";
		this.entityManager.createNativeQuery(query).setParameter("enderecoId", enderecoId).executeUpdate();
	}
}
