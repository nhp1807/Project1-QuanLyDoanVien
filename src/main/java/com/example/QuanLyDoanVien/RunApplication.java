package com.example.QuanLyDoanVien;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RunApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
		// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// System.out.println(encoder.encode("phong"));
		// System.out.println(encoder.encode("phong"));
	}
}
