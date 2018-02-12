#Welcome to Marketplace
**Marketplace** exposes a set of RESTful services to register a project (Seller) for bidding by self employed (Buyer).
Interested buyers can view the open projects and submit the bid. System keeps track of the lowest bid and buyer can always
keeps track on the current lowest bid. On reaching the deadline for bidding, system closes the project and grants the
praject for the user with the lowest bid.

**Time Spent:**
 Approx 12 Hours

**What is achieved:**
* TDD Approach with spring boot
* User Module
    - Register User with emailId, Phone, first name, last name
* Project Module
    - Register a project with name, description, end date
    - Get to know the winning bid once the end date elapsed
    - List open projects
    - View project details to check what is the winning bid or the current lowest bid
* Bidding module
    - Bid for any of the open projects, one bid per user
    - Check the bid to know the status once the project is closed for bidding
* Integration tests to validate the RESTful services

**Identified Improvements**
* Application of Spring security with role management
* Extend with Oauth2
* Caching

**High level technology stack and frameworks used**
* Spring boot
* In-memory DB (h2)
* JPA
* Lombok
* FasterXML/Jackson
* Mockito with Junit 4

**IDE usage**
* This code is developed using Intellij Idea CE, you can either use eclipse or idea.
* Import as either gradle project or maven project
* Make sure to enable Annotation processing, otherwise IDE will complain about compilation (Thanks to Lombok)
* https://stackoverflow.com/questions/25501926/annotation-processor-compilation-in-intellij-idea
* https://stackoverflow.com/questions/43404891/how-to-configure-java-annotation-processors-in-eclipse

**Running from source folder with gradle**
gradle build && java -jar build/libs/marketoplace.jar

**Running from source folder with maven**
mvn package spring-boot:repackage && java -jar target/marketplace-1.0-SNAPSHOT.jara
--OR-
mvn spring-boot:run

**Verification**
Once the application is up, open the Marketplace.postman_collection in postman and follow the below steps.
* Register User
* Register Project (5 or 10 minutes to go as End date in UTC, System is cofigured to use UTC as default time zone)
* Get All Open Projects
* Register User (With diff email and phone) - Code does not check for the user, so you can use the same user (Yes its a defect)
* Submit Bid
* Register One more User
* Submit new bid with new user with lower amount
* Get Project By Id, notice that the lowest bid is assigned to the project
* Once the project bid end time has elapsed, Get Project By Id, response will include the project status change and the
 lowest bid status change