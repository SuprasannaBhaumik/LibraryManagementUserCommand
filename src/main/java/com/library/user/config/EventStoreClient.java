package com.library.user.config;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.library.user.event.DomainEvent;
import com.library.user.event.DomainEvents;

@FeignClient(value="lib-event-store", fallback=EventStoreClientImpl.class)
public interface EventStoreClient {
	
	@PostMapping(path="/createUser")
    ResponseEntity addNewDomainEvent(@RequestBody DomainEvent event);

    @GetMapping(path="/{userId}")
    DomainEvents getDomainEventsForUser(@PathVariable("userId") UUID userId);

}
