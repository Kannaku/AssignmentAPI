#Author: your.email@your.domain.com
@tag
Feature: verify the customer view and Customer ID list from Blog api
  
  @customerview
  Scenario Outline: customer view postive customer IDS
    Given I listed out the "Customers" ids from api
    Then I GET "Customerview" data for "<customerID>" and validate the status code 200 and index as "<listindex>"
    
    Examples:
    |customerID|listindex|
    |1111|0|
    |2222|1|
    |3333|2|
    |4444|3|
    |5555|4|
    |6666|5|
    
    @negativeCustomerView
  Scenario Outline: customer view negative customer IDS
    Given I listed out the "Customers" ids from api
    Then I verify "Customerview" the wrong customer ID as "12" and status code should be 404
    
    