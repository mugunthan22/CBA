# CBA - Automation framework!

I have created this automation framework for supporting both UI and API testing.

## Automation Framework architecture

 - [ ] Designed the automation framework using Maven project and all the dependency of the framework is maintained using Maven Pom.
 
 - [ ] Test Automation and execution will be carried out by TestNG architecture where individual functionality of the product under test will have unique TestNG class.
 
 - [ ] Api Services, routes, response & endpoints are kept under api package and getting used by API test cases which are written and kept under apitest package.
 
 - [ ] POJO class are created and utilized for data handling .

 - [ ] PageFactory design pattern are utilized for effectively automate UI testing where business layer of the application is kept seperately from test cases
 - [ ] Test reporting is handled by Extent-Reports

## Scenarios Automated

 - [ ] All the API requests available in the application are automated and out of which delete feature is not enabled in the project
 
 - [ ] UI functionality are covered by creating new user from the application and logging in with it and attempted to perform Bus battlefield quiz with selecting first and second option available 
 
## Triggering Regression pack
#### Using Maven build
selecting the maven POM file in the project and run as Maven Install or Mavel Test goal will generate the build and rwill trigger the test execution
 #### running as TestNG suite independently
Selecting the testng.xml file under the project and run as testNG suite will trigger the test execution
