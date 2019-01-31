package com.library.user.config;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.library.user.event.DomainEvent;
import com.library.user.event.DomainEvents;

@Component
public class EventStoreClientImpl implements EventStoreClient {

	@Override
	public ResponseEntity addNewDomainEvent(DomainEvent event) {
		return null;
	}

	@Override
	public DomainEvents getDomainEventsForUser(UUID userId) {
		return null;
	}

}
