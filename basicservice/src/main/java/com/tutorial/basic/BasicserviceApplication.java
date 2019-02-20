package com.tutorial.basic;

//import org.slf4j.Logger;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class BasicserviceApplication {
	
//	private static final Logger logger = Logger.getLogger(BasicserviceApplication.class);
	

	public static void main(String[] args) {
		SpringApplication.run(BasicserviceApplication.class, args);
	}

}

