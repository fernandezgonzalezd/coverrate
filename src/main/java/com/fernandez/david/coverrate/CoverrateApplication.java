package com.fernandez.david.coverrate;

import com.fernandez.david.coverrate.service.ZipCompress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class CoverrateApplication {

	@Resource
	ZipCompress zipCompress;

	public static void main(String[] args) {
		SpringApplication.run(CoverrateApplication.class, args);
	}

}
