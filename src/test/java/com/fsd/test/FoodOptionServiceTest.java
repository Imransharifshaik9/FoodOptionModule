package com.fsd.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fsd.entity.Event;
import com.fsd.entity.FoodOption;
import com.fsd.exceptions.CreateFoodOptionException;
import com.fsd.exceptions.DeleteFoodOptionException;
import com.fsd.exceptions.GetAllFoodOptionsByEventIdException;
import com.fsd.exceptions.GetAllFoodOptionsException;
import com.fsd.exceptions.GetFoodOptionByIdException;
import com.fsd.exceptions.UpdateFoodOptionException;
import com.fsd.feign.EventClient;
import com.fsd.repository.FoodOptionRepository;
import com.fsd.service.FoodOptionService;
 
@SpringBootTest
  class FoodOptionServiceTest {
 
    @Mock
    private FoodOptionRepository foodOptionRepository;
 
    @Mock
    private EventClient eventClient;
 
    @InjectMocks
    private FoodOptionService foodOptionService;
 
    @Test
      void testCreateFoodOption() {
        FoodOption foodOption = new FoodOption(1L, "Food", 10.0, "Description", 1L);
        when(foodOptionRepository.save(foodOption)).thenReturn(foodOption);
 
        FoodOption createdFoodOption = foodOptionService.createFoodOption(foodOption);
 
        assertEquals(foodOption, createdFoodOption);
    }
 
    @Test
      void testGetAllFoodOptions() {
        List<FoodOption> foodOptions = new ArrayList<>();
        foodOptions.add(new FoodOption(1L, "Food1", 10.0, "Description1", 1L));
        foodOptions.add(new FoodOption(2L, "Food2", 20.0, "Description2", 2L));
        when(foodOptionRepository.findAll()).thenReturn(foodOptions);
 
        List<FoodOption> retrievedFoodOptions = foodOptionService.getAllFoodOptions();
 
        assertEquals(foodOptions.size(), retrievedFoodOptions.size());
        assertEquals(foodOptions, retrievedFoodOptions);
    }
 
    // Similarly, write test cases for other methods of FoodOptionService
//    @Test
//      void testGetAllFoodOptionsByEventId() {
//        Long eventId = 1L;
//        List<FoodOption> foodOptions = new ArrayList<>();
//        foodOptions.add(new FoodOption(1L, "Food1", 10.0, "Description1", eventId));
//        when(foodOptionRepository.findByEventId(eventId)).thenReturn(foodOptions);
// 
//        List<FoodOption> retrievedFoodOptions = foodOptionService.getAllFoodOptionsByEventId(eventId);
// 
//        assertEquals(foodOptions.size(), retrievedFoodOptions.size());
//        assertEquals(foodOptions, retrievedFoodOptions);
//    }
    
    
    @Test
      void testDeleteFoodOptionException() {
        String errorMessage = "Error deleting food option";
        DeleteFoodOptionException exception = assertThrows(DeleteFoodOptionException.class, () -> {
            throw new DeleteFoodOptionException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
      void testGetAllFoodOptionsException() {
        String errorMessage = "Error getting all food options";
        GetAllFoodOptionsException exception = assertThrows(GetAllFoodOptionsException.class, () -> {
            throw new GetAllFoodOptionsException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
      void testGetFoodOptionByIdException() {
        String errorMessage = "Error getting food option by ID";
        GetFoodOptionByIdException exception = assertThrows(GetFoodOptionByIdException.class, () -> {
            throw new GetFoodOptionByIdException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
      void testGetAllFoodOptionsByEventIdException() {
        String errorMessage = "Error getting all food options by event ID";
        GetAllFoodOptionsByEventIdException exception = assertThrows(GetAllFoodOptionsByEventIdException.class, () -> {
            throw new GetAllFoodOptionsByEventIdException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
      void testUpdateFoodOptionException() {
        String errorMessage = "Error updating food option";
        UpdateFoodOptionException exception = assertThrows(UpdateFoodOptionException.class, () -> {
            throw new UpdateFoodOptionException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
    
    

    @Test
      void testCreateFoodOption_Exception() {
        FoodOption foodOption = new FoodOption(1L, "Food", 10.0, "Description", 1L);
        when(foodOptionRepository.save(foodOption)).thenThrow(new RuntimeException("Database connection error"));

        assertThrows(CreateFoodOptionException.class, () -> {
            foodOptionService.createFoodOption(foodOption);
        });
    }

    

    @Test
      void testGetFoodOptionById() {
        Long id = 1L;
        FoodOption foodOption = new FoodOption(id, "Food1", 10.0, "Description1", 1L);
        when(foodOptionRepository.findById(id)).thenReturn(Optional.of(foodOption));

        FoodOption retrievedFoodOption = foodOptionService.getFoodOptionById(id);

        assertEquals(foodOption, retrievedFoodOption);
    }

    @Test
      void testGetFoodOptionById_NotFound() {
        Long id = 1L;
        when(foodOptionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GetFoodOptionByIdException.class, () -> {
            foodOptionService.getFoodOptionById(id);
        });
    }

    @Test
      void testGetFoodOptionById_Exception() {
        Long id = 1L;
        when(foodOptionRepository.findById(id)).thenThrow(new RuntimeException("Database connection error"));

        assertThrows(GetFoodOptionByIdException.class, () -> {
            foodOptionService.getFoodOptionById(id);
        });
    }

    @Test
      void testUpdateFoodOption() {
        Long id = 1L;
        FoodOption foodOption = new FoodOption(id, "Food1", 10.0, "Description1", 1L);
        when(foodOptionRepository.existsById(id)).thenReturn(true);
        when(foodOptionRepository.save(foodOption)).thenReturn(foodOption);

        FoodOption updatedFoodOption = foodOptionService.updateFoodOption(id, foodOption);

        assertEquals(foodOption, updatedFoodOption);
    }

    @Test
      void testUpdateFoodOption_NotFound() {
        Long id = 1L;
        FoodOption foodOption = new FoodOption(id, "Food1", 10.0, "Description1", 1L);
        when(foodOptionRepository.existsById(id)).thenReturn(false);

        assertThrows(UpdateFoodOptionException.class, () -> {
            foodOptionService.updateFoodOption(id, foodOption);
        });
    }

    @Test
      void testUpdateFoodOption_Exception() {
        Long id = 1L;
        FoodOption foodOption = new FoodOption(id, "Food1", 10.0, "Description1", 1L);
        when(foodOptionRepository.existsById(id)).thenReturn(true);
        when(foodOptionRepository.save(foodOption)).thenThrow(new RuntimeException("Database connection error"));

        assertThrows(UpdateFoodOptionException.class, () -> {
            foodOptionService.updateFoodOption(id, foodOption);
        });
    }

    @Test
      void testDeleteFoodOption() {
        Long id = 1L;
        when(foodOptionRepository.existsById(id)).thenReturn(true);

        foodOptionService.deleteFoodOption(id);

        verify(foodOptionRepository, times(1)).deleteById(id);
    }

    @Test
      void testDeleteFoodOption_NotFound() {
        Long id = 1L;
        when(foodOptionRepository.existsById(id)).thenReturn(false);

        assertThrows(DeleteFoodOptionException.class, () -> {
            foodOptionService.deleteFoodOption(id);
        });
    }

    @Test
      void testDeleteFoodOption_Exception() {
        Long id = 1L;
        when(foodOptionRepository.existsById(id)).thenThrow(new RuntimeException("Database connection error"));

        assertThrows(DeleteFoodOptionException.class, () -> {
            foodOptionService.deleteFoodOption(id);
        });
    }

    @Test
      void testGetAllFoodOptionsByEventId() {
        Long eventId = 1L;
        List<FoodOption> foodOptions = new ArrayList<>();
        foodOptions.add(new FoodOption(1L, "Food1", 10.0, "Description1", eventId));
        when(eventClient.getEventById(eventId)).thenReturn(new Event());
        when(foodOptionRepository.findByEventId(eventId)).thenReturn(foodOptions);

        List<FoodOption> retrievedFoodOptions = foodOptionService.getAllFoodOptionsByEventId(eventId);

        assertEquals(foodOptions, retrievedFoodOptions);
    }

    @Test
      void testGetAllFoodOptionsByEventId_EventNotFound() {
        Long eventId = 1L;
        when(eventClient.getEventById(eventId)).thenReturn(null);

        assertThrows(GetAllFoodOptionsByEventIdException.class, () -> {
            foodOptionService.getAllFoodOptionsByEventId(eventId);
        });
    }

    @Test
      void testGetAllFoodOptionsByEventId_Exception() {
        Long eventId = 1L;
        when(eventClient.getEventById(eventId)).thenThrow(new RuntimeException("Service unavailable"));

        assertThrows(GetAllFoodOptionsByEventIdException.class, () -> {
            foodOptionService.getAllFoodOptionsByEventId(eventId);
        });
    }
    
    @Test
      void testEventConstructorAndGetters() {
        Long id = 1L;
        String name = "Event";
        String description = "Description";
        LocalDate startDate = LocalDate.of(2024, 4, 16);
        LocalDate endDate = LocalDate.of(2024, 4, 18);
        String time = "10:00 AM";
        double entryFee = 20.0;
        String lastRegistrationDate = "2024-04-15";
        boolean foodIncluded = true;
        double foodPriceAdult = 10.0;
        double foodPriceChild = 5.0;
        List<Long> foodOptionIds = new ArrayList<>();
        List<Long> registrationIds = new ArrayList<>();

        Event event = new Event(id, name, description, startDate, endDate, time, entryFee, lastRegistrationDate,
                foodIncluded, foodPriceAdult, foodPriceChild, foodOptionIds, registrationIds);

        assertEquals(id, event.getId());
        assertEquals(name, event.getName());
        assertEquals(description, event.getDescription());
        assertEquals(startDate, event.getStartDate());
        assertEquals(endDate, event.getEndDate());
        assertEquals(time, event.getTime());
        assertEquals(entryFee, event.getEntryFee());
        assertEquals(lastRegistrationDate, event.getLastRegistrationDate());
        assertEquals(foodIncluded, event.isFoodIncluded());
        assertEquals(foodPriceAdult, event.getFoodPriceAdult());
        assertEquals(foodPriceChild, event.getFoodPriceChild());
        assertEquals(foodOptionIds, event.getFoodOptionIds());
        assertEquals(registrationIds, event.getRegistrationIds());
    }

    @Test
      void testEventSetters() {
        Event event = new Event();

        Long id = 1L;
        String name = "Event";
        String description = "Description";
        LocalDate startDate = LocalDate.of(2024, 4, 16);
        LocalDate endDate = LocalDate.of(2024, 4, 18);
        String time = "10:00 AM";
        double entryFee = 20.0;
        String lastRegistrationDate = "2024-04-15";
        boolean foodIncluded = true;
        double foodPriceAdult = 10.0;
        double foodPriceChild = 5.0;
        List<Long> foodOptionIds = new ArrayList<>();
        List<Long> registrationIds = new ArrayList<>();

        event.setId(id);
        event.setName(name);
        event.setDescription(description);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setTime(time);
        event.setEntryFee(entryFee);
        event.setLastRegistrationDate(lastRegistrationDate);
        event.setFoodIncluded(foodIncluded);
        event.setFoodPriceAdult(foodPriceAdult);
        event.setFoodPriceChild(foodPriceChild);
        event.setFoodOptionIds(foodOptionIds);
        event.setRegistrationIds(registrationIds);

        assertEquals(id, event.getId());
        assertEquals(name, event.getName());
        assertEquals(description, event.getDescription());
        assertEquals(startDate, event.getStartDate());
        assertEquals(endDate, event.getEndDate());
        assertEquals(time, event.getTime());
        assertEquals(entryFee, event.getEntryFee());
        assertEquals(lastRegistrationDate, event.getLastRegistrationDate());
        assertEquals(foodIncluded, event.isFoodIncluded());
        assertEquals(foodPriceAdult, event.getFoodPriceAdult());
        assertEquals(foodPriceChild, event.getFoodPriceChild());
        assertEquals(foodOptionIds, event.getFoodOptionIds());
        assertEquals(registrationIds, event.getRegistrationIds());
    }
    
    @Test
      void testFoodOptionConstructorAndGetters() {
        Long id = 1L;
        String name = "Food";
        double price = 10.0;
        String description = "Description";
        Long eventId = 1L;

        FoodOption foodOption = new FoodOption(id, name, price, description, eventId);

        assertEquals(id, foodOption.getId());
        assertEquals(name, foodOption.getName());
        assertEquals(price, foodOption.getPrice());
        assertEquals(description, foodOption.getDescription());
        assertEquals(eventId, foodOption.getEventId());
    }

    @Test
      void testFoodOptionSetters() {
        FoodOption foodOption = new FoodOption();

        Long id = 1L;
        String name = "Food";
        double price = 10.0;
        String description = "Description";
        Long eventId = 1L;

        foodOption.setId(id);
        foodOption.setName(name);
        foodOption.setPrice(price);
        foodOption.setDescription(description);
        foodOption.setEventId(eventId);

        assertEquals(id, foodOption.getId());
        assertEquals(name, foodOption.getName());
        assertEquals(price, foodOption.getPrice());
        assertEquals(description, foodOption.getDescription());
        assertEquals(eventId, foodOption.getEventId());
    }
    
    

}
