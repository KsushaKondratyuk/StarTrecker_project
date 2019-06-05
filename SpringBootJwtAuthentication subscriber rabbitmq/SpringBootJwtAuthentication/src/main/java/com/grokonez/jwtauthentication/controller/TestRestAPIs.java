package com.grokonez.jwtauthentication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestAPIs {
	
	@GetMapping("/api/test/user")
	@PreAuthorize("hasRole('USER') or hasRole('CARRIER')")
	public String userAccess() {
		return ">>> User Contents!";
	}

	
	@GetMapping("/api/test/carrier")
	@PreAuthorize("hasRole('CARRIER')")
	public String carrierAccess() {
		return ">>> Carrier Contents";
	}
}