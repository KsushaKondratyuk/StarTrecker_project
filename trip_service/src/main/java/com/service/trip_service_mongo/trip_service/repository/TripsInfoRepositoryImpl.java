package com.service.trip_service_mongo.trip_service.repository;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.service.trip_service_mongo.trip_service.models.TripDateDirection;
import com.service.trip_service_mongo.trip_service.models.TripsInfo;

@Repository
public class TripsInfoRepositoryImpl {
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	public List<TripsInfo> getAllPossibleTripsFromTo(String From, String To){
		Query query = new Query();
		query.addCriteria(Criteria.where("departurePlanet").is(From));
		query.addCriteria(Criteria.where("arrivalPlanet").is(To));
		
		List<TripsInfo> result = mongoTemplate.find(query, TripsInfo.class);
		
		return result;
	}
	
	
	public List<TripsInfo> TripsDateFromTo(TripDateDirection trips){
		Query query = new Query();
		java.time.LocalDateTime date = trips.getDepartureDate();
		query.addCriteria(Criteria.where("departurePlanet").is(trips.getDeparturePlanet()));
		query.addCriteria(Criteria.where("arrivalPlanet").is(trips.getArrivalPlanet()));
		query.addCriteria(Criteria.where("departureDate").is(date));
		
		List<TripsInfo> result = mongoTemplate.find(query, TripsInfo.class);
		return result;
	}
	
	
	public List<TripsInfo> TripsByDepartureList(String name){
		Query query = new Query();
		query.addCriteria(Criteria.where("departurePlanet").is(name));
		List<TripsInfo> result = mongoTemplate.find(query, TripsInfo.class);
		return result;
	}
	
	public List<TripsInfo> TripsByArrivalList(String name){
		Query query = new Query();
		query.addCriteria(Criteria.where("arrivalPlanet").is(name));
		List<TripsInfo> result = mongoTemplate.find(query, TripsInfo.class);
		return result;
	}
	
	public List<TripsInfo> AllTrips(){
		return mongoTemplate.findAll(TripsInfo.class);
	}
	
	public TripsInfo TripByID(String TripId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("tripId").is(TripId));
		
		TripsInfo result = mongoTemplate.findOne(query, TripsInfo.class);
		return result;
	}
	
	public void Insert(TripsInfo trip) {
		mongoTemplate.insert(trip);
	}
	
	public void Delete(TripsInfo trip) {
		String tripId = trip.getTripId();
		mongoTemplate.remove(mongoTemplate.findById(tripId, TripsInfo.class));		
	}
	
	public void DeleteById(String TripId) {
		mongoTemplate.remove(mongoTemplate.findById(TripId, TripsInfo.class));
	}
	
	public void Update(TripsInfo trip) {
		Query query = new Query();
		query.addCriteria(Criteria.where("tripId").is(trip.getTripId()));

		Update update = new Update();
		update.set("departurePlanet", trip.getDeparturePlanet());
		update.set("arrivalPlanet", trip.getArrivalPlanet());
		update.set("departureDate", trip.getDepartureDate());
		update.set("arrivalDate", trip.getArrivalDate());
		update.set("places", trip.getPlaces());
		update.set("price", trip.getPrice());
		
		mongoTemplate.updateFirst(query, update, TripsInfo.class);
		
		System.out.println("Updated, places : " + trip.getPlaces());
		
	}
	
}
