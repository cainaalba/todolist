package br.com.caina.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistApplication {

	//rodar via terminal: mvn spring-boot:run
	//parar via terminal: ctrl + c
	//acessar h2: http://localhost:8080/h2-console
	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

}
