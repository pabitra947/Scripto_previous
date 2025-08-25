package com.example.scripto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//This is used to run the entire programs
@SpringBootApplication
@EnableTransactionManagement
public class ScriptoApplication {

	public static void main(String[] args) {

		SpringApplication.run(ScriptoApplication.class, args);
	}

}
