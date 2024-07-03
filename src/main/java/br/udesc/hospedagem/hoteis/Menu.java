package br.udesc.hospedagem.hoteis;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.udesc.hospedagem.hoteis.dao.impl.ClienteDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.EnderecoDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.HotelDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.QuartoDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.ReservaDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.ReservaDetalheDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.TipoQuartoDAOImpl;
import br.udesc.hospedagem.hoteis.model.Cliente;
import br.udesc.hospedagem.hoteis.model.Endereco;
import br.udesc.hospedagem.hoteis.model.Hotel;
import br.udesc.hospedagem.hoteis.model.Quarto;
import br.udesc.hospedagem.hoteis.model.Reserva;
import br.udesc.hospedagem.hoteis.model.ReservaDetalhe;
import br.udesc.hospedagem.hoteis.model.TipoQuarto;

@Component
public class Menu {

	Scanner scanner;
	private HotelDAOImpl hotelDAOImpl;
	private QuartoDAOImpl quartoDAOImpl;
	private ClienteDAOImpl clienteDAOImpl;
	private ReservaDAOImpl reservaDAOImpl;
	private EnderecoDAOImpl enderecoDAOImpl;
	private TipoQuartoDAOImpl tipoQuartoDAOImpl;
	private ReservaDetalheDAOImpl reservaDetalheDAOImpl;

	public Menu() {
		super();
		this.scanner = new Scanner(System.in);
	}

