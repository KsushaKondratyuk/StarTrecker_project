package com.grokonez.jwtauthentication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.grokonez.jwtauthentication.model.Comment;
import com.grokonez.jwtauthentication.model.HistoryDTO;
import com.grokonez.jwtauthentication.model.TripDateDirection;
import com.grokonez.jwtauthentication.model.TripsInfo;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	RestTemplate restTemp;
	
	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public List<Comment> AllComments(){
		ResponseEntity<List<Comment>> response = restTemp.exchange("http://comments-service/api/comments/allComments", HttpMethod.GET , null ,new ParameterizedTypeReference<List<Comment>>() {});
		return response.getBody();
	}
	
	@RequestMapping(value = "/comments/addComment", method = RequestMethod.POST)
	public Comment AddComment(@Valid @RequestBody Comment text) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Comment> entity = new HttpEntity<>(text, headers);
        
        ResponseEntity<Comment> added_comment  = restTemp.exchange("http://comments-service/api/comments/addComment", HttpMethod.POST, entity, Comment.class);
        return added_comment.getBody();
	}
	
	@RequestMapping(value = "/trips", method = RequestMethod.GET)
	public List<TripsInfo> AllTrips() {
		ResponseEntity<List<TripsInfo>> response = restTemp.exchange("http://trip-service/trips", HttpMethod.GET , null ,new ParameterizedTypeReference<List<TripsInfo>>() {});
		return response.getBody();

	}
	
	@RequestMapping(value = "/trip/{id}", method = RequestMethod.GET)
    public TripsInfo findTripById(@PathVariable("id") String tripId) {
    	return restTemp.getForObject("http://trip-service/trips/" + tripId, TripsInfo.class);
    }
	
	@RequestMapping(value = "/yourMagicTrip", method = RequestMethod.POST)
	public List<TripsInfo> FromTo(@Valid @RequestBody TripDateDirection trips) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<TripDateDirection> entity = new HttpEntity<>(trips, headers);
        
        ResponseEntity<List<TripsInfo>> responded_trips  = restTemp.exchange("http://trip-service/tripsDateFromTo", HttpMethod.POST, entity, new ParameterizedTypeReference<List<TripsInfo>>() {});
		return responded_trips.getBody();
	}
	
	@RequestMapping(value = "/trips/From/{planet}", method = RequestMethod.GET)
	public List<TripsInfo> TripsByDepPlanet(@PathVariable("planet") String planet){
		ResponseEntity<List<TripsInfo>> response = restTemp.exchange("http://trip-service/tripsByDeparture/" + planet, HttpMethod.GET , null ,new ParameterizedTypeReference<List<TripsInfo>>() {});
		return response.getBody();
	}
	
	@RequestMapping(value = "/trips/To/{planet}", method = RequestMethod.GET)
	public List<TripsInfo> TripsByArrivalPlanet(@PathVariable("planet") String planet){
		ResponseEntity<List<TripsInfo>> response = restTemp.exchange("http://trip-service/tripsByArrival/" + planet, HttpMethod.GET , null ,new ParameterizedTypeReference<List<TripsInfo>>() {});
		return response.getBody();
	}
	
	@RequestMapping(value = "/trips/FromTo/{planet1}/{planet2}", method = RequestMethod.GET)
	public List<TripsInfo> TripsByDepartureArrivalPlanet(@PathVariable("planet1") String planet1,@PathVariable("planet2") String planet2){
		ResponseEntity<List<TripsInfo>> response = restTemp.exchange("http://trip-service/tripsByDepArr/" + planet1 + "/" + planet2, HttpMethod.GET , null ,new ParameterizedTypeReference<List<TripsInfo>>() {});
		return response.getBody();
	}
		

}
