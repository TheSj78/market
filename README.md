# Project-05
### CS 180-L03

Rohin Gupta, Shubham Jain, Pratham Patel, Keshav Shylesh, Thomas Temperley

For this Project, our group decided to go with Option 3. We created a Marketplace system where users can buy/sell items.

## How to compile:

First run the MarketServer.java file to start up the server. Then you will be able to run MarketClient.java. 
Marketclient.java can be run in multiple instances to have multiple users at the same time.

The following code snippet will allow one to compile and run our Project.
```java
javac *.java
java MarketServer.java
java MarketClient.java
```

## Submission details:

1. The Project Report was submitted by Shubham Jain on Brightspace.
2. The Project code was submitted by Keshav Shylesh on Vocareum.
3. The Project Presentation was submitted by Rohin Jain 

## Class descriptions:

* **MarketClient.java**: This class is where the client side operations occur. It uses a GUI to handle interactions from the user.
* **MarketServer.java**: This class is where the server side operations occur. This directly interacts with **MarketClient**.
* **MarketServerThread.java**: This class is how the server handles multiple users through concurrency. It is used in **MarketClient**.
* **User.java**: This class is a Parent class of both **Seller** and **Customer**. It represents a singular **User** of the Marketplace.
* **Customer.java**: This class extends **User**, and represents a **Customer** accessing the Marketplace.
* **Seller.java**: This class extends **User**, and represents a **Seller** accessing the Marketplace.
* **Product.java**: This class represents an individual **Product** item, which keeps track of its name, description, price, store name, and quantity available.
* **Cart.java**: This class represents a **Customer**'s cart, and keeps track of all the **Product**s inside of it.
* **Store.java**: This class represents one of the **Seller**'s **Store**.
* **CSVHandler.java**: This class handles some of the CSV operations used by other classes.
* **InvalidStoreNameException.java**: This class represents a custom Exception, thrown when an Invalid Store Name is detected.
* **InvalidProductNameException.java**: This class represents a custom Exception, thrown when an Invalid Product Name is detected.







