package com.mashirro.xinfang_system;

import com.mashirro.xinfang_common.util.spring.ApplicationContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class XinfangSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(XinfangSystemApplication.class, args);
	}

	@Bean
	public ApplicationContextUtil applicationContextUtil(){
		return new ApplicationContextUtil();
	}


}
