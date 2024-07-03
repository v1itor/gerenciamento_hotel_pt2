package br.udesc.hospedagem.hoteis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "endereco_id")
	private Integer enderecoId;

	@Column(name = "rua")
	private String rua;

	@Column(name = "numero")
	private Integer numero;

	@Column(name = "bairro")
	private String bairro;

	@Column(name = "cidade")
	private String cidade;

	@Column(name = "estado")
	private String estado;

	@Column(name = "pais")
	private String pais;

	@Column(name = "cep")
	private String cep;

	public Endereco() {
		super();
	}

	public Endereco(String rua, Integer numero, String bairro, String cidade, String estado, String pais, String cep) {
		super();
		this.rua = rua;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.pais = pais;
		this.cep = cep;
	}

	public String getBairro() {
		return this.bairro;
	}

	public String getCep() {
		return this.cep;
	}

	public String getCidade() {
		return this.cidade;
	}

	public Integer getEnderecoId() {
		return this.enderecoId;
	}

	public String getEstado() {
		return this.estado;
	}

	public Integer getNumero() {
		return this.numero;
	}

	public String getPais() {
		return this.pais;
	}

	public String getRua() {
		return this.rua;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setEnderecoId(Integer enderecoId) {
		this.enderecoId = enderecoId;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	@Override
	public String toString() {
		return "Endereço:\n" +
				"  Rua: " + this.rua + "\n" +
				"  Número: " + this.numero + "\n" +
				"  Bairro: " + this.bairro + "\n" +
				"  Cidade: " + this.cidade + "\n" +
				"  Estado: " + this.estado + "\n" +
				"  País: " + this.pais + "\n" +
				"  CEP: " + this.cep + "\n" +
				"  ID: " + this.enderecoId;
	}

}
