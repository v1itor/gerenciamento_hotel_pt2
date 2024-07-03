package br.udesc.hospedagem.hoteis.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "RESERVA")
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserva_id")
	private Integer reservaId;

	@Column(name = "data_inicio")
	private Date dataInicio;

	@Column(name = "data_fim")
	private Date dataFim;

	@Column(name = "data_reserva")
	private Date dataReserva;

	@Column(name = "forma_pagamento")
	private String formaPagamento;

	public Date getDataFim() {
		return this.dataFim;
	}

	public Date getDataInicio() {
		return this.dataInicio;
	}

	public Date getDataReserva() {
		return this.dataReserva;
	}

	public String getFormaPagamento() {
		return this.formaPagamento;
	}

	public Integer getReservaId() {
		return this.reservaId;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public void setDataReserva(Date dataReserva) {
		this.dataReserva = dataReserva;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public void setReservaId(Integer reservaId) {
		this.reservaId = reservaId;
	}

	@Override
	public String toString() {
	    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    String dataInicioString = dataInicio != null ? dateFormat.format(dataInicio) : "Não especificado";
	    String dataFimString = dataFim != null ? dateFormat.format(dataFim) : "Não especificado";
	    String dataReservaString = dataReserva != null ? dateFormat.format(dataReserva) : "Não especificado";

	    return "Reserva:\n" +
	            "  Data de Início: " + dataInicioString + "\n" +
	            "  Data de Fim: " + dataFimString + "\n" +
	            "  Data da Reserva: " + dataReservaString + "\n" +
	            "  Forma de Pagamento: " + formaPagamento + "\n" +
	            "  ID: " + reservaId;
	}



}
