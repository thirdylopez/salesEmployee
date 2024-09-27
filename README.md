# salesEmployee
Demo Project for Sales Employee
Create REST API for managing sales employees information, update their information, and calculate their commissions using Java Springboot.
Requirements: 
The application should have resources/endpoints for:
1. Adding sales employees POST /employee - basic information like full name, birthday, and position
2. Update sales employees PUT /employee/{id} - just updating the whole employee basic information
3. Get employee information GET /employee{id} - returns the basic information
4. Calculate commission for a specific employee for a month POST /employee/{id}/commission - for simplicity, the total amount sale of an employee for a month would be the only field  in the body of this request to this endpoint. Commission value would be the response body of this endpoint
For calculating commissions, it is based on the sales employee's position or role in the company. Different roles will have different calculation strategies. Make the system flexible for modification when it comes to calculating commissions, we may add additional role and its own formula for calculating commissions in the future. 
Below are the diff formula for each sales position:
1. Sales Associate: There would be a 5% commission rate on the total sales of a sales associate.
2. Senior Sales Associate: There would be a 7% commission rate on total sales of a senior sales associate and an additional 2% on the excess of the target threshold of 50,000.0 
3. Sales Manager: 6% commission rate on team total sales and an additional fixed rate bonus of 1,000.0 if the team target threshold of 200,00.0 has been achieved
Lets first priorities working on the business logic of the application, like calculating commissions, and adding unit tests. Then work our way to the Springboot components/layers
