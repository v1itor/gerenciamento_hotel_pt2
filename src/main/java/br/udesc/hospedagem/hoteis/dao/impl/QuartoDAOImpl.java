package br.udesc.hospedagem.hoteis.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.udesc.hospedagem.hoteis.dao.QuartoDAO;
import br.udesc.hospedagem.hoteis.model.Quarto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class QuartoDAOImpl implements QuartoDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void atualizarQuarto(Quarto quarto) {
		boolean possuiTipoQuarto = quarto.getTipoQuarto() != null;
		String queryStr = "UPDATE quarto SET ";
		if (possuiTipoQuarto) {
			queryStr += "tipo_quarto_id = :tipoQuartoId, ";
		}
		queryStr += "hotel_id = :hotelId, numero = :numero, disponibilidade = :disponibilidade, preco = :preco WHERE quarto_id = :quartoId";

		Query query = this.entityManager.createNativeQuery(queryStr);

		if (possuiTipoQuarto) {
			query.setParameter("tipoQuartoId", quarto.getTipoQuarto().getTipoQuartoId());
		}
		query.setParameter("hotelId", quarto.getHotelId());
		query.setParameter("numero", quarto.getNumero());
		query.setParameter("disponibilidade", quarto.getDisponibilidade());
		query.setParameter("preco", quarto.getPreco());
		query.setParameter("quartoId", quarto.getQuartoId());
		query.executeUpdate();
	}

	public List<Map<String, String>> buscaOcupacoesDeQuartos(Date dataInicial, Date dataFinal) {
		String query = """
				SELECT
				hotel.nome AS nome_hotel,
				quarto.numero AS numero_quarto,
				reserva.data_inicio,
				reserva.data_fim,
				cliente.nome AS nome_cliente
				FROM
				reserva
				JOIN
				reserva_detalhe ON reserva.reserva_id = reserva_detalhe.reserva_id
				JOIN
				quarto ON reserva_detalhe.quarto_id = quarto.quarto_id
				JOIN
				hotel ON quarto.hotel_id = hotel.hotel_id
				JOIN
				cliente ON reserva_detalhe.cliente_id = cliente.cliente_id
				WHERE
				reserva.data_inicio >= :dataInicial AND reserva.data_fim <= :dataFinal""";
		List<Object[]> retornoRelatorio = this.entityManager.createNativeQuery(query).setParameter("dataInicial", dataInicial).setParameter("dataFinal", dataFinal).getResultList();

		List<Map<String, String>> resultadoRelatorio = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		retornoRelatorio.forEach(obj -> {
			Map<String, String> map = new HashMap<>();
			map.put("Nome do hotel", obj[0].toString());
			map.put("Numero do quarto", obj[1].toString());
			map.put("Data de inicio", sdf.format(obj[2]));
			map.put("Data de fim", sdf.format(obj[3]));
			map.put("Nome do cliente", obj[4].toString());
			resultadoRelatorio.add(map);
		});

		return resultadoRelatorio;
	}

	@Override
	public Quarto buscaQuartoPorId(Integer quartoId) {
		String queryStr = "SELECT * FROM quarto WHERE quarto_id = :quartoId";
		Query query = this.entityManager.createNativeQuery(queryStr, Quarto.class);
		query.setParameter("quartoId", quartoId);
		List<Quarto> listaResultado = query.getResultList();
		if (listaResultado.size() == 0) {
			return null;
		}
		return listaResultado.get(0);
	}

	@Override
	public List<Quarto> buscarQuartoDisponivelPorHotelId(Integer hotelId, Date dataEntrada, Date dataSaida) {
		String queryStr = "SELECT * FROM quarto WHERE hotel_id = :hotelId AND disponibilidade = true";
		Query query = this.entityManager.createNativeQuery(queryStr, Quarto.class);
		query.setParameter("hotelId", hotelId);
		List<Quarto> listaResultado = query.getResultList();

		return listaResultado;
	}

	@Override
	@Transactional
	public void cadastrarQuarto(Quarto quarto) {
		boolean possuiTipoQuarto = quarto.getTipoQuarto() != null;
		String queryStr = "INSERT INTO quarto (hotel_id, ";
		if (possuiTipoQuarto) {
			queryStr += "tipo_quarto_id, ";
		}
		queryStr += "numero, disponibilidade, preco) VALUES (:hotelId, ";
		if (possuiTipoQuarto) {
			queryStr += ":tipoQuartoId, ";
		}
		queryStr += ":numero, :disponibilidade, :preco)";

		Query query = this.entityManager.createNativeQuery(queryStr);

		if (possuiTipoQuarto) {
			query.setParameter("tipoQuartoId", quarto.getTipoQuarto().getTipoQuartoId());
		}
		query.setParameter("hotelId", quarto.getHotelId());
		query.setParameter("numero", quarto.getNumero());
		query.setParameter("disponibilidade", quarto.getDisponibilidade());
		query.setParameter("preco", quarto.getPreco());
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void deletarQuarto(Integer quartoId) {

		String queryReservaDetalhe = "DELETE FROM reserva_detalhe WHERE quarto_id  = :quartoId";
		this.entityManager.createNativeQuery(queryReservaDetalhe).setParameter("quartoId", quartoId).executeUpdate();

		String query = "DELETE FROM quarto WHERE quarto_id = :quartoId";
		this.entityManager.createNativeQuery(query).setParameter("quartoId", quartoId).executeUpdate();
	}

	@Override
	public List<Quarto> listarQuarto() {
		String queryStr = "SELECT * FROM quarto";
		Query query = this.entityManager.createNativeQuery(queryStr, Quarto.class);
		List<Quarto> listaResultado = query.getResultList();
		return listaResultado;
	}

}
