package com.tutorial.temperatureservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@Value("${build.version}")
	private String buildVersion;
	
	@RequestMapping("/")
	public String home() {
		return "Temperature Service running version: " + buildVersion;
	}
	
	@RequestMapping(value = "/temp", produces = { "text/plain; charset=UTF-8" })
	public ResponseEntity<String> getTemperature() {
		String temp = buildVersion.equals("1.0.0") ? "hot" : "cold";
		return new ResponseEntity<>(temp, HttpStatus.OK);
	}
}
