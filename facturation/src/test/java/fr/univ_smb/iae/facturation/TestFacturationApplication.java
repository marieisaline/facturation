package fr.univ_smb.iae.facturation;

import org.springframework.boot.SpringApplication;

public class TestFacturationApplication {

	public static void main(String[] args) {
		SpringApplication.from(FacturationApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
