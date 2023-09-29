package cl.rs.bci.exercise.BCIExcercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;

@SpringBootApplication
@EnableFeignClients("cl.rs.bci")
@ComponentScan("cl.rs.bci")

public class BciExcerciseApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BciExcerciseApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "9091"));
		app.run(args);
	}

}
