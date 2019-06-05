package com.grokonez.jwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class HistoryDTO {

	@JsonProperty("bought_date")
    private List<LocalDateTime> boughtDate;

	@JsonProperty("trip")
    private List<TripsInfo> trip;
    
    public HistoryDTO(List<LocalDateTime> boughtDate, List<TripsInfo> trip) {
    	this.boughtDate = boughtDate;
    	this.trip = trip;
    }
//--------------------    
    /*public List<LocalDateTime> getBoughtDate() {
		return boughtDate;
	}

	public void setBoughtDate(List<LocalDateTime> boughtDate) {
		this.boughtDate = boughtDate;
	}
	
	public List<Trip> getTrip() {
		return trip;
	}

	public void setTrip(List<Trip> trip) {
		this.trip = trip;
	}*/
}
