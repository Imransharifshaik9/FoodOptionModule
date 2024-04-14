package com.fsd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsd.entity.FoodOption;
import com.fsd.exceptions.CreateFoodOptionException;
import com.fsd.exceptions.DeleteFoodOptionException;
import com.fsd.exceptions.GetAllFoodOptionsByEventIdException;
import com.fsd.exceptions.GetAllFoodOptionsException;
import com.fsd.exceptions.GetFoodOptionByIdException;
import com.fsd.exceptions.UpdateFoodOptionException;
import com.fsd.feign.EventClient;
import com.fsd.repository.FoodOptionRepository;

@Service
public class FoodOptionService {

    @Autowired
    private FoodOptionRepository foodOptionRepository;
    
    @Autowired
    EventClient eClient;

   


    public FoodOption createFoodOption(FoodOption foodOption) {
        try {
            return foodOptionRepository.save(foodOption);
        } catch (Exception e) {
            throw new CreateFoodOptionException("Error occurred while creating food option: " + e.getMessage());
        }
    }

    public List<FoodOption> getAllFoodOptions() {
        try {
            return foodOptionRepository.findAll();
        } catch (Exception e) {
            throw new GetAllFoodOptionsException("Error occurred while retrieving food options: " + e.getMessage());
        }
    }

    public FoodOption getFoodOptionById(Long id) {
        try {
            Optional<FoodOption> optionalFoodOption = foodOptionRepository.findById(id);
            return optionalFoodOption.orElseThrow(() -> new GetFoodOptionByIdException("Food option not found with ID: " + id));
        } catch (Exception e) {
            throw new GetFoodOptionByIdException("Error occurred while retrieving food option: " + e.getMessage());
        }
    }

    public FoodOption updateFoodOption(Long id, FoodOption foodOption) {
        try {
            if (foodOptionRepository.existsById(id)) {
                foodOption.setId(id);
                return foodOptionRepository.save(foodOption);
            } else {
                throw new UpdateFoodOptionException("Food option not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new UpdateFoodOptionException("Error occurred while updating food option: " + e.getMessage());
        }
    }

    public void deleteFoodOption(Long id) {
        try {
            if (foodOptionRepository.existsById(id)) {
                foodOptionRepository.deleteById(id);
            } else {
                throw new DeleteFoodOptionException("Food option not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new DeleteFoodOptionException("Error occurred while deleting food option: " + e.getMessage());
        }
    }

    public List<FoodOption> getAllFoodOptionsByEventId(Long eventId) {
        try {
            if (eClient.getEventById(eventId) != null) {
                return foodOptionRepository.findByEventId(eventId);
            } else {
                throw new GetAllFoodOptionsByEventIdException("Event not found with ID: " + eventId);
            }
        } catch (Exception e) {
            throw new GetAllFoodOptionsByEventIdException("Error occurred while retrieving food options by event ID: " + e.getMessage());
        }
    }
    
    
    
    
    
    
    
}
