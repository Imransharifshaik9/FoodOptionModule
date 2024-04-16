package com.fsd.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fsd.controller.FoodOptionController;
import com.fsd.entity.Event;
import com.fsd.entity.FoodOption;
import com.fsd.exceptions.CreateFoodOptionException;
import com.fsd.feign.EventClient;
import com.fsd.service.FoodOptionService;

@SpringBootTest
public class FoodOptionControllerTest {

    @Mock
    private FoodOptionService foodOptionService;

    @Mock
    private EventClient eventClient;

    @InjectMocks
    private FoodOptionController foodOptionController;

    @Test
    public void testCreateFoodOption() throws Exception {
        FoodOption foodOption = new FoodOption(1L, "Food", 10.0, "Description", 1L);
        when(foodOptionService.createFoodOption(foodOption)).thenReturn(foodOption);

        ResponseEntity<FoodOption> responseEntity = foodOptionController.createFoodOption(foodOption);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(foodOption, responseEntity.getBody());
    }

    @Test
    public void testGetAllFoodOptions() throws Exception {
        List<FoodOption> foodOptions = new ArrayList<>();
        foodOptions.add(new FoodOption(1L, "Food1", 10.0, "Description1", 1L));
        foodOptions.add(new FoodOption(2L, "Food2", 20.0, "Description2", 2L));
        when(foodOptionService.getAllFoodOptions()).thenReturn(foodOptions);

        ResponseEntity<List<FoodOption>> responseEntity = foodOptionController.getAllFoodOptions();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(foodOptions, responseEntity.getBody());
    }

    @Test
    public void testGetFoodOptionById() throws Exception {
        Long id = 1L;
        FoodOption foodOption = new FoodOption(id, "Food1", 10.0, "Description1", 1L);
        when(foodOptionService.getFoodOptionById(id)).thenReturn(foodOption);

        ResponseEntity<FoodOption> responseEntity = foodOptionController.getFoodOptionById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(foodOption, responseEntity.getBody());
    }

    @Test
    public void testUpdateFoodOption() throws Exception {
        Long id = 1L;
        FoodOption foodOption = new FoodOption(id, "Food1", 10.0, "Description1", 1L);
        when(foodOptionService.updateFoodOption(id, foodOption)).thenReturn(foodOption);

        ResponseEntity<FoodOption> responseEntity = foodOptionController.updateFoodOption(id, foodOption);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(foodOption, responseEntity.getBody());
    }

    @Test
    public void testDeleteFoodOption() throws Exception {
        Long id = 1L;
        ResponseEntity<Void> responseEntity = foodOptionController.deleteFoodOption(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(foodOptionService, times(1)).deleteFoodOption(id);
    }

    @Test
    public void testGetAllFoodOptionsByEventId() throws Exception {
        Long eventId = 1L;
        List<FoodOption> foodOptions = new ArrayList<>();
        foodOptions.add(new FoodOption(1L, "Food1", 10.0, "Description1", eventId));
        when(foodOptionService.getAllFoodOptionsByEventId(eventId)).thenReturn(foodOptions);

        List<FoodOption> retrievedFoodOptions = foodOptionController.getAllFoodOptionsByEventId(eventId);

        assertEquals(foodOptions.size(), retrievedFoodOptions.size());
        assertEquals(foodOptions, retrievedFoodOptions);
    }

    @Test
    public void testGetAllEvents() throws Exception {
        Long eventId = 1L;
        Event event = new Event();
        when(eventClient.getEventById(eventId)).thenReturn(event);

        Event retrievedEvent = foodOptionController.getAllEvents(eventId);

        assertEquals(event, retrievedEvent);
    }
    
    
    @Test
    public void testCreateFoodOptionException() {
        String errorMessage = "Error creating food option";
        CreateFoodOptionException exception = assertThrows(CreateFoodOptionException.class, () -> {
            throw new CreateFoodOptionException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
    
}
