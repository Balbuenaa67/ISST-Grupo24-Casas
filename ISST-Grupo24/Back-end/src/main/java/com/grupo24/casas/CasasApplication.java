package com.grupo24.casas;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc

@ComponentScan
@SpringBootApplication
public class CasasApplication {


	public static void main(String[] args) {
		SpringApplication.run(CasasApplication.class, args);
	}


}