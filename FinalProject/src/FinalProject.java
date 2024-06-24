import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class FinalProject {
	private Scanner input = new Scanner(System.in);
	private Item[] items = new Item[100];
    private static final String adminUsername = "admin";
    private static final String adminPassword = "password";
    private int itemCount = 0;

	public static void main(String[] args) {
		FinalProject foo = new FinalProject();
		foo.loadInitialData("./data/inventory.txt");
		foo.mainMenu();
	}

	public void mainMenu() {
		while (true) {
			// display menu and ask for user selection (validate)
			System.out.println("Main Menu:");
			System.out.println("1) Admin");
			System.out.println("2) User");
			System.out.println("3) Exit");
			System.out.println("Choose an option");
			int option = input.nextInt();
		
			switch (option) {
			case 1:
				// verify the admin login
				if (verifyAdminLogin()) {
					// if login correct go to the admin menu
					adminMenu();
				} else {
					System.out.println("Incorrect login credentials.");
				}
				break;
	
			case 2:
				userMenu();
				break;
	
			case 3:
				// confirm they want to exit and then exit
				System.out.println("Are you sure you want to exit? Yes/No");
				char confirm = input.next().charAt(0);
				if (confirm == 'Y' || confirm == 'y') {
					System.exit(0);
				}
				break;
				
			default:
				System.out.println("Invalid option. Try again.");
			}
		}
	}
	
	private boolean verifyAdminLogin() {
		System.out.println("Enter username: ");
        String username = input.next();
        System.out.println("Enter password: ");
        String password = input.next();

        return adminUsername.equals(username) && adminPassword.equals(password);
	}

	public void adminMenu() {
		// display menu and ask for user selection (validate)
		while (true) {
			System.out.println("Admin Menu:");
			System.out.println("1) View all items");
			System.out.println("2) Add new item");
			System.out.println("3) Search and update item");
			System.out.println("4) Search and delete");
			System.out.println("Choose an option");
			int option = input.nextInt();
			
			int found = -1;
			switch (option) {
			case 1:
				displayAllItems();
				break;
				
			case 2:
				addItem();
				break;
	
			case 3:
				System.out.println("What item would you like to update?");
				found = searchForItem();
	
				if (found != -1) {
					updateItem(found);
				}  else {
					System.out.println("Item not found.");
				}
				break;
				
			case 4:
				System.out.println("What item would you like to delete?");
				found = searchForItem();
				if (found != -1) {
				    deleteItem(found);
				} else {
					System.out.println("Item not found.");
				}
				break;
			
			default:
				System.out.println("Invalid option. Try again.");
			}
		}
	}

	public void userMenu() {
		while (true) {
			// display menu and ask for user selection (validate)
			System.out.println("User Menu:");
			System.out.println("1) Search item");
			System.out.println("2) Place order (or purchase item)");
			System.out.println("Choose an option");
			int option = input.nextInt();
			
			int found = -1;

			switch (option) {
			case 1:
				found = searchForItem();
				displayItem(found);
				break;
				
			case 2:
				System.out.println("What item would you like to purchase?");
                found = searchForItem();
                if (found != -1) {
                    purchaseItem(found);
                } else {
                    System.out.println("Item not found.");
                }
                break;
				
			default:
				return;
			}
		}
	}
	
    private void displayAllItems() {
        for (int i = 0; i < itemCount; i++) {
            System.out.println(items[i]);
        }
    }

	private void displayItem( int index) {
		if (index >= 0 && index < itemCount) {
			System.out.println(items[index]);	
		}
	}

	public int searchForItem() {
		System.out.println("Search by: ");
		System.out.println("1) ID ");
		System.out.println("2) Name ");
		int choice = input.nextInt();
		input.nextLine();
		
		switch (choice) {
		case 1:
			System.out.println("Enter product ID: ");
			String id = input.nextLine();
			for (int i = 0; i < itemCount; i++) {
				if (items[i].getId().equals(id)) {
					return i;
				}
			}
			break;
			
		case 2:
			System.out.println("Enter item name: ");
			String name = input.nextLine();
			for (int i = 0; i < itemCount; i++) {
				if (items[i].getName().equalsIgnoreCase(name)) {
					return i;
				}
			}
			break;
			
        default:
            System.out.println("Invalid choice. Please try again.");
		}
		return -1;  //if item not found otherwise return the array index of the item 
	}

	private void deleteItem(int index) {
		// add codes
        if (index >= 0 && index < itemCount) {
            for (int i = index; i < itemCount - 1; i++) {
                items[i] = items[i + 1];
            }
            items[--itemCount] = null;
            System.out.println("Item deleted.");
        }
	}

	private void updateItem(int index) {
		// add codes
        if (index >= 0 && index < itemCount) {
            System.out.print("Enter new quantity: ");
            int quantity = input.nextInt();
            items[index].setQuantity(quantity);
            System.out.println("Item updated.");
        }
	}

	private void addItem() {
		// TODO Auto-generated method stub
        System.out.print("Enter product ID: ");
        String id = input.next();
        System.out.print("Enter item name: ");
        String name = input.next();
        System.out.print("Enter unit cost: ");
        double price = input.nextDouble();
        System.out.print("Enter quantity: ");
        int quantity = input.nextInt();

        items[itemCount++] = new Item(id, name, price, quantity);
        System.out.println("Item added.");
	}

	private void purchaseItem(int index) {
		// add codes
        if (index >= 0 && index < itemCount) {
            System.out.print("Enter quantity to purchase: ");
            int quantity = input.nextInt();
            Item item = items[index];
            if (quantity > item.getQuantity()) {
                System.out.println("Sorry! Not enough in stock.");
            } else {
                double totalCost = quantity * item.getPrice() * 1.07;
                item.setQuantity(item.getQuantity() - quantity);
                System.out.println("Purchase successful! Total cost: $" + totalCost);
            }
        }
	}
	
    public void loadInitialData(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0];
                String name = data[1];
                double price = Double.parseDouble(data[2]);
                int quantity = Integer.parseInt(data[3]);
                items[itemCount++] = new Item(id, name, price, quantity);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

class Item {
	private String id;
	private String name;
	private double price;
	private int quantity;

	//add constructors
	public Item (String i, String n, double p, int q) {
		id = i;
		name = n;
		price = p;
		quantity = q;
	}
	
	//add get and set methods
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity (int q) {
		quantity = q;
	}
	
	@Override
	public String toString() {
		return "Item{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", price=" + price + ", quantity=" + quantity + '}';
	}
}