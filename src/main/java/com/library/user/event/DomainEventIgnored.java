package com.library.user.event;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode( callSuper = true )
@ToString( callSuper = true )
@JsonPropertyOrder({ "eventType", "userId", "occurredOn" })
public class DomainEventIgnored extends DomainEvent {

	@JsonCreator
	public DomainEventIgnored(
			 @JsonProperty( "userId" ) UUID userId,
			 @JsonProperty( "occurredOn" ) Instant when) {
		super(userId, when);
	}

	@Override
	@JsonIgnore
	public String eventType() {
		return this.getClass().getSimpleName();
	}
}
