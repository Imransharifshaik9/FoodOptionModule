package com.fsd.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fsd.entity.Event;

import feign.Headers;

@FeignClient(name = "EVENTSERVICE", url = "http://localhost:7070")
public interface EventClient {
	
	

    @GetMapping("/events/{id}")
    Event getEventById(@PathVariable("id") Long id);

		

}
