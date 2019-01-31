package com.library.user.service;

import java.util.UUID;

import com.library.user.client.UserClient;
import com.library.user.dto.UserDTO;
import com.library.user.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {
	
	private final UserClient client;

    public UserService( final UserClient client ) {
        this.client = client;
    }

    public UUID createUser(UserDTO userObj) {
        log.debug( "createUser : enter" );
        User user = new User(UUID.randomUUID());
        user.setAge(userObj.getAge());
        user.setUserName(userObj.getName());
        this.client.save(user);
        return user.getId();
    }

    public void renameUser( final UUID userId, final String name ) {
        log.debug( "renameUser : enter" );
        User user = this.client.find(userId);
        if(user != null && !user.getUserName().equalsIgnoreCase(name)) {
        	user.renameUser(name);
        	this.client.save(user);
        }
    }
}
