package com.library.user.event;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class DomainEvents {

    private UUID userId;
    private List<DomainEvent> domainEvents = new ArrayList<>();

}
