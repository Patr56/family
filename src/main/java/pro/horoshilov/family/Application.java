package pro.horoshilov.family;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("pro.horoshilov.family")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}