package com.example.springcashier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class SpringCashierApplication {

	public static void main(String[] args) {

		// REF: https://docs.oracle.com/javase/8/docs/api/java/lang/System.html
		System.out.println("YOYOYOYOYOYOYO MYSQL_HOST: " + System.getenv("MYSQL_HOST"));

		// Start Spring App
		SpringApplication.run(SpringCashierApplication.class, args);
	}

}
