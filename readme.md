Taxi Billing System Java Spring Boot Application

Project Name	                  Taxi Billing System

Abstract	                      This Java Spring Boot application serves a Taxi Billing System, allowing users to perform various operations related to booking a Taxi

Languages Used	                Java

IDE	                            Intellij Idea Professionl

Database	                      PostgresQL(TaxiBillingSystem)

Security	                      Jwt Authentication

1.Introduction about my project

•	The application follows a layered architecture including controller, service, repository, model, contract, validation and exception layers to ensure separation of concerns and maintainability.
•	Testing has conducted with the help of junit and Mockito

2. Features
   
User Controller consists of:
•	@Post Mapping-Register User: Endpoint to register a user.
•	@PostMapping-Logindetails-Jwt Token for logging in
•	@PostMapping-AccountBalanceDetails- for the account Balance of a 

Taxi Controller consists of:
•	@Post Mapping-Add A Taxi: Endpoint to add a new taxi.
•	@PostMapping-Book a Taxi- for booking a taxi
•	@Get Mapping-Booking details by Id- for getting the details of a user
•	@DeleteMapping-CancelBooking-deleting the booked user
•	@PostMapping-FareCAclulationDetails-calculating the fare for travelled distance by a user

3. Technology Stack
•	Java
•	Spring Boot
•	Spring Data JPA

4. Entity Class
 • Entity classes are User, Taxi and Booking.

5.Layers
•	Controller Layer
-The controller layer contains RESTful endpoints responsible for handling incoming HTTP requests and delegating the processing to the service layer.

•	Service Layer
-The service layer encapsulates the business logic of the application. It performs operations such as validation, exceptions, transformation, and interaction with the repository layer.
•	Repository Layer
-The repository layer interacts with the underlying database, providing methods for storing, retrieving, updating, and deleting  taxi billing and booking records.
•	Model Layer
-	The model layer defines the data structures used within the application, including the User, Taxi and Booking  entity class.
•	Contract Layer
-The contract layer defines the API contracts between different layers of the application, ensuring proper communication and separation of concerns.

6.Security
 • Regular security audits and vulnerability assessments are conducted to identify and mitigate potential security risks.
 • For Json Web Token, using the Jwt Filter and a security filter chain configuration is added.
 •	The project follows best practices for secure coding, and dependencies are regularly updated to patch known vulnerabilities.
  
6.Tests
•	My Java Spring Boot project utilizes the following testing frameworks:
      JUnit 5: The standard framework for writing unit tests in Java.
      Spring Boot Test@SpringBootTest:   Provides support for testing Spring Boot applications, including features like @AutoConfigureMockMvc @WithMockUser etc.
      Mockito: A popular mocking framework for creating and configuring mock objects.
      Assertions: A fluent assertion library for writing easy-to-read, descriptive test assertions.
      

