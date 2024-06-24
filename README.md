# Inventory Management System 
The IMS will contain a catalog of items (stationery Items) that can be managed, viewed, and purchased.
## Components detail:
The Inventory Management System (IMS) has two main components
- Inventory management component
- Inventory viewing and purchasing component.
### Inventory Management Component
The inventory management component allows an admin user with the following functionalities:
1. Ability to login to the system as admin role with a predefined "admin" as the username and "password" as the password. This is the only role that would require a user to login to the system.
2. Ability to view, add, update, and delete items from the inventory.  
### Inventory Viewing and Purchasing Component
The inventory viewing and purchasing component allows any user (no login necessary) to perform the following:
1. Search and view for items. Searching capabilities are limitied to the name of the item or productId.
2. Purchase items.
- This action will ask for the quantity.
- It will show the total cost of the purchase with a 7% sales tax.
- This action will also update the inventory to reduce it by the quantity that was purchased. 
## Implementation Details
### Command line Menu:
Main Menu:
1) Admin
2) User
3) Exit

Admin Menu:
1) View all items
2) Add new item
3) Search and update item
4) Search and delete

User Menu:
1) Search item
2) Place order (or purchase item)

