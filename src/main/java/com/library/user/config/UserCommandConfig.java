package com.library.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.library.user.client.UserClient;
import com.library.user.service.UserService;

@Configuration
public class UserCommandConfig {

	@Bean
    public UserService userService(final UserClient userClient) {
        return new UserService(userClient);
    }

}
