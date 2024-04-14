package com.fsd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.entity.Event;
import com.fsd.entity.FoodOption;
import com.fsd.feign.EventClient;
import com.fsd.service.FoodOptionService;

@RestController
@RequestMapping("/foodOptions")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", origins = "http://localhost:3000", methods =  {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD}, maxAge = 3600L, exposedHeaders = "Access-Control-Allow-Origin")

public class FoodOptionController {

    @Autowired
    private FoodOptionService foodOptionService;
    
    @Autowired
    private EventClient eClient;
    
     public FoodOptionController() {
    	 
    	 
    	 
     }
    
    @PostMapping
    public ResponseEntity<FoodOption> createFoodOption(@RequestBody FoodOption foodOption) throws Exception {
        FoodOption createdFoodOption = foodOptionService.createFoodOption(foodOption);
        return new ResponseEntity<>(createdFoodOption, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FoodOption>> getAllFoodOptions() throws Exception {
        List<FoodOption> foodOptions = foodOptionService.getAllFoodOptions();
        return new ResponseEntity<>(foodOptions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodOption> getFoodOptionById(@PathVariable Long id) throws Exception{
        FoodOption foodOption = foodOptionService.getFoodOptionById(id);
        if (foodOption != null) {
            return new ResponseEntity<>(foodOption, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodOption> updateFoodOption(@PathVariable Long id, @RequestBody FoodOption foodOption) throws Exception{
        FoodOption updatedFoodOption = foodOptionService.updateFoodOption(id, foodOption);
        if (updatedFoodOption != null) {
            return new ResponseEntity<>(updatedFoodOption, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodOption(@PathVariable Long id) throws Exception {
        foodOptionService.deleteFoodOption(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
    @GetMapping("/event/{eventId}")
    public List<FoodOption> getAllFoodOptionsByEventId(@PathVariable Long eventId) throws Exception {
        return foodOptionService.getAllFoodOptionsByEventId(eventId);
        
    }
    
    @GetMapping("/allevents/{eventId}")
    public Event getAllEvents(@PathVariable Long eventId) throws Exception {
        return eClient.getEventById(eventId);
    }

    
}

