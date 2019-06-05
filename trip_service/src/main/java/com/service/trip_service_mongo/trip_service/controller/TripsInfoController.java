package com.service.trip_service_mongo.trip_service.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.trip_service_mongo.trip_service.models.TripDateDirection;
import com.service.trip_service_mongo.trip_service.models.TripsInfo;
import com.service.trip_service_mongo.trip_service.repository.TripsInfoRepository;

@RestController
@RequestMapping("/")
public class TripsInfoController {
	
	@Autowired
	private TripsInfoRepository TripsInfoRep;
		
	
	@RequestMapping(value = "/trips", method = RequestMethod.GET)
	public List<TripsInfo> showTrips() {
		List<TripsInfo> trips = this.TripsInfoRep.AllTrips();
		return trips;
	}
	
	@RequestMapping(value = "/tripsByDeparture/{planet}", method = RequestMethod.GET)
	public List<TripsInfo> showTripsByDeparture(@PathVariable("planet")String planet){
		List<TripsInfo> trips = this.TripsInfoRep.TripsByDepartureList(planet);
		return trips;
	}
	
	@RequestMapping(value = "/tripsByArrival/{planet}", method = RequestMethod.GET)
	public List<TripsInfo> showTripsByArrival(@PathVariable("planet") String planet){
		List<TripsInfo> trips = this.TripsInfoRep.TripsByArrivalList(planet);
		return trips;
	}
	
	@RequestMapping(value = "/tripsByDepArr/{planet1}/{planet2}", method = RequestMethod.GET)
	public List<TripsInfo> showTripsByDepArr(@PathVariable("planet1") String planet1, @PathVariable String planet2){
		List<TripsInfo> trips = TripsInfoRep.getAllPossibleTripsFromTo(planet1, planet2);
		return trips;
	}
	

	@RequestMapping(value = "/tripsDateFromTo", method = RequestMethod.POST)
	public List<TripsInfo> TripsDateFromTo(@Valid @RequestBody TripDateDirection trips) {
		List<TripsInfo> trip = this.TripsInfoRep.TripsDateFromTo(trips);
		return trip;
	}
	
	@RequestMapping(value = "/trips/{tripId}", method = RequestMethod.GET)
	public TripsInfo OneTrip(@PathVariable("tripId") String tripId) {
		TripsInfo trip = this.TripsInfoRep.TripByID(tripId);
		return trip;
	}
	
	@RequestMapping(value = "/deleteTrip", method = RequestMethod.POST)
	public void DeleteTrip(@Valid @RequestBody TripsInfo trip) {
		TripsInfoRep.Delete(trip);
	}
	
	@RequestMapping(value = "/deleteTripById/{tripId}", method = RequestMethod.DELETE)
	public void DeleteTripById(@PathVariable("tripId") String tripId) {
		TripsInfoRep.DeleteById(tripId);
	}
	
	@RequestMapping(value = "/insertTrip", method = RequestMethod.POST)
	public TripsInfo InsertTrip(@Valid @RequestBody TripsInfo trip) {
		TripsInfoRep.Insert(trip);
		return this.TripsInfoRep.TripByID(trip.getTripId());
	}
	
	@RequestMapping(value = "/updateTrip", method = RequestMethod.POST)
	public void UpdateTrip(@Valid @RequestBody TripsInfo trip) {
		TripsInfoRep.Update(trip);
	}
	
	
}
