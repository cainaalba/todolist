package br.com.caina.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistApplication {

	//rodar via terminal: mvn spring-boot:run
	//parar via terminal: ctrl + c
	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

}
