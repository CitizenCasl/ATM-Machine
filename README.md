# ATM
A console application that simulates the operation of an ATM. Users can perform various operations such as checking the balance, withdrawing funds, and depositing money after successful authorization using a card number and PIN code.
## Functionality
### Authorization
The user must enter:

  **1. A valid card number matching the pattern: 'XXXX-XXXX-XXXX-XXXX'**
  
  **2. The correct PIN code**

### Operations
After successful authorization, the user can perform the following operations:
 - **Check Balance**: Displays the current balance on the card. 
 - **Withdraw Funds**: Allows withdrawing funds from the account, not exceeding the current balance or the ATM's limit.
 - **Deposit Funds**: Allows depositing money to the card (maximum deposit amount is 1,000,000).

### Data Storage
- Data is stored in a text file with space (" ") as a delimiter.
- The program saves its state after completion (the data file is updated).

### Error Handling
All messages about successful actions and errors are displayed on the console.

### Requirements
- **Programming language: Java version 7+**
- **Minimum number of classes: 4**
- **Adherence to principles of strong cohesion and the 4 principles of OOP**
- **Compliance with Java code style (variable naming, class structure, etc.)**
- **High-quality error handling**
- **Use of collections for working with data lists**

### Additional Tasks
- Implement a console menu for the application (console input)
- Create a .bat file to run the application
- The PIN code can be entered incorrectly 3 times, after which the card should be blocked
- The card blockage is automatically lifted after 24 hours
