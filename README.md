# Gamesys Test

Registration with Exclusion Services 

Build a web application that offers a remote service interface (endpoint) to register a user. A user can only register if not part of an exclusion list. The goal is to demonstrate that the web application runs, the tests are passing, and real HTTP requests can be made. In summary: 
* Set up a way to run a web application server 
* Implement a REST endpoint for the register operation based on the given interface specifications 
* Implement a simple way for the web application to remember already registered users (no real persistence needed, in-memory is sufficient) 
* Implement a way to use the given 'ExclusionService' interface to simulate the business logic of validating the user against an exclusion list. 

It is not expected that anything more is done than to implement the minimal requirements in a clean and reasonable way. Any detail that is missing in the requirements can be implemented at discretion. If the candidate feels the wish to use more technology than needed, they are happily invited to do so as long as they can successfully run the system on MacOS. 
The project will have to be demonstrated in the interview and will be open for discussion. This will also be a good chance to talk about anything that was unclear, couldn't be implemented or leaves room for improvement. 
Technology 
The choice of technologies and frameworks is up to the candidate as long as they are Java-based and freely available: 
* Programming Language Java (version 8 and above)
* Webserver Any e.g.: Spring Boot, Jetty plugin in Maven, Dropwizard, Tomcat..
* Remote Service Protocol REST 
• Testing/ Stubbing Any (e.g. Junit, Mockito, self-made stubs, rest-assured)
* Build Maven, Gradle.. 

Architecture 

Registration Service (Web Application) 

This will be the web application the candidate needs to set up and implement. It offers a REST endpoint to register a user. The definition of the endpoint is: 
* parameters: 
    * o “username” (alphanumerical, no spaces) o “password” (at least four characters, at least one lower case character, at least one upper case character, at least one number) o “dateOfBirth” (ISO 8601) o “ssn” (Social Security Number - does not have to be validated, for better understanding see: http://en.wikipedia.org/wiki/Social_Security_number) 
    * checks: 
        * parameters are valid according to specification o user hasn't been already registered o user is not in the exclusion list 
        * returns an appropriate response that makes clear if a member could be registered or any error occurred 

Exclusion Service 

The exclusion service is given as an interface and needs to be implemented in order to exclude the following users: 
* username password dateOfBirth ssn 
* adaLovelace Analytical3ngineRulz 10th December 1815 85385075 
* alanTuring eniGmA123 23rd of June 1912 123456789 
* konradZuse zeD1 22nd of June 1910 987654321 

````
/** 
* Service to offer validation of a user against an exclusion list. 
* @author gamesys 
*/ 

public interface ExclusionService { 
    /** 
    * Validates a user against an exclusion list using their date of 
    * birth and social security number as identifier. 
    * @param dateOfBirth the user's date of birth in ISO 8601 format 
    * @param ssn the user's social security number (United States) 
    * @return true if the user is not excluded 
    */ 
    boolean validate(String dateOfBirth, String ssn); 

}
````
# My Notes

* There is no database, just two synchronized singleton collections. 
    + usersExcludedCollection
    + usersCollection
    + See DataProviderConfiguration.java

* To validate the username, password and date of birth during the request, 3 annotations were created using the hibernate-validator project.
    + @DateISO8601
    + @Username
    + @Password 

* The service **UserAPIService** was created as bridge between the controller and the business service **RegisterUserService**.
In the future can be used to link more  business services.

## Instructions

**How to start netty server?**

```
docker-compose up
```

or

```
gradlew bootRun

(needs JAVA_HOME defined)
```

or

```
sh start.sh

(needs JAVA_HOME defined)
```
**How to perform the tests?**

```
gradlew test

(needs JAVA_HOME defined)
```

**API documentation**

```
http://localhost:8080/api/docs-ui.html
```

**Curl Command**

```
curl -X POST "http://localhost:8080/api/user" \
-H "accept: */*" -H "Content-Type: application/json" \
-d "{\"username\":\"silva\",\"ssn\":\"12345678\",\"password\":\"Sha1\",\"dateOfBirth\":\"1984-12-11\"}"
```

## Improvements for the future 

+ Use a real database (needs to have a reactive driver) 
+ Security layer
+ More rest operations
+ Improve the documentation
+ Handle the correct response code when an error occurs

