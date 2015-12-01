# **Bike Hire System**

### Overview
This project was set as an assignment for the Visual Object Software module of my MSc Computing course. The task was to design, implement and test a stand-alone computerised system using object-oriented principles in Java. The system's primary function is to cater for the hiring of bikes by new and existing customers.

### Quick Start
Further information regarding the project is provided below, however you can start using the Bike Hire System immediately by following these simple steps: 

1. Run BikeHireApp.jar to start the application
  - Username: "user"
  - Password: "pass"
2. On first launch, the app will fail to find existing data, you have 2 options:
  - Select yes to generate some dummy data that you can use to familiarise yourself with the system (recommended)
  - Select no to begin using an empty system without any pre-existing data
3. See the [user guide](doc/userguide/userguide.pdf) for usage instructions

### Project Definition
##### Brief
The Northampton Bicycle Hire Company is looking to computerise its system for managing the bike hires. The current (manual) system is as follows: A customer goes to a reception desk and asks for the type of bike they want to hire, e.g. a woman’s ten-speed racer in blue. The receptionist relays this information to a member of staff, who brings out a suitable bike from the store room. The receptionist checks the bike number, which is painted on the side of the bike, and pulls out the index card for this bike from a card index box marked “In Box”. The card has on it details about the bike, such as its number, make, model, type (men, women, children), colour and frame size. The card also shows the daily hire rate and the deposit, and has space to record details about each hire transaction. The receptionist fills in details of the customer’s name and address, and the start and return dates of the hire. She then works out the amount due (daily hire * number of days + deposit), and records this on the card. The amount due is paid by the customer, who then takes the bike and departs. The receptionist files the completed card in an index box marked “Out Box”. When the bike is returned, the customer tells the receptionist his or her name and the relevant card is extracted from the “Out Box”. The details on the card are checked to see that they correspond with the bike that is being returned. The date of return is also checked; if the return is late, the customer is required to pay an extra charge. The bike is then examined by a mechanic to check for any damage. If the bike is in good condition, the receptionist returns the deposit to the customer.

##### Minimum Requirements

1. To record details of a new customer (name, address, etc.)
2. Hire bikes to customers (i.e. record details of transaction)
3. To generate two separate customer invoices: one at the time of bike hire and another at the time of bike return
4. To simply record (make) a payment for a particular customer
5. To display the payment history of a particular customer

##### Additional Requirements

1. Permanent storage and retrieval of records via object serialization
2. Maintain the stock and status of all bikes (updated after each applicable transaction)
3. Maintain the status of all hires (updated after each applicable transaction)
4. Login feature for the system (username and password)
5. To record details of a new bike
6. Facility to view all customer, bike, hire and invoice details
7. Business analysis; most and least popular bikes

##### Scope
The system is not concerned with the following: 

1. Advance booking of bikes
2. Promotional offers
3. Refunds / Deletetion of hires
4. Capturing the fluctuation of customer and bike details - it is inevitable that customer details (address) and bike details (daily hire rate) will change throughout their existence in the system

### Dependencies
[Joda-Time](http://www.joda.org/joda-time/) is used by some of the classes in the Bike Hire System to handle date/time operations. The library files for Joda-Time can be found in the [lib](lib/joda-time-2.7) folder. 

All unit tests are written using [JUnit](http://junit.org).
