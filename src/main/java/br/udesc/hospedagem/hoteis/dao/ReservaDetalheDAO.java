package br.udesc.hospedagem.hoteis.dao;

import java.util.List;

import br.udesc.hospedagem.hoteis.model.ReservaDetalhe;

public interface ReservaDetalheDAO {

	public void cadastrarReservaDetalhe(ReservaDetalhe reservaDetalhe);

	void deletarReservaDetalhe(Integer reservaDetalheId);

	List<ReservaDetalhe> listarReservaDetalhe();

	List<ReservaDetalhe> listarReservaDetalhePorReservaId(Integer reservaId);

}
