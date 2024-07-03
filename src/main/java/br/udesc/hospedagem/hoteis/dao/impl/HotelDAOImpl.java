package br.udesc.hospedagem.hoteis.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.udesc.hospedagem.hoteis.dao.HotelDAO;
import br.udesc.hospedagem.hoteis.model.Hotel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class HotelDAOImpl implements HotelDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void atualizarHotel(Hotel hotel) {
		String query = "UPDATE hotel SET nome = :nome, endereco_id = :enderecoId, classificacao = :classificacao, data_construcao = :dataConstrucao WHERE hotel_id = :hotelId";
		this.entityManager.createNativeQuery(query).setParameter("nome", hotel.getNome())
		.setParameter("enderecoId", hotel.getEnderecoId())
		.setParameter("classificacao", hotel.getClassificacao())
		.setParameter("dataConstrucao", hotel.getDataConstrucao())
		.setParameter("hotelId", hotel.getHotelId())
		.executeUpdate();
	}

	@Override
	public List<Map<String, String>> buscarHospedesQueEstaoHospedadosEmHotel(Integer hotelId) {
		String query = """
				SELECT
				cliente.nome AS nome_cliente,
				cliente.email,
				cliente.telefone,
				quarto.numero
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
				quarto.hotel_id = :hotelId AND
				((CURRENT_TIMESTAMP between data_inicio and data_fim) or
				(CURRENT_TIMESTAMP between data_inicio and data_fim))""";
		List<Object[]> retornoRelatorio = this.entityManager.createNativeQuery(query).setParameter("hotelId", hotelId)
				.getResultList();

		List<Map<String, String>> retorno = new ArrayList<>();

		retornoRelatorio.forEach(obj -> {
			Map<String, String> map = new HashMap<>();
			map.put("Nome do cliente", (String) obj[0]);
			map.put("Email do cliente", (String) obj[1]);
			map.put("Telefone do cliente", (String) obj[2]);
			map.put("Número do quarto", (String) obj[3]);

			retorno.add(map);
		});

		return retorno;

	}

	@Override
	@Transactional
	public void cadastrarHotel(Hotel hotel) {
		String query = "INSERT INTO hotel (nome, endereco_id, classificacao, data_construcao) "
				+ "VALUES (:nome, :enderecoId, :classificacao, :dataConstrucao)";

		this.entityManager.createNativeQuery(query).setParameter("nome", hotel.getNome())
		.setParameter("enderecoId", hotel.getEnderecoId())
		.setParameter("classificacao", hotel.getClassificacao())
		.setParameter("dataConstrucao", hotel.getDataConstrucao()).executeUpdate();
	}

	@Override
	@Transactional
	public void deletarHotel(Integer hotelId) {
		String queryReservaDetalhe = "DELETE FROM reserva_detalhe WHERE quarto_id IN (SELECT quarto_id FROM quarto WHERE hotel_id = :hotelId)";
		this.entityManager.createNativeQuery(queryReservaDetalhe).setParameter("hotelId", hotelId).executeUpdate();

		String queryQuarto = "DELETE FROM quarto WHERE hotel_id = :hotelId";
		this.entityManager.createNativeQuery(queryQuarto).setParameter("hotelId", hotelId).executeUpdate();

		String query = "DELETE FROM hotel WHERE hotel_id = :hotelId";
		this.entityManager.createNativeQuery(query).setParameter("hotelId", hotelId).executeUpdate();
	}

	@Override
	public List<Hotel> listarHotel() {
		String query = "SELECT * FROM hotel";
		return this.entityManager.createNativeQuery(query, Hotel.class).getResultList();
	}

	@Override
	public List<Hotel> listarHotelComQuartosDisponiveis() {
		String query = "SELECT distinct h.* FROM hotel h JOIN quarto q ON h.hotel_id = q.hotel_id WHERE q.disponibilidade = true";
		return this.entityManager.createNativeQuery(query, Hotel.class).getResultList();
	}
}
