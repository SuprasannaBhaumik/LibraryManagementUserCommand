package com.library.user.client;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.library.user.event.DomainEvent;
import com.library.user.model.User;
import com.library.user.remote.RestConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventStoreUserClient implements UserClient {

	private final RestConfig.EventStoreClient eventStoreClient;

    public EventStoreUserClient( final RestConfig.EventStoreClient eventStoreClient ) {
        this.eventStoreClient = eventStoreClient;
    }
    
	@Override
	public void save(User user) {
		log.debug( "User save : enter" );
        List<DomainEvent> newChanges = user.changes();
        newChanges.forEach( domainEvent -> {
            log.debug( "save : domainEvent=" + domainEvent );
            ResponseEntity accepted = this.eventStoreClient.addNewDomainEvent(domainEvent);
            if( !accepted.getStatusCode().equals( HttpStatus.ACCEPTED ) ) {
                throw new IllegalStateException( "could not add DomainEvent to the Event Store" );
            }
        });
        user.flushChanges();
        log.debug( "User save : exit" );
	}

	@Override
	public User find(UUID userId) {
		return null;
	}

}
