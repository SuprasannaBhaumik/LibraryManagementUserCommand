package com.library.user.remote;

import java.util.UUID;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.DomainEvents;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.library.user.event.DomainEvent;

@Profile("event-store")
@Configuration
@EnableFeignClients
public class RestConfig {

    @FeignClient(value = "esd-event-store")
    public interface EventStoreClient {

        @PostMapping( path = "/createUser" )
        ResponseEntity addNewDomainEvent( @RequestBody DomainEvent event );

        @GetMapping( path = "/{userId}" )
        DomainEvents getDomainEventsForUser( @PathVariable( "userId" ) UUID userId );

    }

}