	private void atualizarCliente() {
		System.out.println("===== Atualizar Cliente =====");

		System.out.println("Clientes disponíveis: ");
		this.clienteDAOImpl.listarCliente().forEach(cliente -> {
			System.out.println(cliente);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do cliente escolhido: ");
		Integer clienteId = this.insereNumero();

		Cliente cliente = this.pedeDadosCliente();

		this.cadastraEndereco(cliente.getEnderecoId());

		cliente.setClienteId(clienteId);
		this.clienteDAOImpl.atualizarCliente(cliente);

		System.out.println("Cliente atualizado com sucesso!");
	}

	private void atualizarHotel() {
		System.out.println("===== Atualizar Hotel =====");

		System.out.println("Hoteis disponíveis: ");
		this.hotelDAOImpl.listarHotel().forEach(hotel -> {
			System.out.println(hotel);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do hotel escolhido: ");
		Integer hotelId = this.insereNumero();

		Hotel hotel = this.pedeDadosHotel();

		Endereco endereco = this.cadastraEndereco(hotel.getEnderecoId());

		hotel.setEnderecoId(this.enderecoDAOImpl.buscarIdEnderecoPorAributos(endereco));

		hotel.setHotelId(hotelId);
		this.hotelDAOImpl.atualizarHotel(hotel);

		System.out.println("Hotel atualizado com sucesso!");
	}

	private void atualizarQuarto() {
		System.out.println("===== Atualizar Quarto =====");

		System.out.println("Quartos disponíveis: ");
		this.quartoDAOImpl.listarQuarto().forEach(quarto -> {
			System.out.println(quarto);
			System.out.println("\n----------------------\n");
		});

		System.out.print("Digite o ID do quarto escolhido: ");
		Integer quartoId = this.insereNumero();

		Quarto quarto = this.pedeDadosQuarto();

		quarto.setQuartoId(quartoId);
		this.quartoDAOImpl.atualizarQuarto(quarto);

		System.out.println("Quarto atualizado com sucesso!");
	}

	private void atualizarReserva() {
		System.out.println("===== Atualizar Reserva =====");

		System.out.println("Reservas disponíveis: ");
		this.reservaDAOImpl.listarReserva().forEach(reserva -> {
			System.out.println(reserva);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID da reserva escolhida: ");
		Integer reservaId = this.insereNumero();

		Reserva reserva = this.pedeDadosReserva();

		reserva.setReservaId(reservaId);
		this.reservaDAOImpl.atualizarReserva(reserva);

		System.out.println("Reserva atualizada com sucesso!");
	}

	private void atualizarTipoQuarto() {
		System.out.println("===== Atualizar Tipo de Quarto =====");

		System.out.println("Tipos de quarto disponíveis: ");
		this.tipoQuartoDAOImpl.buscarTipoQuarto().forEach(tipoQuarto -> {
			System.out.println(tipoQuarto);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do tipo de quarto escolhido: ");
		Integer tipoQuartoId = this.insereNumero();

		TipoQuarto tipoQuarto = this.pedeDadosTipoQuarto();

		tipoQuarto.setTipoQuartoId(tipoQuartoId);
		this.tipoQuartoDAOImpl.atualizarTipoQuarto(tipoQuarto);

		System.out.println("Tipo de quarto atualizado com sucesso!");
	}

	private void buscaInformacoesHospedagemCliente() {
		System.out.println("Clientes disponíveis: ");
		this.clienteDAOImpl.listarCliente().forEach(cliente -> {
			System.out.println(cliente);
			System.out.println("\n----------------------\n");
		});
		System.out.println("Para buscar infomrações de hospedagem do cliente, por favor, insira o id do cliente: ");
		Integer clienteId = this.insereNumero();
		List<Map<String, String>> resultado = this.clienteDAOImpl.buscarInformacoesHospedagemCliente(clienteId);
		if (resultado.isEmpty()) {
			System.out.println("Cliente não encontrado ou não possui hospedagens.");
			return;
		}
		resultado.forEach(hospedagem -> {
			hospedagem.forEach((key, value) -> {
				System.out.println(key + ": " + value);
			});
			System.out.println("\n----------------------\n");
		});
	}

	private void buscaQuartosOcupadosNoPeriodo() {
		System.out.println("Digite a data de início (dd/MM/yyyy): ");
		Date dataInicio = this.lerData();
		System.out.println("Digite a data de fim (dd/MM/yyyy): ");
		Date dataFim = this.lerData();
		List<Map<String, String>> resultado = this.quartoDAOImpl.buscaOcupacoesDeQuartos(dataInicio, dataFim);
		if (resultado.isEmpty()) {
			System.out.println("Nenhum quarto ocupado no período.");
			return;
		}
		resultado.forEach(quarto -> {
			quarto.forEach((key, value) -> {
				System.out.println(key + ": " + value);
			});
			System.out.println("\n----------------------\n");
		});
	}

	private Endereco cadastraEndereco(Integer enderecoId) {
		Endereco endereco = this.pedeDadosEndereco();

		if (enderecoId != null) {
			endereco.setEnderecoId(enderecoId);
			this.enderecoDAOImpl.atualizarEndereco(endereco);
		} else {
			this.enderecoDAOImpl.cadastrarEndereco(endereco);
		}

		return endereco;
	}

	private void cadastrarCliente() {
		System.out.println("===== Cadastro de Cliente =====");

		Cliente cliente = this.pedeDadosCliente();

		// Salvar o cliente no banco de dados
		this.clienteDAOImpl.cadastrarCliente(cliente);

		System.out.println("Agora vamos cadastrar o endereço do cliente: ");
		Endereco endereco = this.cadastraEndereco(null);
		cliente.setEnderecoId(this.enderecoDAOImpl.buscarIdEnderecoPorAributos(endereco));

		System.out.println("Cliente cadastrado com sucesso!");
	}

	private void cadastrarHotel() {
		System.out.println("===== Cadastro de Hotel =====");

		Hotel hotel = this.pedeDadosHotel();

		Endereco endereco = this.cadastraEndereco(null);

		hotel.setEnderecoId(this.enderecoDAOImpl.buscarIdEnderecoPorAributos(endereco));

		this.hotelDAOImpl.cadastrarHotel(hotel);

		System.out.println("Hotel cadastrado com sucesso!");
	}

	private void cadastrarQuarto() {
		System.out.println("===== Cadastro de Quarto =====");

		Quarto quarto = this.pedeDadosQuarto();

		if (quarto == null) {
			return;
		}

		this.quartoDAOImpl.cadastrarQuarto(quarto);

		System.out.println("Quarto cadastrado com sucesso!");
	}

	private void cadastrarReserva() {
		System.out.println("===== Cadastro de Reserva =====");



		Reserva reserva = this.pedeDadosReserva();

		System.out.println("Agora vamos criar um vinculo com um cliente e um quarto:");

		ReservaDetalhe reservaDetalhe = this.pedeClienteEQuartoDaReservaDetalhe(reserva);

		if (reservaDetalhe == null) {
			return;
		}

		this.reservaDAOImpl.cadastrarReserva(reserva);

		Integer reservaId = this.reservaDAOImpl.buscaReservaIdPorAtributo(reserva);
		reservaDetalhe.setReservaId(reservaId);

		this.reservaDAOImpl.cadastrarReserva(reserva);

		System.out.println("Reserva cadastrada com sucesso!");
	}

	private void deletarCliente() {

		List<Cliente> listaDeClientes = this.clienteDAOImpl.listarCliente();

		if (listaDeClientes.isEmpty()) {
			System.out.println("\n\nNenhum cliente cadastrado. Por favor, cadastre um cliente antes de continuar.\n\n");
			return;
		}

		listaDeClientes.forEach(cliente -> {
			System.out.println(cliente);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do cliente escolhido: ");
		Integer clienteId = this.insereNumero();

		this.clienteDAOImpl.deletarCliente(clienteId);
		System.out.println("Cliente deletado com sucesso!");
	}

	private void deletarHotel() {

		List<Hotel> listaDeHoteis = this.hotelDAOImpl.listarHotel();

		if (listaDeHoteis.isEmpty()) {
			System.out.println("\n\nNenhum hotel cadastrado. Por favor, cadastre um hotel antes de continuar.\n\n");
			return;
		}

		listaDeHoteis.forEach(hotel -> {
			System.out.println(hotel);
			System.out.println("\n----------------------\n");
		});

		System.out.println(
				"Atenção: Ao deletar um hotel, todos os quartos associados a ele serão deletados, assim como todas as partes da reserva associadas àquele quarto.");

		System.out.println("Digite o ID do hotel escolhido: ");
		Integer hotelId = this.insereNumero();

		Hotel hotelSelecionado = listaDeHoteis.stream().filter(hotel -> hotel.getHotelId().equals(hotelId)).findFirst()
				.orElse(null);

		if (hotelSelecionado == null) {
			System.out.println("Hotel não encontrado. Por favor, insira um ID válido.");
			return;
		}

		this.hotelDAOImpl.deletarHotel(hotelId);
		this.enderecoDAOImpl.deletarEndereco(hotelSelecionado.getEnderecoId());
		System.out.println("Hotel deletado com sucesso!");
	}

	private void deletarQuarto() {

		List<Quarto> listaDeQuartos = this.quartoDAOImpl.listarQuarto();

		if (listaDeQuartos.isEmpty()) {
			System.out.println("\n\nNenhum quarto cadastrado. Por favor, cadastre um quarto antes de continuar.\n\n");
			return;
		}

		listaDeQuartos.forEach(quarto -> {
			System.out.println(quarto);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do quarto escolhido: ");
		Integer quartoId = this.insereNumero();

		if (listaDeQuartos.stream().noneMatch(quarto -> quarto.getQuartoId().equals(quartoId))) {
			System.out.println("Quarto não encontrado. Por favor, insira um ID válido.");
			return;
		}

		this.quartoDAOImpl.deletarQuarto(quartoId);
		System.out.println("Quarto deletado com sucesso!");
	}


	private void deletarReserva() {

		List<Reserva> listaDeReservas = this.reservaDAOImpl.listarReserva();

		if (listaDeReservas.isEmpty()) {
			System.out
			.println("\n\nNenhuma reserva cadastrada. Por favor, cadastre uma reserva antes de continuar.\n\n");
			return;
		}

		listaDeReservas.forEach(reserva -> {
			System.out.println(reserva);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID da reserva escolhida: ");
		Integer reservaId = this.insereNumero();

		if (listaDeReservas.stream().noneMatch(reserva -> reserva.getReservaId().equals(reservaId))) {
			System.out.println("Reserva não encontrada. Por favor, insira um ID válido.");
			return;
		}

		this.reservaDAOImpl.deletarReserva(reservaId);
		System.out.println("Reserva deletada com sucesso!");
	}

	private void deletarReservaDetalhe() {
		System.out.println("===== Deletar Reserva Detalhe =====");

		List<ReservaDetalhe> listaDeReservasDetalhes = this.reservaDetalheDAOImpl.listarReservaDetalhe();

		if (listaDeReservasDetalhes.isEmpty()) {
			System.out.println("\n\nNenhuma reserva detalhe cadastrada. Por favor, cadastre uma reserva detalhe antes de continuar.\n\n");
			return;
		}

		listaDeReservasDetalhes.forEach(reservaDetalhe -> {
			System.out.println(reservaDetalhe);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do vinculo de reserva escolhida: ");
		Integer reservaDetalheId = this.insereNumero();

		if (listaDeReservasDetalhes.stream()
				.noneMatch(reservaDetalhe -> reservaDetalhe.getReservaDetalheId().equals(reservaDetalheId))) {
			System.out.println("Vínculo de reserva não encontrado. Por favor, insira um ID válido.");
			return;
		}

		this.reservaDetalheDAOImpl.deletarReservaDetalhe(reservaDetalheId);
		System.out.println("Vínculo da reserva deletada com sucesso!");
	}

	private void deletaTipoQuarto() {
		System.out.println("Tipos de quarto disponíveis: ");
		this.tipoQuartoDAOImpl.buscarTipoQuarto().forEach(tipoQuarto -> {
			System.out.println(tipoQuarto);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do tipo de quarto escolhido: ");
		Integer tipoQuartoId = this.insereNumero();

		this.tipoQuartoDAOImpl.deletarTipoQuarto(tipoQuartoId);
		System.out.println("Tipo de quarto deletado com sucesso!");
	}

	public void exibeMenu() {
		boolean sair = false;
		while (!sair) {
			System.out.println("Menu Principal:");
			System.out.println("1 - Cadastros");
			System.out.println("2 - Listagens");
			System.out.println("3 - Atualizações");
			System.out.println("4 - Deletar");
			System.out.println("5 - Outras Operações");
			System.out.println("0 - Sair");

			System.out.println("Digite a opção desejada: ");

			Integer opcao = this.insereNumero();

			switch (opcao) {
			case 0 -> sair = true;
			case 1 -> {
				System.out.println("Menu de Cadastros:");
				System.out.println("1 - Cadastrar Hotel");
				System.out.println("2 - Cadastrar Quarto");
				System.out.println("3 - Cadastrar Cliente");
				System.out.println("4 - Cadastrar Reserva");
				System.out.println("5 - Cadastrar Tipo de Quarto");

				int subOpcaoCadastro = this.insereNumero();
				switch (subOpcaoCadastro) {
				case 1 -> this.cadastrarHotel();
				case 2 -> this.cadastrarQuarto();
				case 3 -> this.cadastrarCliente();
				case 4 -> this.cadastrarReserva();
				case 5 -> this.insereTipoQuarto();
				default -> System.out.println("Opção inválida. Por favor, tente novamente.");
				}
			}
			case 2 -> {
				System.out.println("Menu de Listagens:");
				System.out.println("1 - Listar Hoteis");
				System.out.println("2 - Listar Quartos");
				System.out.println("3 - Listar Clientes");
				System.out.println("4 - Listar Reservas");
				System.out.println("5 - Listar Tipos de Quarto");

				int subOpcaoListagem = this.insereNumero();
				switch (subOpcaoListagem) {
				case 1 -> this.listarHoteis();
				case 2 -> this.listarQuartos();
				case 3 -> this.listarClientes();
				case 4 -> this.listarReservas();
				case 5 -> this.listarTiposQuarto();
				default -> System.out.println("Opção inválida. Por favor, tente novamente.");
				}
			}
			case 3 -> {
				System.out.println("Menu de Atualizações:");
				System.out.println("1 - Atualizar Hotel");
				System.out.println("2 - Atualizar Quarto");
				System.out.println("3 - Atualizar Cliente");
				System.out.println("4 - Atualizar Reserva");
				System.out.println("5 - Atualizar Tipo de Quarto");

				int subOpcaoAtualizacao = this.insereNumero();
				switch (subOpcaoAtualizacao) {
				case 1 -> this.atualizarHotel();
				case 2 -> this.atualizarQuarto();
				case 3 -> this.atualizarCliente();
				case 4 -> this.atualizarReserva();
				case 5 -> this.atualizarTipoQuarto();
				default -> System.out.println("Opção inválida. Por favor, tente novamente.");
				}
			}
			case 4 -> {
				System.out.println("Menu de Deletar:");
				System.out.println("1 - Deletar Hotel");
				System.out.println("2 - Deletar Quarto");
				System.out.println("3 - Deletar Cliente");
				System.out.println("4 - Deletar Reserva");
				System.out.println("5 - Deletar Tipo de Quarto");

				int subOpcaoDelecao = this.insereNumero();
				switch (subOpcaoDelecao) {
				case 1 -> this.deletarHotel();
				case 2 -> this.deletarQuarto();
				case 3 -> this.deletarCliente();
				case 4 -> this.deletarReserva();
				case 5 -> this.deletaTipoQuarto();
				default -> System.out.println("Opção inválida. Por favor, tente novamente.");
				}
			}
			case 5 -> {
				System.out.println("Menu de Outras Operações:");
				System.out.println("1 - Vincular Reserva");
				System.out.println("2 - Deletar Vínculo de Reserva");
				System.out.println("3 - Buscar Informações de Hospedagem do Cliente");
				System.out.println("4 - Buscar Clientes Hospedados em Hotel");
				System.out.println("5 - Buscar Quartos Ocupados no Período");

				int subOpcaoOutras = this.insereNumero();
				switch (subOpcaoOutras) {
				case 1 -> this.vinculaReserva();
				case 2 -> this.deletarReservaDetalhe();
				case 3 -> this.buscaInformacoesHospedagemCliente();
				case 4 -> this.listarClientesNoHotel();
				case 5 -> this.buscaQuartosOcupadosNoPeriodo();
				default -> System.out.println("Opção inválida. Por favor, tente novamente.");
				}
			}
			default -> System.out.println("Opção inválida. Por favor, tente novamente.");
			}
		}

		return;
	}

	private BigDecimal insereBigDecimal() {
		boolean numeroValido = false;
		BigDecimal numero = null;
		while (!numeroValido) {
			try {
				numero = this.scanner.nextBigDecimal();
				this.scanner.nextLine(); // Limpar o buffer do scanner
				numeroValido = true;
			} catch (Exception e) {
				System.out.println("Número inválido. Por favor, insira um número válido:");
			}
		}
		return numero;
	}

	private Integer insereNumero() {
		boolean numeroValido = false;
		Integer numero = null;
		while (!numeroValido) {
			try {
				numero = this.scanner.nextInt();
				this.scanner.nextLine(); // Limpar o buffer do scanner
				numeroValido = true;
			} catch (Exception e) {
				System.out.println("Número inválido. Por favor, insira um número válido:");
				this.scanner.nextLine(); // Limpar o buffer do scanner
			}
		}
		return numero;
	}

	private String insereString() {
		boolean numeroValido = false;
		String string = null;
		while (!numeroValido) {
			try {
				string = this.scanner.nextLine();
				numeroValido = true;
			} catch (Exception e) {
				System.out.println("Número inválido. Por favor, insira um número válido:");
			}
		}
		return string;
	}

	private void insereTipoQuarto() {
		TipoQuarto tipoQuarto = this.pedeDadosTipoQuarto();
		this.tipoQuartoDAOImpl.inserirTipoQuarto(tipoQuarto);
		System.out.println("Tipo de quarto cadastrado com sucesso!");
	}

	private Date lerData() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date data = null;
		boolean dataValida = false;

		while (!dataValida) {
			try {
				String dataStr = this.scanner.nextLine();
				data = dateFormat.parse(dataStr);
				dataValida = true;
			} catch (ParseException e) {
				System.out.println("Formato de data inválido. Por favor, insira a data no formato dd/MM/yyyy:");
			}
		}

		return data;
	}

	private void listarClientes() {
		List<Cliente> listaDeClientes = this.clienteDAOImpl.listarCliente();
		if (listaDeClientes.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado.");
			return;
		}
		listaDeClientes.forEach(cliente -> {
			System.out.println(cliente);
			System.out.println("\n----------------------\n");
		});
		Integer opcao = -1;
		while (opcao != 0) {
			System.out.println("Insira o ID de um cliente para detalhar o endereço ou 0 para voltar ao menu: ");
			final Integer opcaoUsuario = this.insereNumero();

			if (opcaoUsuario == 0) {
				break;
			}

			Cliente clienteEscolhido = listaDeClientes.stream()
					.filter(cliente -> cliente.getClienteId().equals(opcaoUsuario)).findFirst().orElse(null);

			Endereco endereco = this.enderecoDAOImpl.buscarEnderecoPorId(clienteEscolhido.getEnderecoId());

			if (endereco == null) {
				System.out.println("Endereço não encontrado.");
				continue;
			}

			System.out.println("Endereço do cliente com id " + opcaoUsuario + ": ");
			System.out.println(endereco);
			opcao = opcaoUsuario;
		}
	}

	private void listarClientesNoHotel() {
		System.out.println("Hoteis disponíveis: ");
		this.hotelDAOImpl.listarHotel().forEach(hotel -> {
			System.out.println(hotel);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do hotel escolhido: ");
		Integer hotelId = this.insereNumero();
		List<Map<String, String>> resultado = this.hotelDAOImpl.buscarHospedesQueEstaoHospedadosEmHotel(hotelId);

		if (resultado.isEmpty()) {
			System.out.println("Nenhum cliente hospedado no hotel.");
			return;
		}

		resultado.forEach(cliente -> {
			cliente.forEach((key, value) -> {
				System.out.println(key + ": " + value);
			});
			System.out.println("\n----------------------\n");
		});
	}

	private void listarHoteis() {
		List<Hotel> listaDeHoteis = this.hotelDAOImpl.listarHotel();
		if (listaDeHoteis.isEmpty()) {
			System.out.println("Nenhum hotel cadastrado.");
			return;
		}
		listaDeHoteis.forEach(hotel -> {
			System.out.println(hotel);
			System.out.println("\n----------------------\n");
		});

		Integer opcao = -1;
		while (opcao != 0) {
			System.out.println("Insira o ID de um hotel para detalhar o endereço ou 0 para voltar ao menu: ");
			final Integer opcaoUsuario = this.insereNumero();

			if (opcaoUsuario == 0) {
				break;
			}

			Hotel HotelEscolhido = listaDeHoteis.stream().filter(hotel -> hotel.getHotelId().equals(opcaoUsuario))
					.findFirst().orElse(null);

			Endereco endereco = this.enderecoDAOImpl.buscarEnderecoPorId(HotelEscolhido.getEnderecoId());

			if (endereco == null) {
				System.out.println("Endereço não encontrado.");
				continue;
			}

			System.out.println("Endereço do hotel com id " + opcaoUsuario + ": ");
			System.out.println(endereco);
			opcao = opcaoUsuario;
		}

	}

	private void listarQuartos() {
		List<Quarto> listaDeQuartos = this.quartoDAOImpl.listarQuarto();
		if (listaDeQuartos.isEmpty()) {
			System.out.println("Nenhum quarto cadastrado.");
			return;
		}
		listaDeQuartos.forEach(quarto -> {
			System.out.println(quarto);
			System.out.println("\n----------------------\n");
		});

		this.pedeConfirmacaoVoltarMenu();
	}

	private void listarReservas() {
		List<Reserva> listaDeReservas = this.reservaDAOImpl.listarReserva();
		if (listaDeReservas.isEmpty()) {
			System.out.println("Nenhuma reserva cadastrada.");
			return;
		}
		listaDeReservas.forEach(reserva -> {
			System.out.println(reserva);
			System.out.println("\n----------------------\n");
		});

		Integer opcao = -1;
		while (opcao != 0) {
			System.out.println("Insira o ID de uma reserva para detalhar ou 0 para voltar ao menu: ");
			final Integer opcaoUsuario = this.insereNumero();

			if (opcaoUsuario == 0) {
				break;
			}

			Reserva reservaEscolhida = listaDeReservas.stream()
					.filter(reserva -> reserva.getReservaId().equals(opcaoUsuario)).findFirst().orElse(null);

			if (reservaEscolhida == null) {
				System.out.println("Reserva não encontrada.");
				continue;
			}

			List<ReservaDetalhe> reservaDetalhe = this.reservaDetalheDAOImpl
					.listarReservaDetalhePorReservaId(opcaoUsuario);

			if (reservaDetalhe.isEmpty()) {
				System.out.println("Nenhum vínculo de reserva encontrado.");
				continue;
			}

			System.out.println("Vinculos da reserva com o id " + opcaoUsuario + ": ");

			System.out.println("\n----------------------\n");
			reservaDetalhe.forEach(rd -> {
				Cliente cliente = this.clienteDAOImpl.buscaClientePorId(rd.getClienteId());

				if (cliente == null) {
					System.out.println("Cliente não encontrado.");
				} else {
					System.out.println("Cliente: " + cliente);
					System.out.println("\n----------------------\n");
				}

				Quarto quarto = this.quartoDAOImpl.buscaQuartoPorId(rd.getQuartoId());

				if (quarto == null) {
					System.out.println("Quarto não encontrado.");
				} else {
					System.out.println("Quarto: " + quarto);
					System.out.println("\n----------------------\n");
				}

				System.out.println("\n----------------------\n");
			});
			opcao = opcaoUsuario;
		}
	}

	private void listarTiposQuarto() {
		List<TipoQuarto> listaDeTiposQuarto = this.tipoQuartoDAOImpl.buscarTipoQuarto();
		if (listaDeTiposQuarto.isEmpty()) {
			System.out.println("Nenhum tipo de quarto cadastrado.");
			return;
		}
		listaDeTiposQuarto.forEach(tipoQuarto -> {
			System.out.println(tipoQuarto);
			System.out.println("\n----------------------\n");
		});

		this.pedeConfirmacaoVoltarMenu();
	}

	private ReservaDetalhe pedeClienteEQuartoDaReservaDetalhe(Reserva reserva) {
		System.out.println("Clientes disponíveis: ");
		this.clienteDAOImpl.listarCliente().forEach(cliente -> {
			System.out.println(cliente);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do cliente escolhido: ");
		Integer clienteId = this.insereNumero();

		List<Quarto> quartosDoHotel = new ArrayList<>();
		do {
			System.out.println("Selecione o hotel para a reserva: ");

			List<Hotel> listaDeHoteis = this.hotelDAOImpl.listarHotelComQuartosDisponiveis();

			if (listaDeHoteis.isEmpty()) {
				System.out.println(
						"\n\nNenhum hotel com quartos disponíveis cadastrado. Por favor, cadastre um hotel ou quarto ou aguarde o fim das reservas antes de continuar.\n\n");
				return null;
			}

			listaDeHoteis.forEach(hotel -> {
				System.out.println(hotel);
				System.out.println("\n----------------------\n");
			});

			System.out.println("Digite o ID do hotel escolhido: ");
			Integer hotelId = this.insereNumero();

			quartosDoHotel = this.quartoDAOImpl.buscarQuartoDisponivelPorHotelId(hotelId, reserva.getDataInicio(), reserva.getDataFim());

			if (quartosDoHotel.isEmpty()) {
				System.out.println("Hotel sem quartos disponíveis. Por favor, selecione outro hotel.");
			}

		} while (quartosDoHotel.isEmpty());

		System.out.println("Quartos disponíveis: ");

		quartosDoHotel.forEach(quarto -> {
			System.out.println(quarto);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do quarto escolhido: ");
		Integer quartoId = this.insereNumero();

		ReservaDetalhe reservaDetalhe = new ReservaDetalhe();

		reservaDetalhe.setClienteId(clienteId);
		reservaDetalhe.setQuartoId(quartoId);
		return reservaDetalhe;
	}

	private void pedeConfirmacaoVoltarMenu() {
		System.out.println("Pressione ENTER para voltar ao menu.");
		this.scanner.nextLine();
	}

	private Cliente pedeDadosCliente() {
		// Coletar dados do usuário
		System.out.println("Nome do Cliente: ");
		String nome = this.insereString();

		System.out.println("Email do Cliente: ");
		String email = this.insereString();

		System.out.println("Telefone do Cliente: ");
		String telefone = this.insereString();

		// Aqui você pode coletar mais dados do cliente, dependendo dos atributos da entidade Cliente

		// Criar o objeto Cliente
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setEmail(email);
		cliente.setTelefone(telefone);

		return cliente;
	}

	private Endereco pedeDadosEndereco() {
		System.out.println("Rua: ");
		String rua = this.insereString();

		System.out.println("Número: ");
		Integer numero = this.insereNumero();

		System.out.println("Bairro: ");
		String bairro = this.insereString();

		System.out.println("Cidade: ");
		String cidade = this.insereString();

		System.out.println("Estado: ");
		String estado = this.insereString();

		System.out.println("País: ");
		String pais = this.insereString();

		System.out.println("CEP: ");
		String cep = this.insereString();

		Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, pais, cep);
		return endereco;
	}

	private Hotel pedeDadosHotel() {
		System.out.println("Nome do Hotel: ");
		String nome = this.insereString();

		System.out.println("Classificação do Hotel: ");
		Integer classificacao = this.insereNumero();

		System.out.println("Data de Construção do Hotel (dd/MM/yyyy): ");
		Date dataConstrucao = this.lerData();

		System.out.println("Agora vamos cadastrar o endereço do Hotel ");

		Hotel hotel = new Hotel();

		hotel.setNome(nome);
		hotel.setClassificacao(classificacao);
		hotel.setDataConstrucao(dataConstrucao);
		return hotel;
	}

	private Quarto pedeDadosQuarto() {
		// Coletar dados do usuário
		System.out.println("Número do Quarto: ");
		Integer numero = this.insereNumero();

		System.out.println("Disponibilidade (true/false): ");
		boolean disponibilidade = this.scanner.nextBoolean();
		this.scanner.nextLine(); // Limpar o buffer do scanner

		System.out.println("Preço: ");
		BigDecimal preco = this.insereBigDecimal();

		// Criar o objeto Quarto
		Quarto quarto = new Quarto();
		quarto.setNumero(numero);
		quarto.setDisponibilidade(disponibilidade);
		quarto.setPreco(preco);

		List<Hotel> listaDeHoteis = this.hotelDAOImpl.listarHotel();

		if (listaDeHoteis.isEmpty()) {
			System.out.println(
					"\n\nNenhum hotel cadastrado. Por favor, cadastre um hotel antes de continuar.\n\n");
			return null;
		}

		listaDeHoteis.forEach(hotel -> {
			System.out.println(hotel);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do hotel escolhido: ");
		Integer hotelId = this.insereNumero();

		quarto.setHotelId(hotelId);
		return quarto;
	}

	private Reserva pedeDadosReserva() {
		System.out.println("Data de Início (dd/MM/yyyy): ");
		Date dataInicio = this.lerData();

		System.out.println("Data de Fim (dd/MM/yyyy): ");
		Date dataFim = this.lerData();

		System.out.println("Forma de Pagamento: ");
		String formaPagamento = this.insereString();

		Reserva reserva = new Reserva();

		reserva.setDataInicio(dataInicio);
		reserva.setDataFim(dataFim);
		reserva.setFormaPagamento(formaPagamento);
		return reserva;
	}

	private TipoQuarto pedeDadosTipoQuarto() {
		System.out.println("Tipo do Tipo de Quarto (Ex.: Suíte): ");
		String tipo = this.insereString();

		System.out.println("Descrição do Tipo de Quarto: ");
		String descricao = this.insereString();

		System.out.println("Capacidade do Tipo de Quarto (Ex.: Suíte com banheira, geladeira, etc): ");
		Integer capacidade = this.insereNumero();

		TipoQuarto tipoQuarto = new TipoQuarto();
		tipoQuarto.setDescricao(descricao);
		tipoQuarto.setCapacidade(capacidade);
		tipoQuarto.setTipo(tipo);
		return tipoQuarto;
	}

	@Autowired
	public void setClienteDAOImpl(ClienteDAOImpl clienteDAOImpl) {
		this.clienteDAOImpl = clienteDAOImpl;
	}

	@Autowired
	public void setEnderecoDAOImpl(EnderecoDAOImpl enderecoDAOImpl) {
		this.enderecoDAOImpl = enderecoDAOImpl;
	}

	@Autowired
	public void setHotelDAOImpl(HotelDAOImpl hotelDAOImpl) {
		this.hotelDAOImpl = hotelDAOImpl;
	}

	@Autowired
	public void setQuartoDAOImpl(QuartoDAOImpl quartoDAOImpl) {
		this.quartoDAOImpl = quartoDAOImpl;
	}

	@Autowired
	public void setReservaDAOImpl(ReservaDAOImpl reservaDAOImpl) {
		this.reservaDAOImpl = reservaDAOImpl;
	}

	@Autowired
	public void setReservaDetalheDAOImpl(ReservaDetalheDAOImpl reservaDetalheDAOImpl) {
		this.reservaDetalheDAOImpl = reservaDetalheDAOImpl;
	}

	@Autowired
	public void setTipoQuartoDAOImpl(TipoQuartoDAOImpl tipoQuartoDAOImpl) {
		this.tipoQuartoDAOImpl = tipoQuartoDAOImpl;
	}

	public void vinculaReserva() {
		System.out.println("===== Vincular Reserva =====");

		System.out.println("Clientes disponíveis: ");
		this.clienteDAOImpl.listarCliente().forEach(cliente -> {
			System.out.println(cliente);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do cliente escolhido: ");
		Integer clienteId = this.insereNumero();

		System.out.println("Reservas disponíveis: ");
		List<Reserva> listaDeReservas = this.reservaDAOImpl.listarReserva();

		listaDeReservas.forEach(reserva -> {
			System.out.println(reserva);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID da reserva escolhida: ");
		Integer reservaId = this.insereNumero();

		Reserva reservaEscolhida = listaDeReservas.stream().filter(reserva -> reserva.getReservaId().equals(reservaId))
				.findFirst().orElse(null);


		List<Quarto> quartosDoHotel = new ArrayList<>();
		do {
			System.out.println("Selecione o hotel para a reserva: ");

			List<Hotel> listaDeHoteis = this.hotelDAOImpl.listarHotelComQuartosDisponiveis();

			if (listaDeHoteis.isEmpty()) {
				System.out.println(
						"\n\nNenhum hotel com quartos disponíveis cadastrado. Por favor, cadastre um hotel ou quarto ou aguarde o fim das reservas antes de continuar.\n\n");
				return;
			}

			listaDeHoteis.forEach(hotel -> {
				System.out.println(hotel);
				System.out.println("\n----------------------\n");
			});

			System.out.println("Digite o ID do hotel escolhido: ");
			Integer hotelId = this.insereNumero();

			quartosDoHotel = this.quartoDAOImpl.buscarQuartoDisponivelPorHotelId(hotelId,
					reservaEscolhida.getDataInicio(), reservaEscolhida.getDataFim());

			if (quartosDoHotel.isEmpty()) {
				System.out.println("Hotel sem quartos disponíveis. Por favor, selecione outro hotel.");
			}

		} while (quartosDoHotel.isEmpty());

		System.out.println("Quartos disponíveis: ");

		quartosDoHotel.forEach(quarto -> {
			System.out.println(quarto);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do quarto escolhido: ");
		Integer quartoId = this.insereNumero();

		ReservaDetalhe reservaDetalhe = new ReservaDetalhe();

		reservaDetalhe.setClienteId(clienteId);
		reservaDetalhe.setQuartoId(quartoId);
		reservaDetalhe.setReservaId(reservaId);

		this.reservaDetalheDAOImpl.cadastrarReservaDetalhe(reservaDetalhe);

		System.out.println("Reserva vinculada com sucesso!");
	}


}
