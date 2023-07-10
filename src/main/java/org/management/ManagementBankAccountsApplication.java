package org.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ManagementBankAccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementBankAccountsApplication.class, args);
	}

}
