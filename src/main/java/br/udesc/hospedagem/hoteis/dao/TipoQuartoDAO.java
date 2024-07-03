package br.udesc.hospedagem.hoteis.dao;

import java.util.List;

import br.udesc.hospedagem.hoteis.model.TipoQuarto;

public interface TipoQuartoDAO {

	void atualizarTipoQuarto(TipoQuarto tipoQuarto);

	List<TipoQuarto> buscarTipoQuarto();

	void deletarTipoQuarto(int tipoQuartoId);

	void inserirTipoQuarto(TipoQuarto tipoQuarto);
}
