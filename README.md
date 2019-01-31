# LibraryManagementUserCommand
![CQRS Architecture](https://raw.githubusercontent.com/SuprasannaBhaumik/LibraryManagementUserCommand/master/src/main/resources/static/CQRSArchitecture.jpeg)

We are following the DDD approach where all the domains related to the Library Management system are separated, and they have their own database store.

For LMS(Library Management System) these are namely : Users, Subscriptions, Books, Reminders, etc.

The individual domain are still separated by the Command Query Responsibilty Segregation.

  A.Command part takes care of POST verbs and can be scaled independenly.
  B.Query part takes care of the GET part of the Domain.


This microservice application deals with the Command side of Users in the LMS.
Command side will constitute the post verbs, namely POST, PUT, DELETE.

Steps related to overall architecture: 

1. API calls made to the discovery server(Eureka) are directed to our Command/Query application.
2. As soon as request is received, we create a DomainEvent related to the action. In this case the DomainEvents are likely
   UserInitialized, UserRenamed, UserDeleted. Once the event is created we use Feign to hit our Event Store Microservice.
3. Event store microservice validates the event and then stores it in the persistent store.
4. Alternatively after the action of storing the event store happens, the same event is published to the Kafka to be able to be        consumed by the other microservices that are listening/subscribed to it.
