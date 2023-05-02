package me.kreaktech.unility;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UtilityApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UtilityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
