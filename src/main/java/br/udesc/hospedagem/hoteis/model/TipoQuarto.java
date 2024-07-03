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
@Entity(name = "TIPO_QUARTO")
public class TipoQuarto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tipo_quarto_id")
	private Integer tipoQuartoId;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "capacidade")
	private Integer capacidade;

	@Column(name = "descricao", columnDefinition = "TEXT")
	private String descricao;

	public Integer getCapacidade() {
		return this.capacidade;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public String getTipo() {
		return this.tipo;
	}

	public Integer getTipoQuartoId() {
		return this.tipoQuartoId;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setTipoQuartoId(Integer tipoQuartoId) {
		this.tipoQuartoId = tipoQuartoId;
	}
	
	@Override
	public String toString() {
	    return "Tipo de Quarto:\n" +
	            "  Tipo: " + tipo + "\n" +
	            "  Capacidade: " + capacidade + "\n" +
	            "  Descrição: " + descricao + "\n" +
	            "  ID: " + tipoQuartoId;
	}


}
