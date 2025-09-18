package br.com.moreproductive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoreProductiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoreProductiveApplication.class, args);
	}
    /*
    * Exceptions funcionando sem o try catch dentro do controller.
    * Post, get, put, delete, tudo sem /salvar, /deletar, só no metodo
    * Validações funcionando corretamente*/
}
