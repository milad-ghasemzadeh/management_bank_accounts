# Management Bank Accounts | Spring boot

## Table of Contents

- [Prerequisites](#prerequisites)
- [Run And Test](#Run And Test)
- [Benefits](#Benefits)
- [Used By](#Used By)
- [Address to APIS](#Address to APIS)
- [Access to DataBase ](#Access to DataBase )
- [Access to Swagger](#Access to Swagger)
- [Author ](#Author)




## Prerequisites

Java 8: Make sure you have Java 8 or a compatible version installed on your system.

Maven: Ensure that Maven is installed to manage the project's dependencies and build process.


- Second step install IDE
  - Intelij
  
## Run And Test
- In another step you can Run or Debug the ManagementBankAccountsApplication
- In another step you can test all sections of program in test Folder. there are some classes to test


## Benefits

- Clean code
- Java 8
- TDD
- Exception Handling
- Used an Idea for easily  converting entities  and DTOs


## Used By

This project is created for the Management Bank Accounts


## Address to APIS

Card
- Post : http://localhost:8080/api/card
- Get : http://localhost:8080/api/card

Person
- Post (we have Basic Auth in here) : http://localhost:8080/api/person
- Get : http://localhost:8080/api/person

Sender
- Get : http://localhost:8080/api/sender


## Access to DataBase 
you can go to Database console H2 by following like and use these credentials :

- Link : http://localhost:8080/h2-console/

- UserName : who_is_milad

- Password : honest_creative_hardworking_brave
* the value of JDBC Driver is : jdbc:h2:mem:testdb


## Access to Swagger

- Link http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#


## Author
Milad Ghasemzadeh



