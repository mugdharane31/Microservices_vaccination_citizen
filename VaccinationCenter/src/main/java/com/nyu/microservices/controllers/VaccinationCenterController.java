package com.nyu.microservices.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nyu.microservices.Entity.VaccinationCenter;
import com.nyu.microservices.Model.Citizen;
import com.nyu.microservices.Model.RequiredResponse;
import com.nyu.microservices.Repos.CenterRepo;
import com.nyu.microservices.user.UserRepo;
import com.nyu.microservices.user.Entity.UserBean;



@RestController
@RequestMapping("/vaccinationcenter")
public class VaccinationCenterController {
	
	@Autowired
	private CenterRepo centerRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	@RequestMapping(path ="/test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<>("vaccinationcenter hello", HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(path ="/login", method=RequestMethod.GET)
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
		System.out.println("email = " + email + " password = " + password);
		UserBean center  = userRepo.findByEmail(email).get(0);
		if(center.getPassword().equals(password)) {
			return new ResponseEntity<>("vaccinationcenter logged in", HttpStatus.OK);
		}
		return new ResponseEntity<>("vaccinationcenter not logged in", HttpStatus.FORBIDDEN);
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(path ="/getusers", method=RequestMethod.GET)
	public ResponseEntity<RequiredResponse> getUsers() {
		List<UserBean> users  = userRepo.findAll();
		RequiredResponse requiredResponse =  new RequiredResponse();
		requiredResponse.setUsers(users);
		return new ResponseEntity<RequiredResponse>(requiredResponse, HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(path ="/createuser")
	public ResponseEntity<UserBean> createUser(@RequestParam String fName, @RequestParam String lName, @RequestParam String email, @RequestParam String password) {
		UserBean userbean = new UserBean();
		userbean.setFirstName(fName);
		userbean.setLastName(lName);
		userbean.setEmail(email);
		userbean.setPassword(password);
		UserBean userAdded = userRepo.save(userbean);
		return new ResponseEntity<>(userAdded, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(path ="/deleteuser")
	@Transactional
	public ResponseEntity<String> deleteUser(@RequestParam String email) {

		userRepo.deleteByEmail(email);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@GetMapping(path = "/id/{id}")
	public ResponseEntity<RequiredResponse> getAllDadaBasedonCenterId(@PathVariable Integer id){
		RequiredResponse requiredResponse =  new RequiredResponse();
		//1st get vaccination center detail
		VaccinationCenter center  = centerRepo.findById(id).get();
		//requiredResponse.setCenter(center);
		
		// then get all citizen registerd to vaccination center
		
		java.util.List<Citizen> listOfCitizens = restTemplate.getForObject("http://CITIZEN-SERVICE/citizen/id/"+id, List.class);
		//requiredResponse.setCitizens(listOfCitizens);
		return new ResponseEntity<RequiredResponse>(requiredResponse, HttpStatus.OK);
	}
	
	
	
	
	

}
