# LibraryManagementUserCommand
![CQRS Architecture](https://raw.githubusercontent.com/SuprasannaBhaumik/LibraryManagementUserCommand/master/src/main/resources/static/CQRSArchitecture.jpeg)

We are following the DDD approach where all the domains related to the Library Management system are separated, and they have their own database store.

For LBS(Library Management System) these are namely : Users, Subscriptions, Books, Reminders, etc.

The individual domain are still separated by the Command Query Responsibilty Segregation.

Command part takes care of POST verbs and can be scaled independenly.
Query part takes care of the GET part of the Domain.


This microservice application deals with the Command side of Users in the Library Management SystemCommand side will constitute the post verbs, namely POST, PUT, DELETE.

Steps : 

1.API calls made to the discovery server are directed to our Command/Query application. 
