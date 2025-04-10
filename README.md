## Neoflex internship assessment task

REST API service with JDK 11, Spring Boot 2.7, Web, Swagger/OpenAPI 3.0

Requirements for a voting service:

* Single API endpoint GET `/calculate`
* Endpoint accepts a person's average salary and vacation dates 
* Returns a payment excluding non-business days
* The list of non-business days is taken from a third-party service

Run: `mvn spring-boot:run` in the root directory

[REST API documentation](http://localhost:8080/)