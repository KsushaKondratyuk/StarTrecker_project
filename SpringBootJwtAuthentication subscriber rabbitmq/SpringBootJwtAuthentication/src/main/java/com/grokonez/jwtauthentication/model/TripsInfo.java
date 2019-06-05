package com.grokonez.jwtauthentication.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class TripsInfo {
	
		@Id
	 	private String tripId;

	    private LocalDateTime departureDate;

	    private String departurePlanet;

	    private LocalDateTime arrivalDate;

	    private String arrivalPlanet;
	    
	    private int price;
	    
	    private int places;
	
	public TripsInfo() {
		
	}
	
	public TripsInfo(String tripId, String From, LocalDateTime DepDate, String To, LocalDateTime ArrDate, int price, int places) {
		this.tripId = tripId;
		this.departurePlanet = From;
		this.departureDate = DepDate;
		this.arrivalPlanet = To;
		this.arrivalDate = ArrDate;
		this.price = price;
		this.places = places;
		
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
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

	public LocalDateTime getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDateTime arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getArrivalPlanet() {
		return arrivalPlanet;
	}

	public void setArrivalPlanet(String arrivalPlanet) {
		this.arrivalPlanet = arrivalPlanet;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPlaces() {
		return places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}
	
	
}
