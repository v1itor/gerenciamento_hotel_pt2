package br.udesc.hospedagem.hoteis.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "HOTEL")
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_id")
	private Integer hotelId;

	@Column(name = "nome")
	private String nome;

	@Column(name = "endereco_id")
	private Integer enderecoId;

	@Column(name = "classificacao")
	private Integer classificacao;

	@Column(name = "data_construcao")
	@Temporal(TemporalType.DATE)
	private Date dataConstrucao;

	public Integer getClassificacao() {
		return this.classificacao;
	}

	public Date getDataConstrucao() {
		return this.dataConstrucao;
	}

	public Integer getEnderecoId() {
		return this.enderecoId;
	}

	public Integer getHotelId() {
		return this.hotelId;
	}

	public String getNome() {
		return this.nome;
	}

	public void setClassificacao(Integer classificacao) {
		this.classificacao = classificacao;
	}

	public void setDataConstrucao(Date dataConstrucao) {
		this.dataConstrucao = dataConstrucao;
	}

	public void setEnderecoId(Integer enderecoId) {
		this.enderecoId = enderecoId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		String dataConstrucaoString = this.dataConstrucao != null ? dateFormat.format(this.dataConstrucao) : "Não especificado";
		String enderecoString = this.enderecoId != null ? String.valueOf(this.enderecoId) : "Não especificado";

		return "Hotel:"  + this.nome + "\n" +
		"  Endereço: " + enderecoString + "\n" +
		"  Classificação: " + this.classificacao + "\n" +
		"  Data de Construção: " + dataConstrucaoString + "\n" +
		"  ID: " + this.hotelId;
	}

}
