### Features
 - Apis for webhook verification
   - Api to process the webhook asynchronously
   - Api to verify webhook with tweak to fail the processing at controller level or processing payload level
   - Api to intentionally delay the webhook processing
 - Cucumber tests
 - K6 performance verification test

### Usage
 - Compile
   - cd simply-api
   - mvn clean install

 - Run api server
   - cd simply-api/Service
   - mvn spring-boot:run
   
 - Run integration test
   - First ensure api server is running
     - cd simply-api/IntegrationTest
     - mvn test -Pintegration-test
   
### APIs
 - Api to verify webhook calls
   - Non Secured webhook
     ```
     webhook call 
     Url : POST {{host}}/api/webhook
     Payload : { 
                     //json object
               }
     webhook get payload back, for id use id property specified in payload during post request
     Url : GET {{host}}/api/webhook/<id>
     ```
   - Secured webhook
     ```
     webhook call 
     Url : POST {{host}}/api/secured/webhook
     Header : Authorization Basic (user:password)
     Payload : { 
                    //json object
              }
     webhook get payload back, for id use id property specified in payload during post request
     Url : GET {{host}}/api/secured/webhook/<id>
     Header : Authorization Basic (user:password)
     ```
 - There are many other apis: 
   - To measure the processing time
   - To delay the processing
   - To fail processing etc
   - Refer postman collection : [Postman Collection](postman_collection.json)

### Cucumber Integration test
 - Integration test result stored in html format, file name is **cucumber_report.html** in root folder
 - Properties for the api is stored in application.yml folder of resources folder under test dir
   - url - webhook url
   - username - username in case webhook url is authenticated
   - password - password for secured webhook
 - To export the report on live site enable in **cucumber.properties** file

### Logs
 - To enable debug level log check **logback.xml** file

### Reference
 - https://start.spring.io/
 - https://k6.io/