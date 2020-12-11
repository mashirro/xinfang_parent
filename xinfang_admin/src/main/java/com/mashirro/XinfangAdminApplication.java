package com.mashirro;

import com.mashirro.xinfang_common.util.spring.ApplicationContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.mashirro")
public class XinfangAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(XinfangAdminApplication.class, args);
	}

	@Bean
	public ApplicationContextUtil applicationContextUtil(){
		return new ApplicationContextUtil();
	}
}
