package org.revo.pere;

import org.revo.pere.domain.Company;
import org.revo.pere.service.CompanyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PereApplication {

    public static void main(String[] args) {
        SpringApplication.run(PereApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(CompanyService companyService) {
        return args -> {
            companyService.save(Company.builder().name("asge").email("fdfd@fd.com").build());
            companyService.save(Company.builder().name("asge").email("fdfd@fd.com").build());
        };
    }
}