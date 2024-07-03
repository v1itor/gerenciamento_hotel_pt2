package br.udesc.hospedagem.hoteis.dao;

import java.util.Date;
import java.util.List;

import br.udesc.hospedagem.hoteis.model.Quarto;

public interface QuartoDAO {

	void atualizarQuarto(Quarto quarto);

	Quarto buscaQuartoPorId(Integer quartoId);

	public List<Quarto> buscarQuartoDisponivelPorHotelId(Integer hotelId, Date dataEntrada, Date dataSaida);

	public void cadastrarQuarto(Quarto quarto);

	void deletarQuarto(Integer quartoId);

	List<Quarto> listarQuarto();

}
