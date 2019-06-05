package com.grokonez.jwtauthentication.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.grokonez.jwtauthentication.message.request.LoginForm;
import com.grokonez.jwtauthentication.message.request.SignUpForm;
import com.grokonez.jwtauthentication.message.response.JwtResponse;
import com.grokonez.jwtauthentication.model.Comment;
import com.grokonez.jwtauthentication.model.Role;
import com.grokonez.jwtauthentication.model.RoleName;
import com.grokonez.jwtauthentication.model.TripDateDirection;
import com.grokonez.jwtauthentication.model.TripsInfo;
import com.grokonez.jwtauthentication.model.User;
import com.grokonez.jwtauthentication.repository.RoleRepository;
import com.grokonez.jwtauthentication.repository.UserRepository;
import com.grokonez.jwtauthentication.security.jwt.JwtProvider;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
	
	@Autowired
	RestTemplate restTemp;
	
	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public List<Comment> AllComments(){
		ResponseEntity<List<Comment>> response = restTemp.exchange("http://comments-service/api/comments/allComments", HttpMethod.GET , null ,new ParameterizedTypeReference<List<Comment>>() {});
		return response.getBody();
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

	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
        	switch(role) {
	    		case "carrier":
	    			Role carrierRole = roleRepository.findByName(RoleName.ROLE_CARRIER)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	    			roles.add(carrierRole);
	    			
	    			break;
	    		default:
	        		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	        		roles.add(userRole);        			
        	}
        });
        
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }
}