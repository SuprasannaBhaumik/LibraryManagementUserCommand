package com.library.user.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.library.user.dto.UserDTO;
import com.library.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping( "/users" )
@Slf4j
public class UserController {

	private final UserService service;

    public UserController( final UserService service ) {
        this.service = service;
    }
	
	
	@PostMapping("/createUser")
	public ResponseEntity createUser(@RequestBody UserDTO user, final UriComponentsBuilder uriComponentsBuilder) {
		log.info("creating the user");
		UUID userId = this.service.createUser(user);
		log.info("user is created with id " + userId.toString());
		log.info("hitting the users/id to refresh the view");
		return ResponseEntity
				.created(
						uriComponentsBuilder
						.path("/users/{userId}")
						.buildAndExpand(userId)
						.toUri()
				)
				.build();
	}
	
	@PatchMapping( "/{userId}" )
    public ResponseEntity renameUser( @PathVariable( "userId" ) UUID userId, @RequestParam( "name" ) String name, final UriComponentsBuilder uriComponentsBuilder ) {
        log.debug( "renameUser : enter" );
        this.service.renameUser( userId, name );
        return ResponseEntity
                .accepted()
                .build();
    }
	
}
