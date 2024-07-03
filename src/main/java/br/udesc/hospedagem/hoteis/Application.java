package br.udesc.hospedagem.hoteis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		context.close();
	}

	@Autowired
	private Menu menu;

	@PostConstruct
	public void runMenu() {
		try {
			this.menu.exibeMenu();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			this.menu.exibeMenu();
		}
	}
}