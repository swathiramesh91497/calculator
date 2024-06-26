# Calculator
This calculator app does basic math operations such as: 
  - Apis: 
    - Add
    - Subtract 
    - Divide
    - Multiply 
    - Audit

There is an additional endpoint called "audit" to receive a list of all the math problems you previously computed.\
Swagger Documentation can be seen here: http://localhost:8080/openapi/swagger-ui/index.html 
- The audit endpoint is protected by http basic authorization. 
- The correct way to call it would be: 
```
curl -u admin:password "http://localhost:8080/calculator/audit"
```
## To Run 
- Clone the Repository 
- mvn clean install 
- Run the main method of the CalculatorApp class 