Personal expenses management application that allows users to 
track how much money have they spent.

This is command-line application.

To build an application: 
- download the sorce files
- go to project derectory and print command: mvn package

To run an application:
- print command: - java –jar target\money-tracker-1.0.jar

Application supports following commands: 

● add 2017-04-25 12 USD Jogurt  - adds expense entry to the list 
of user expenses. Expenses for various dates could be added in 
any order. Command accepts following parameters: 
 
2017-04-25- is the date when expense occurred 
12 - is an amount of money spent 
USD - the currency in which expense occurred 
Jogurt  - is the name of product purchased 
 
● list - shows the list of all expenses sorted by date 
 
● clear 2017-04-25 - removes all expenses for specified date, 
where: 
 
2017-04-25 - is the date for which all expenses should be 
removed 
 
● total PLN - this command should take a list of exchange rates 
from "http://fixer.io", calculate the total amount of money spent and 
present it to user in specified currency, where: 
 
PLN - is the currency in which total amount of expenses should 
be presented 
