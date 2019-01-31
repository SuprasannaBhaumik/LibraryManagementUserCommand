package com.library.user.model;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.collection.Stream.ofAll;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;*/

import com.google.common.collect.ImmutableList;
import com.library.user.event.DomainEvent;
import com.library.user.event.UserDeleted;
import com.library.user.event.UserInitialized;
import com.library.user.event.UserRenamed;

import io.vavr.API;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Data
/*@Entity
@Table(name="LIB_USERS")*/
@Slf4j
public class User {
	
	private UUID id;
	
	private String userName;
	
	private int age;
	
	private long subscribtionId;
	
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	private List<DomainEvent> changes = new ArrayList<>();
	
	public User() {}
	
	public User( final UUID userId) {
		userInitialized(new UserInitialized(userId, Instant.now()));
	}

	private User userInitialized(final UserInitialized event) {
		log.info("user is initialized");
		flushChanges();
		this.id = event.getUserId();
		this.changes.add(event);
		return this;
	}
	
	public void renameUser(final String name) {
		log.info("user is renamed to : "+ name);
        userRenamed(new UserRenamed( name, this.id, Instant.now()));
    }

    private User userRenamed( final UserRenamed event ) {
        this.userName = event.getName();
        this.changes.add( event );
        return this;
    }

    public void deleteUser(final String userId) {
    	log.info("deleting the user with id: "+ userId);
    	userDeleted( new UserDeleted(UUID.fromString(userId), Instant.now()));
    }

	private User userDeleted(UserDeleted event) {
		this.changes.add(event);
		return this;
	}

	public void flushChanges() {
		this.changes.clear();
	}

	//event handling methods
	public static User createFrom(final UUID userid, final Collection<DomainEvent> domainEvents) {
		return ofAll(domainEvents)
				.foldLeft(new User(userid), User::handleEvent);
    }

	public User handleEvent(final DomainEvent domainEvent) {
        return API.Match(domainEvent).of(
                Case($(instanceOf(UserInitialized.class)), this::userInitialized),
                Case($(instanceOf(UserRenamed.class)), this::userRenamed),
                Case($(instanceOf(UserDeleted.class ) ), this::userDeleted),
                Case($(), this)
        );
    }

	public List<DomainEvent> changes() {
		 return ImmutableList.copyOf( this.changes );
	}

}

/*@Id
@Column(name="ID")
@Column(name="NAME")
@Column(name="AGE")
@Column(name="SUBSCRIBTION_ID")*/