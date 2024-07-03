package br.udesc.hospedagem.hoteis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva_detalhe")
public class ReservaDetalhe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserva_detalhe_id")
	private Integer reservaDetalheId;

	@Column(name = "reserva_id")
	private Integer reservaId;

	@Column(name = "quarto_id")
	private Integer quartoId;

	@Column(name = "cliente_id")
	private Integer clienteId;

	public int getClienteId() {
		return this.clienteId;
	}

	public int getQuartoId() {
		return this.quartoId;
	}

	public Integer getReservaDetalheId() {
		return this.reservaDetalheId;
	}

	public int getReservaId() {
		return this.reservaId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public void setQuartoId(Integer quartoId) {
		this.quartoId = quartoId;
	}

	public void setReservaDetalheId(Integer reservaDetalheId) {
		this.reservaDetalheId = reservaDetalheId;
	}

	public void setReservaId(Integer reservaId) {
		this.reservaId = reservaId;
	}

	@Override
	public String toString() {
		return "Reserva Detalhe:\n" +
				"  Reserva ID: " + this.reservaId + "\n" +
				"  Quarto ID: " + this.quartoId + "\n" +
				"  Cliente ID: " + this.clienteId + "\n" +
				"  ID do vínculo da reserva: " + this.reservaDetalheId + "\n";
	}
}
