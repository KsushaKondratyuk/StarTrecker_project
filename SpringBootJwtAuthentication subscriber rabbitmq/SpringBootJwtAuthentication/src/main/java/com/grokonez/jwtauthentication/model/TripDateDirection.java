package com.grokonez.jwtauthentication.model;

import java.time.LocalDateTime;

public class TripDateDirection {
	
	private LocalDateTime departureDate;
	
	private String departurePlanet;
		
	private String arrivalPlanet;
	

	public TripDateDirection() {

	}

	public TripDateDirection(String from, String to, LocalDateTime departureDate) {
		this.departureDate = departureDate;
		this.departurePlanet = from;
		this.arrivalPlanet = to;
	}

	public LocalDateTime getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}

	public String getDeparturePlanet() {
		return departurePlanet;
	}

	public void setDeparturePlanet(String departurePlanet) {
		this.departurePlanet = departurePlanet;
	}

	public String getArrivalPlanet() {
		return arrivalPlanet;
	}

	public void setArrivalPlanet(String arrivalPlanet) {
		this.arrivalPlanet = arrivalPlanet;
	}

	
	
}
