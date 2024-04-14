package com.fsd;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fsd.entity.FoodOption;
import com.fsd.feign.EventClient;
import com.fsd.repository.FoodOptionRepository;
import com.fsd.service.FoodOptionService;
 
@SpringBootTest
public class FoodOptionServiceTest {
 
    @Mock
    private FoodOptionRepository foodOptionRepository;
 
    @Mock
    private EventClient eventClient;
 
    @InjectMocks
    private FoodOptionService foodOptionService;
 
    @Test
    public void testCreateFoodOption() {
        FoodOption foodOption = new FoodOption(1L, "Food", 10.0, "Description", 1L);
        when(foodOptionRepository.save(foodOption)).thenReturn(foodOption);
 
        FoodOption createdFoodOption = foodOptionService.createFoodOption(foodOption);
 
        assertEquals(foodOption, createdFoodOption);
    }
 
    @Test
    public void testGetAllFoodOptions() {
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
//    public void testGetAllFoodOptionsByEventId() {
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
}