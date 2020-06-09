# User Management Service
   this is the user microservice which is used to handle all the user details and manage them 
       We are saving all data as latest in ums bucket .
	User can buy or sell the stock on the basic of latest feeds which comes from Stock data management .
	User can see the portfolio .
	In portfolio user can see the purchases or sell details along with that also see the history of stocks buy or sell 
    Admin also see the details of all the users .	
### Documentation
[Technical documentation]  
### Prerequisites

  * Software: User-management-service
  * JDK 1.8
  * Spring Boot
  * Spring Reactive
  * For persistence, use Couchbase Server.
  * Embedded Server: Netty
  * For logging, use slf4j.
  * Gradle 4.10 is used to build project.
  * For building project, use Gradle 4.10.
  * For Static Code Analysis, use Sonar to generate reports. 
  * For Code Coverage Analysis, use Jacoco.

### Installation and Build

  * Move to the base of microservice folder i.e. ${PROJECT_CHECKOUT_FOLDER}\User-management-service.

  * Add proot id,password in gradle.properties.
  
  * Import  the project to the IDE.  
  
##### Database 

Add/edit database related information like bucketName,cluster username,cluster password at ${PROJECT_CHECKOUT_FOLDER}\User-management-service.
  src\main\resources\application.properties .To enable auto indexing

##### Configs

###### Application configuration
  * Add/edit application level properties at ${PROJECT_CHECKOUT_FOLDER}\User-management-service\src\main\resource\application.properties.Edit config.home path in application.properties to the one mentioned in dockerfile.
  
### Running the MicroService
   * Move to the ${PROJECT_CHECKOUT_FOLDER}\User-management-service\build\libs
   * Execute command: java -jar lap-0.0.1-SNAPSHOT-exec.jar

### Coding style tests 

   * Code is fully reactive,no blocking code has been implemented.
  
### Deployment

### Versioning

  API versioning is followed.
