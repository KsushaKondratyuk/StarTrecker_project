package com.service.trip_service_mongo.trip_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.service.trip_service_mongo.trip_service.models.TripDateDirection;
import com.service.trip_service_mongo.trip_service.models.TripsInfo;

public interface TripsInfoRepository extends MongoRepository<TripsInfo, Long>{
	
	
	public List<TripsInfo> TripsByDepartureList(String Departure);
	
	public List<TripsInfo> TripsByArrivalList(String Arrival);
	
	public List<TripsInfo> getAllPossibleTripsFromTo(String From, String To);
	
	public List<TripsInfo> AllTrips();
	
	public List<TripsInfo> TripsDateFromTo(TripDateDirection trips);

	public TripsInfo TripByID(String tripId);
	
	public void Insert(TripsInfo trip);
	
	public void Delete(TripsInfo trip);
	
	public void DeleteById(String TripId);
	
	public void Update(TripsInfo trip);

}
