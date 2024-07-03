package br.udesc.hospedagem.hoteis;

import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Neo4jUtils {

    @Autowired
    private Driver driver;

    public void executeCypherQuery(String query) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run(query);
                return null;
            });
        }
    }

    public Result executeReadQuery(String query) {
        try (Session session = driver.session()) {
            return session.readTransaction(tx -> tx.run(query));
        }
    }
    
    public void executeCypherQuery(String query, Map<String, Object> parameters) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run(query, Values.parameters(parameters));
                return null;
            });
        }
    }

    public Result executeReadQuery(String query, Map<String, Object> parameters) {
        try (Session session = driver.session()) {
            return session.readTransaction(tx -> tx.run(query, Values.parameters(parameters)));
        }
    }
}
