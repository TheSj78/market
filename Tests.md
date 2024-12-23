# Test 1: User Sign Up

Steps: 

1.	User launches application.
2.	User selects Sign Up.
3.	User enters username in “Enter Username” textbox followed by pressing “OK”.
4.	User enters password in “Enter Password” textbox followed by pressing “OK”.
5.	User selects Seller or Customer when asked “Are you a Customer or Seller?”.

Expected result: Application returns to login or sign-up page.

Test Status: Passed.

# Test 2: User Login

Steps: 

1.	User launches application.
2.	User selects login.
3.	User enters username in “Username” textbox.
4.	User enters password in “Password” textbox.
5.	User presses “Login” button.

Expected result: 

Application goes to a panel with the following options if User is a Seller

OR Application goes to a panel with the following options if User is a Customer:
  
OR a message dialog with the following message is shown if the username does not exist: “Account doesn’t exist! Sign-Up.”

OR a message dialog with the following message is shown if the password is incorrect:
“Incorrect Password”

Test Status: Passed

# Seller Tests

# Test 3: Open a Store 

Steps:

1.	User logins in as a Seller.
2.	User selects “Open a Store” option.
3.	User enters store name in “Enter Store Name” textbox followed by selecting “OK”.
Expected result: Application goes to a panel with the “Opened Store!” message.
Test Status: Passed

# Test 4: Display a Store 

Steps:

1.	User logins in as a Seller.
2.	User selects “Display Stores” option.

Expected result: Application goes to a panel with the “List of all stores” heading followed by all stores in a box.

Test Status: Passed.

# Test 5: Edit Store 

Steps:

1.	User logins in as a Seller.
2.	User selects “Edit Store” option.
3.	User enters store name in “Enter Store Name” textbox.
Expected result: Application goes to a panel with the “Add Product”, “Remove Product”, “Edit Product”, “Close Store” buttons on the top.
## Test 5a: Add Product 

Steps:
1.	User selects “Add Product” option.
2.	User enters product name in “Enter Product Name” textbox.
3.	User enters product description in “Enter Product Description” textbox.
4.	User enters product quantity in “Enter Product Quantity” textbox.
5.	User enters product price in “Enter Product Price” textbox.


Expected Result: Product is added to the marketplace and this can be checked with the “View Market” option in the Customer options.
OR 
An error message “Invalid Input” is shown if there’s an error in entering the fields and an exception in thrown.

Test Status: Passed.

## Test 5b: Remove Product 

Steps:
1.	User selects “Remove Product” option.
2.	User selects a product from the list of products in the store when prompted with “Select a Product.”

Expected Result: Product is removed from the Store and this can be verified by selecting the “Shop by Store” in the customer options or by selecting the  “View Market” option in the Customer options
Test Status: Passed.


## Test 5c: Edit Product 

Steps:
1.	User selects “Edit Product” option.
2.	User selects a product from the list of products in the store when prompted with “Select a Product”.
3.	User enters new product name in the “Enter New Product Name” textbox.
4.	User enters new product description in the “Enter New Product Description” textbox.
5.	User enters new product price in the “Enter New Product Price” textbox.
6.	User enters new product price in the “Enter New Product Quantity” textbox.

Expected Result: The specified product’s fields have been edited and this can be verified by selecting the “Shop by Store” in the customer options or by selecting the  “View Market” option in the Customer options

Test Status: Passed.

## Test 5d: Close Store

Steps:
1.	User selects “Close Store” option.

Expected Result: The given store has been removed from the marketplace and this can be verified using the “Display Stores” option.

Test Status: Passed.

# Test 6: Import Products 

Steps:
1.	User logs in as Seller.
2.	User selects “Import Products” option.
3.	Enter file path in “Enter file path” textbox.
4.	Enter store name in “Enter store name” textbox.
5.	Press enters.
Expected Result: User is shown a message dialog with the message “Imported Products!”,
OR
User is shown a message dialog with the message “Invalid Path” if an IOException is thrown,
OR
User is shown a message dialog with the message “Product does not exist!” if an InvalidProductNameException is thrown.

Test status: Passed

# Test 7: Export Products (Seller)

Steps:
1.	User logs in as Seller.
2.	User selects “Export Products” option.
3.	Enter file path in “Enter Path to Export to” textbox.
4.	Press enter.
   
Expected Result : User is shown a message dialog with the message “Exported to path !” if the products are exported to the path of the User’s choice,
OR
User is shown a message dialog with the message “Invalid Path” if an IOException is thrown,

Test status: Passed

# Test 8: View Dashboard

Steps:
1.	User logs in as Seller.
2.	User selects “View Dashboard” option.
3.	User selects a store from the list of stores in the marketplace when prompted with “Select a Store.”
Expected Result: User is shown a list of product statistics that follows the format -
“<product name>-<product quantity sold> Sold.”

Test status: Passed

