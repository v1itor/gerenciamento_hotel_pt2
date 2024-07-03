package br.udesc.hospedagem.hoteis.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "QUARTO")
public class Quarto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quarto_id")
	private Integer quartoId;

	@Column(name = "hotel_id")
	private Integer hotelId;

	@ManyToOne
	@JoinColumn(name = "tipo_quarto_id")
	private TipoQuarto tipoQuarto;

	@Column(name = "numero")
	private Integer numero;

	@Column(name = "disponibilidade")
	private Boolean disponibilidade;

	@Column(name = "preco")
	private BigDecimal preco;

	public Boolean getDisponibilidade() {
		return this.disponibilidade;
	}

	public Integer getHotelId() {
		return this.hotelId;
	}

	public Integer getNumero() {
		return this.numero;
	}

	public BigDecimal getPreco() {
		return this.preco;
	}

	public Integer getQuartoId() {
		return this.quartoId;
	}

	public TipoQuarto getTipoQuarto() {
		return this.tipoQuarto;
	}

	public void setDisponibilidade(Boolean disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	public void setHotelId(Integer hotel) {
		this.hotelId = hotel;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public void setQuartoId(Integer quartoId) {
		this.quartoId = quartoId;
	}

	public void setTipoQuarto(TipoQuarto tipoQuarto) {
		this.tipoQuarto = tipoQuarto;
	}

	@Override
	public String toString() {
		String disponibilidadeString = this.disponibilidade != null ? (this.disponibilidade ? "Disponível" : "Indisponível") : "Não especificado";
		String hotelId = this.hotelId != null ? this.getHotelId().toString() : "Não especificado";
		String tipoQuartoNome = this.tipoQuarto != null ? this.tipoQuarto.getDescricao() : "Não especificado";
		String numeroQuarto = this.numero != null ? String.valueOf(this.numero) : "Não especificado";
		String precoString = this.preco != null ? this.preco.toString() : "Não especificado";

		return "Quarto:\n" +
		"  Número: " + numeroQuarto + "\n" +
		"  ID do Hotel: " + hotelId + "\n" +
		"  Tipo de Quarto: " + tipoQuartoNome + "\n" +
		"  Disponibilidade: " + disponibilidadeString + "\n" +
		"  Preço: " + precoString + "\n" +
		"  ID: " + this.quartoId;
	}


}
