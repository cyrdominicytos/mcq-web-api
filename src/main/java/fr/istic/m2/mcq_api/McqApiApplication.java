package fr.istic.m2.mcq_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McqApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(McqApiApplication.class, args);
		System.out.println("========= API successfully run ! ===========");
	}

}
