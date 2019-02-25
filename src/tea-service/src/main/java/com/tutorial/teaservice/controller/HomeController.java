package com.tutorial.teaservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HomeController {

	@Value("${build.version}")
	private String buildVersion;
	
	private final RestTemplate restTemplate = new RestTemplate();
	private static final String ENDPOINT = "http://temperature-service:9080";
	
	@RequestMapping("/")
	public String home() {
		return "Tea Service running version: " + buildVersion;
	}
	
	@RequestMapping(value = "/tea", produces = { "text/plain; charset=UTF-8" })
	public ResponseEntity<String> getTea() {
		String temp = restTemplate.getForEntity(ENDPOINT + "/temp", String.class).getBody();
		return new ResponseEntity<>("tea is " + temp, HttpStatus.OK);
	}
}