# Test 9: Edit Account (Seller)

Steps:
1.	User logs in as Seller.
2.	User selects “Edit Account” option.
3.	Enter new email in “Enter new email” textbox followed by selecting “OK”.
4.	Enter new password in “Enter new password” textbox followed by selecting “OK”.

Expected Result: User’s username and password is changed to new username and password and User is taken back to the Seller options panel.

Test status: Passed

## Test 10: Delete Account (Seller) 
Steps:
1.	User logs in as Seller.
2.	User selects “Delete Account” option.


Expected Result: User’s account is deleted and the User would not be able to login with the same credentials again.
Test status: Passed

# Customer Tests

# Test 11: View Market 

Steps:
1.	User logs in as a Customer.
2.	User selects “View Market”.
Expected Result: User is able to see a heading “List of all products” with all products listed in a box below.

Test status: Passed

# Test 12: Search Market 

Steps:
1.	User logs in as a Customer.
2.	User selects “Search Market” option.
3.	User enters search text in “Enter Search Text” textbox.


Expected Result: User is taken to a scroll pane with list of products as the search result for the search text,
OR
User is shown an error message “No Products Matched the Search” if no products were found based on the search text.

Test status: Passed

# Test 13: Sort Market 

Steps:
1.	User logs in as a Customer.
2.	User selects “Sort Market” option.
3.	User is shown a question dialog with the question “Sort by Quantity or Price?” and selects “Price” or “Quantity”

Expected Result: User is taken to a scroll pane with list of products sorted by either Price of Quantity depending on what the User selected,
OR
User is shown an error message “No Products to Sort!”  if there are no products in the marketplace.

Test status: Passed

# Test 14: Shop by Store

Steps:

1.	User logs in as a Customer.
2.	User selects “Shop by Store” option.


Expected Result: User shown a dropdown menu with list of stores in the marketplace.
OR
User is shown an error message “No Stores Available” if no stores are available in the marketplace.

3.	User selects a store from the dropdown menu and selects “OK”.

Expected Result: User is prompted to select a product from the list of products in the store in a dropdown menu.
OR
User is shown an error message “No Products Available” if the store currently has no products.

4.	User selects a product from the list of products in the store shown in a dropdown menu.
5.	User selects “Yes” or “No” when asked “Add to Cart.”

Expected Result: The product is added to the User’s cart and this can be verified by selecting the “View Cart” option.	

Test Status: Passed

# Test 15: View Cart 

1.	User logs in as a Customer.
2.	User selects “View Cart” option.
3.	User selects “Checkout” or “Remove Product” or “Empty Cart”.

Expected Result if User selects Checkout: All the products are purchased, and the cart Is empty. 

Expected result if User selects “Empty Cart”: The cart is emptied and this can be verified by using  “View Cart”

Expected Result if User selects “Remove Product”: User is prompted to select a product to be removed from the list of products in the cart.

Steps after removing product: 
1.	User selects a product to be removed from the list of products in the cart.
Expected result after removing product: The product is removed from the cart and this can be confirmed by selecting “View Cart” again.

Test status: Passed

# Test 16: Add to Cart 

Steps:
1.	User logs in as a Customer.
2.	User selects “Add to Cart” option.

Expected result: User is prompted with “Select a Product.” Displaying a list of products in the market.
OR
User is shown an error message “Product Not Found” if the marketplace has no products currently.

3.	User selects a product from the list of products in the marketplace shown.
   
Expected Result: The selected product has been added to the User’s shopping cart and the cart can be viewed using the “View Cart” option.

Test Status: Passed

# Test 17: View Dashboard

Steps:
1.	User logs in as a Customer.
2.	User selects “View Dashboard” option.

Expected result: User is shown their product history which follows the format –
“You have bought <number of products> items from <store name>.”

Test Status: Passed

# Test 18: Export Product History 

Steps: 

1.	User logs in in as a Customer.
2.	User selects “Export Product History”.
3.	User enters complete path of the file they want to export product history to in the “Enter Path to Export to” textbox.

Expected result: The product history is exported to the file of their choice and a message dialog “Exported to Path!” is shown, 
OR
User is shown a message dialog with the message “Invalid Path” if an IOException is thrown.

# Test 19: Edit Account 
Steps:
1.	User logs in as a customer.
2.	User selects “Edit Account” option.
3.	Enter new email in “Enter new email” textbox followed by selecting “OK”.
4.	Enter new password in “Enter new password” textbox followed by selecting “OK”.

Expected Result: User’s username and password is changed to new username and password and User is taken back to the customer options panel. This can be verified by logging in again and the old credentials won’t be accepted.

Test status: Passed

# Test 20: Delete Account (Seller) 
Steps:
1.	User logs in as Seller.
2.	User selects “Delete Account” option.


Expected Result: User’s account is deleted and the User would not be able to login with the same credentials again.
Test status: Passed




