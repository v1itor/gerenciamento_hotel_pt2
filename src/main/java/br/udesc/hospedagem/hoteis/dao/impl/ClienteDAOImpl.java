package br.udesc.hospedagem.hoteis.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.udesc.hospedagem.hoteis.dao.ClienteDAO;
import br.udesc.hospedagem.hoteis.model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class ClienteDAOImpl implements ClienteDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void atualizarCliente(Cliente cliente) {
		String query = "UPDATE cliente SET nome = :nome, cpf = :cpf, email = :email, telefone = :telefone, endereco_id = :enderecoId WHERE cliente_id = :clienteId";
		this.entityManager.createNativeQuery(query).setParameter("nome", cliente.getNome())
		.setParameter("cpf", cliente.getCpf()).setParameter("email", cliente.getEmail())
		.setParameter("telefone", cliente.getTelefone()).setParameter("enderecoId", cliente.getEnderecoId())
		.setParameter("clienteId", cliente.getClienteId()).executeUpdate();
	}

	@Override
	public Cliente buscaClientePorId(Integer clienteId) {
		String query = "SELECT * FROM cliente WHERE cliente_id = :clienteId";
		List<Cliente> listaRetorno = this.entityManager.createNativeQuery(query, Cliente.class)
				.setParameter("clienteId", clienteId).getResultList();
		if (listaRetorno.size() == 0) {
			return null;
		}
		return listaRetorno.get(0);
	}

	@Override
	public List<Map<String, String>> buscarInformacoesHospedagemCliente(Integer clienteId) {
		String query = """
				SELECT
				hotel.nome AS nome_hotel,
				quarto.numero AS numero_quarto,
				reserva.data_inicio,
				reserva.data_fim,
				cliente.nome AS nome_cliente,
				cliente.email,
				cliente.telefone
				FROM
				cliente
				JOIN
				reserva_detalhe ON cliente.cliente_id = reserva_detalhe.cliente_id
				JOIN
				reserva ON reserva_detalhe.reserva_id = reserva.reserva_id
				JOIN
				quarto ON reserva_detalhe.quarto_id = quarto.quarto_id
				JOIN
				hotel ON quarto.hotel_id = hotel.hotel_id
				WHERE
				cliente.cliente_id = :clienteId""";
		List<Object[]> retornoRelatorio = this.entityManager.createNativeQuery(query).setParameter("clienteId", clienteId).getResultList();

		List<Map<String, String>> resultadoRelatorio = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (Object[] obj : retornoRelatorio) {
			Map<String, String> map = new HashMap<>();
			map.put("Nome do hotel", obj[0].toString());
			map.put("Numero do quarto", obj[1].toString());
			map.put("Data de inicio", sdf.format(obj[2]));
			map.put("Data de fim", sdf.format(obj[3]));
			map.put("Nome do cliente", obj[4].toString());
			map.put("Email do cliente", obj[5].toString());
			map.put("Telefone do cliente", obj[6].toString());
			resultadoRelatorio.add(map);
		}

		return resultadoRelatorio;
	}

	@Override
	@Transactional
	public void cadastrarCliente(Cliente cliente) {
		String query = "INSERT INTO cliente (nome, cpf, email, telefone, endereco_id) "
				+ "VALUES (:nome, :cpf, :email, :telefone, :enderecoId)";

		this.entityManager.createNativeQuery(query).setParameter("nome", cliente.getNome())
		.setParameter("cpf", cliente.getCpf()).setParameter("email", cliente.getEmail())
		.setParameter("telefone", cliente.getTelefone()).setParameter("enderecoId", cliente.getEnderecoId())
		.executeUpdate();
	}

	@Transactional
	@Override
	public void deletarCliente(Integer clienteId) {
		String queryReservaDetalhe = "DELETE FROM reserva_detalhe WHERE cliente_id = :clienteId";
		this.entityManager.createNativeQuery(queryReservaDetalhe).setParameter("clienteId", clienteId).executeUpdate();

		String query = "DELETE FROM cliente WHERE cliente_id = :clienteId";
		this.entityManager.createNativeQuery(query).setParameter("clienteId", clienteId).executeUpdate();
	}

	@Override
	public List<Cliente> listarCliente() {
		String query = "SELECT * FROM cliente order by cliente_id desc";
		return this.entityManager.createNativeQuery(query, Cliente.class).getResultList();
	}
}
