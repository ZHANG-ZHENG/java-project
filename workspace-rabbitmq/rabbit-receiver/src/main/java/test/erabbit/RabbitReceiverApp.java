package test.erabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RabbitReceiverApp {
    public static void main(String[] args) {
    	//http://localhost:15672/
        SpringApplication.run(RabbitReceiverApp.class, args);
    }
}