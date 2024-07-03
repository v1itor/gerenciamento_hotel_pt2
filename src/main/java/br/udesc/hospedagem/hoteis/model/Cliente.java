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
@Entity(name = "CLIENTE")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLIENTE_ID")
	private Integer clienteId;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "CPF")
	private String cpf;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "TELEFONE")
	private String telefone;

	@Column(name = "endereco_id")
	private Integer enderecoId;

	public Integer getClienteId() {
		return this.clienteId;
	}

	public String getCpf() {
		return this.cpf;
	}

	public String getEmail() {
		return this.email;
	}

	public Integer getEnderecoId() {
		return this.enderecoId;
	}

	public String getNome() {
		return this.nome;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnderecoId(Integer enderecoId) {
		this.enderecoId = enderecoId;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return "Cliente:\n" +
				"  Nome: " + this.nome + "\n" +
				"  CPF: " + this.cpf + "\n" +
				"  Email: " + this.email + "\n" +
				"  Telefone: " + this.telefone + "\n" +
				"  Endereço ID: " + this.enderecoId + "\n" +
				"  ID: " + this.clienteId;
	}

}
