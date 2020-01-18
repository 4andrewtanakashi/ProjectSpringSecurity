package br.com.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SsTestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsTestsApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("0000"));
	}

}
