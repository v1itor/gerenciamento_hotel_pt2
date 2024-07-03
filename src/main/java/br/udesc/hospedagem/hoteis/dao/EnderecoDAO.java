package br.udesc.hospedagem.hoteis.dao;

import br.udesc.hospedagem.hoteis.model.Endereco;

public interface EnderecoDAO {

	void atualizarEndereco(Endereco endereco);

	public Integer buscarIdEnderecoPorAributos(Endereco endereco);

	public void cadastrarEndereco(Endereco endereco);

	void deletarEndereco(Integer enderecoId);

}
