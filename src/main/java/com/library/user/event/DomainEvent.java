package com.library.user.event;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "eventType",
        defaultImpl = DomainEventIgnored.class
)
@JsonSubTypes({
        @JsonSubTypes.Type( value = UserInitialized.class, name = "UserInitialized" ),
        @JsonSubTypes.Type( value = UserRenamed.class, name = "UserRenamed" ),
        @JsonSubTypes.Type( value = UserDeleted.class, name = "UserDeleted" )
})
public abstract class DomainEvent {
	
	private final UUID userId;
	private final Instant when;

	DomainEvent( final UUID userId, final Instant when ) {
        this.userId = userId;
        this.when = when;
    }

	@JsonProperty( "occurredOn" )
    public Instant occurredOn() {
        return when;
    }

    @JsonProperty( "eventType" )
    public abstract String eventType();

}
