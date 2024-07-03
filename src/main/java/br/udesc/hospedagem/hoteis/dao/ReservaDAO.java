package br.udesc.hospedagem.hoteis.dao;

import java.util.List;

import br.udesc.hospedagem.hoteis.model.Reserva;

public interface ReservaDAO {

	void atualizarReserva(Reserva reserva);

	public Integer buscaReservaIdPorAtributo(Reserva reserva);

	public void cadastrarReserva(Reserva reserva);

	void deletarReserva(Integer reservaId);

	List<Reserva> listarReserva();

}
