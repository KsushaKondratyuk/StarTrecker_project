package com.grokonez.jwtauthentication.controller; 

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.grokonez.jwtauthentication.model.TripsInfo;


@RestController
@RequestMapping("/api/carrier")
@PreAuthorize("hasRole('CARRIER')")
public class CarrierController {
	
	@Autowired
	RestTemplate restTemp;
	
	@RequestMapping(value = "/createTrip", method = RequestMethod.POST)
	public String CreateTrip(@Valid @RequestBody TripsInfo trip) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<TripsInfo> entity = new HttpEntity<>(trip, headers);
        
        ResponseEntity<TripsInfo> createdTrip  = restTemp.exchange("http://trip-service/insertTrip", HttpMethod.POST, entity, TripsInfo.class);
        return "Trip " + createdTrip.getBody().getTripId() + " added!";
	}
	
	@RequestMapping(value = "/deleteTrip", method = RequestMethod.POST)
	public String DeleteTrip(@Valid @RequestBody TripsInfo trip) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<TripsInfo> entity = new HttpEntity<>(trip, headers);
        
        ResponseEntity<TripsInfo> deletedTrip = restTemp.exchange("http://trip-service/deleteTrip",HttpMethod.POST, entity, TripsInfo.class);
        return "Trip " + deletedTrip.getBody().getTripId() + " deleted!";
		
	}

}