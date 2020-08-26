##  Document purpose
I will try briefly to explain how I wanted to approach testing, which difficulties I faced and what I actually was able to produce.

## Initial technical task:
Setup BDD framework based on Cucumber
Write tests for https://petstore.swagger.io/

## Original plan was:
1. Write tests in Java.
2. Use generated client.
3. Write next tests:
    * CRUD operations with API
4. Use [Allure](http://allure.qatools.ru/) for reporting.

## What was achieved:
1. Maven project that has a series of API tests.
2. API tests include:
    - CRUD tests with PET API. This few tests shows ability to work with Rest API.
3. After tests are finished, allure report is generated.

## Difficulties I have faced during testing :
Initial project setup was done with generated client from swager. Test was runnable but client had an issues. So I had to rewrite client using RestAssured library.
Requests were returning JasonPath and I was working with stings for validating response.
But then I decided to rewrite string validations to data models. So I can work with objects instead of strings.
Most of the time I had to spend on figuring out right dependencies as maven would not start tests what blocked possibility of going to CI.

In order to run test you can use IDE or you need to call `mvn cleat test` task inside the project folder.
In order to get report you need to call `allure serve target/surefire-reports/` task inside the project folder. But to be able to do so
you need to install `allure` with `brew install allure`.

## What could be improved :
- Data provider with various test data sets can be created. Can be added logger to requests and responses. Extend amount of test to cover all endpoints and add negative cases.