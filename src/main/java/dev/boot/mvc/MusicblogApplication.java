package dev.boot.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.boot"})
public class MusicblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicblogApplication.class, args);
	}

}
