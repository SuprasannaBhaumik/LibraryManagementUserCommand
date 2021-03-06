package com.library.user.client;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.user.config.EventStoreClient;
import com.library.user.event.DomainEvent;
import com.library.user.event.DomainEvents;
import com.library.user.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userClient")
public class EventStoreUserClient implements UserClient {

	@Autowired
	EventStoreClient eventStoreClient;

    /*public EventStoreUserClient(final EventStoreClient eventStoreClient) {
        this.eventStoreClient = eventStoreClient;
    }*/
    
	@Override
	public void save(User user) {
		log.info("User save : enter");
        List<DomainEvent> newChanges = user.changes();
        newChanges.forEach(domainEvent -> {
            log.debug("save : domainEvent=" + domainEvent);
            ResponseEntity accepted = this.eventStoreClient.addNewDomainEvent(domainEvent);
            if(!accepted.getStatusCode().equals(HttpStatus.ACCEPTED)) {
                throw new IllegalStateException("could not add DomainEvent to the Event Store");
            }
        });
        user.flushChanges();
        log.info("User save : exit");
	}

	@Override
	public User find(UUID userId) {
		log.debug("find : enter");

        DomainEvents domainEvents = this.eventStoreClient.getDomainEventsForUser(userId);
        if( domainEvents.getDomainEvents().isEmpty() ) {
            log.warn("find : exit, target[" + userId.toString() + "] not found");
            throw new IllegalArgumentException( "User[" + userId.toString() + "] not found");
        }

        User user = User.createFrom(userId, domainEvents.getDomainEvents());
        user.flushChanges();

        log.debug("find : exit");
        return user;
	}

}
