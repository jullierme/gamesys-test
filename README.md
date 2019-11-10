# Gamesys Test

## Notes

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

