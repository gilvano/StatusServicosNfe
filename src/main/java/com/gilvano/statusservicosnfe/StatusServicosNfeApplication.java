package com.gilvano.statusservicosnfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StatusServicosNfeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatusServicosNfeApplication.class, args);
    }

}
