package com.library.user.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.esotericsoftware.minlog.Log;
import com.library.user.event.DomainEvent;
import com.library.user.event.UserDeleted;
import com.library.user.event.UserInitialized;
import com.library.user.event.UserRenamed;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Data
@Entity
@Table(name="LB_USERS")
@Slf4j
public class User {
	
	@Id
	@Column(name="ID")
	private UUID id;
	
	@Column(name="NAME")
	private String userName;
	
	@Column(name="AGE")
	private int age;
	
	@Column(name="SUBSCRIBTION_ID")
	private long subscribtionId;
	
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	private List<DomainEvent> changes = new ArrayList<>();
	
	public User() {}
	
	public User( final UUID userId) {
		userInitialized(new UserInitialized(userId, Instant.now()));
	}

	private User userInitialized(final UserInitialized event) {
		Log.info("user is initialized");
		flushChanges();
		this.id = event.getUserId();
		this.changes.add(event);
		return this;
	}
	
	public void renameUser(final String name) {
		Log.info("user is renamed to : "+ name);
        userRenamed(new UserRenamed( name, this.id, Instant.now()));
    }

    private User userRenamed( final UserRenamed event ) {
        this.userName = event.getName();
        this.changes.add( event );
        return this;
    }

    public void deleteUser(final String userId) {
    	Log.info("deleting the user with id: "+ userId);
    	userDeleted( new UserDeleted(UUID.fromString(userId), Instant.now()));
    }

	private void userDeleted(UserDeleted event) {
		this.changes.add(event);
	}

	private void flushChanges() {
		this.changes.clear();
	}
	
}
