package com.library.user.client;

import java.util.UUID;

import com.library.user.model.User;

public interface UserClient {

	void save(User user);

	User find(UUID userId);

}
