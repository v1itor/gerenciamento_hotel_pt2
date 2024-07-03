package br.udesc.hospedagem.hoteis.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.Result;
import org.neo4j.driver.types.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.udesc.hospedagem.hoteis.Neo4jUtils;
import br.udesc.hospedagem.hoteis.dao.ClienteDAO;
import br.udesc.hospedagem.hoteis.model.Cliente;
import jakarta.transaction.Transactional;

@Service
public class ClienteDAOImpl implements ClienteDAO {

    @Autowired
    private Neo4jUtils neo4jUtils;

    @Transactional
    public void atualizarCliente(Cliente cliente) {
        String query = "MATCH (c:Cliente {clienteId: $clienteId}) " +
                       "SET c.nome = $nome, " +
                       "c.cpf = $cpf, " +
                       "c.email = $email, " +
                       "c.telefone = $telefone, " +
                       "c.enderecoId = $enderecoId";
        neo4jUtils.executeCypherQuery(query, 
            Map.of("clienteId", cliente.getClienteId(),
                   "nome", cliente.getNome(),
                   "cpf", cliente.getCpf(),
                   "email", cliente.getEmail(),
                   "telefone", cliente.getTelefone(),
                   "enderecoId", cliente.getEnderecoId()));
    }

    public Cliente buscaClientePorId(Integer clienteId) {
        String query = "MATCH (c:Cliente {clienteId: $clienteId}) RETURN c";
        Result result = neo4jUtils.executeReadQuery(query, Map.of("clienteId", clienteId));
        List<Cliente> listaRetorno = result.list(record -> {
            Node node = record.get("c").asNode();
            Cliente cliente = new Cliente();
            cliente.setClienteId(node.get("clienteId").asInt());
            cliente.setNome(node.get("nome").asString());
            cliente.setCpf(node.get("cpf").asString());
            cliente.setEmail(node.get("email").asString());
            cliente.setTelefone(node.get("telefone").asString());
            cliente.setEnderecoId(node.get("enderecoId").asInt());
            return cliente;
        });

        if (listaRetorno.isEmpty()) {
            return null;
        }
        return listaRetorno.get(0);
    }

	@Override
    public List<Map<String, String>> buscarInformacoesHospedagemCliente(Integer clienteId) {
        String query = """
                MATCH (c:Cliente {clienteId: $clienteId})-[:RESERVA_DETALHE]->(rd:ReservaDetalhe)-[:RESERVA]->(r:Reserva),
                      (rd)-[:QUARTO]->(q:Quarto)-[:HOTEL]->(h:Hotel)
                RETURN h.nome AS nome_hotel,
                       q.numero AS numero_quarto,
                       r.dataInicio AS data_inicio,
                       r.dataFim AS data_fim,
                       c.nome AS nome_cliente,
                       c.email AS email,
                       c.telefone AS telefone
                """;

        Result result = neo4jUtils.executeReadQuery(query, Map.of("clienteId", clienteId));

        List<Map<String, String>> resultadoRelatorio = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        result.list().forEach(record -> {
            Map<String, String> map = new HashMap<>();
            map.put("Nome do hotel", record.get("nome_hotel").asString());
            map.put("Numero do quarto", record.get("numero_quarto").asString());
            map.put("Data de inicio", sdf.format(record.get("data_inicio").asLocalDate()));
            map.put("Data de fim", sdf.format(record.get("data_fim").asLocalDate()));
            map.put("Nome do cliente", record.get("nome_cliente").asString());
            map.put("Email do cliente", record.get("email").asString());
            map.put("Telefone do cliente", record.get("telefone").asString());
            resultadoRelatorio.add(map);
        });

        return resultadoRelatorio;
    }

    @Transactional
    public void cadastrarCliente(Cliente cliente) {
        String query = "CREATE (c:Cliente {clienteId: $clienteId, nome: $nome, cpf: $cpf, email: $email, telefone: $telefone, enderecoId: $enderecoId})";
        neo4jUtils.executeCypherQuery(query, Map.of(
            "clienteId", cliente.getClienteId(),
            "nome", cliente.getNome(),
            "cpf", cliente.getCpf(),
            "email", cliente.getEmail(),
            "telefone", cliente.getTelefone(),
            "enderecoId", cliente.getEnderecoId()
        ));
    }
    
    @Transactional
    public void deletarCliente(Integer clienteId) {
        String queryReservaDetalhe = "MATCH (c:Cliente {clienteId: $clienteId})-[r:RESERVA_DETALHE]->() DELETE r";
        neo4jUtils.executeCypherQuery(queryReservaDetalhe, Map.of("clienteId", clienteId));

        String query = "MATCH (c:Cliente {clienteId: $clienteId}) DELETE c";
        neo4jUtils.executeCypherQuery(query, Map.of("clienteId", clienteId));
    }


   public List<Cliente> listarCliente() {
        String query = "MATCH (c:Cliente) RETURN c ORDER BY c.clienteId DESC";
        Result result = neo4jUtils.executeReadQuery(query, Map.of());

        List<Cliente> listaRetorno = new ArrayList<>();
        result.list().forEach(record -> {
            Node node = record.get("c").asNode();
            Cliente cliente = new Cliente();
            cliente.setClienteId(node.get("clienteId").asInt());
            cliente.setNome(node.get("nome").asString());
            cliente.setCpf(node.get("cpf").asString());
            cliente.setEmail(node.get("email").asString());
            cliente.setTelefone(node.get("telefone").asString());
            cliente.setEnderecoId(node.get("enderecoId").asInt());
            listaRetorno.add(cliente);
        });

        return listaRetorno;
    }
}
