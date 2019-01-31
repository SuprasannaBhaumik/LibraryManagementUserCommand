package com.library.user.remote;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.library.user.client.EventStoreUserClient;
import com.library.user.client.UserClient;

@Profile("event-store")
@Configuration
public class EventStoreClientConfig {

    @Bean
    @Primary
    public UserClient userClient(
			@Qualifier("com.library.user.remote.RestConfig$EventStoreClient") final RestConfig.EventStoreClient eventStoreClient
	){
        return new EventStoreUserClient(eventStoreClient);
    }

}
