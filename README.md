**SUPERMARKET CHECKOUT API**

This is a SpringBoot REST API application which allows users to add, update or remove items to a cart.
It also allows admin to add, update or remove items on offer.

The main purpose of this application is to calculate the total price of the items added to the cart including all the offers.

**How to build and run:**

Maven is used to build and manage the dependencies.   `mvn clean install` command can be used to create the jar file which will be created in the target folder within the project.

Command to run the JAR file from the jar location: `java -jar checkout.systems-0.0.1-SNAPSHOT.jar` this will run the application and following endpoints can be accessed via postman or any other API tester.

Once the project is started you can use  the following endpoints:

ITEMS ON OFFER ENDPOINTS
1. ADD ITEM ON OFFER : `http://localhost:8080/addAnItemOnOffer/A/1/50`
2. REMOVE ITEM ON OFFER : `http://localhost:8080/removeAnItemOnOffer/A/2/130`
3. UPDATE ITEM ON OFFER : `http://localhost:8080/updateAnItemOnOffer/A/2/130`
4. GET ALL ITEMS ON OFFERS : `http://localhost:8080/getAllOffersOnItems`

ITEMS ON CART ENDPOINTS
1. ADD/UPDATE AN ITEM : POST `http://localhost:8080/addOrUpdateItem`  with body. Below is an example that can be provided as a body.
       example : `[{
   "itemName": "A","itemQuantity": 1,
   "itemPrice": 50.0
   },
   {
   "itemName": "B",
   "itemQuantity": 1,
   "itemPrice": 45.0
   }
   ]`
2. REMOVE AN ITEM : `http://localhost:8080/removeItems`
3. RETRIEVE ALL ITEMS ON CART: `http://localhost:8080/cartItems`

To find the total Price of all the items in cart use : `http://localhost:8080/totalPrice`
