# LibraryManagementUserCommand
![CQRS Architecture](https://raw.githubusercontent.com/SuprasannaBhaumik/LibraryManagementUserCommand/master/src/main/resources/static/CQRSArchitecture.jpeg)

We are following the DDD(Domain Design Driven) approach where all the domains related to the Library Management system are separated, and have their own independent persistant store.

For LMS(Library Management System) these are namely : Users, Subscriptions, Books, Reminders, etc.

The individual domain entity are still separated by the Command Query Responsibilty Segregation(CQRS).

  A.Command part takes care of POST verbs in the application, and can be scaled independenly.
  B.Query part takes care of the GET part of the Domain, and can be scaled independently.


This microservice application deals with the Command side of Users in the LMS.
Command side will constitute the post verbs, namely POST, PUT, DELETE.

For the type of action we create events, and persist them.

Steps related to overall architecture: 

1. API calls made, are intercepted by the discovery server(Netflix OSS - Eureka) and directed to specific microservices                application(Command/Query).
2. As soon as request is received, we create a DomainEvent related to the action. 
   In this case the DomainEvents are likely : 
      UserInitialized, 
      UserRenamed, 
      UserDeleted, and many more as can be discovered. 
   Once the event is created we use Feign(Netflix OSS) to hit our Event Store Microservice.
3. Event store microservice then validates the event and stores it in the persistent store.
4. Event store then publishes this message to a Kafka stream for it to be able to be consumed by the other 
   dependent microservices subscribed to it.
5. Query microservice is subscribed to the same topic and consumes and updates it cache with the latest data.
6. Command microservice also listens to the same message and updates the persistent store.
