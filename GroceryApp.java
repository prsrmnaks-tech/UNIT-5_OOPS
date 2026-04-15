import java.util.*;

class Product {
    protected String name;
    protected double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double calculateCost(int qty) {
        return price * qty;
    }

    public String getName() {
        return name;
    }
}

class Fruit extends Product {
    public Fruit(String name, double price) {
        super(name, price);
    }

    @Override
    public double calculateCost(int qty) {
        return price * qty;
    }
}

class Vegetable extends Product {
    public Vegetable(String name, double price) {
        super(name, price);
    }

    @Override
    public double calculateCost(int qty) {
        return price * qty;
    }
}

class Dairy extends Product {
    public Dairy(String name, double price) {
        super(name, price);
    }

    @Override
    public double calculateCost(int qty) {
        return price * qty;
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotal() {
        return product.calculateCost(quantity);
    }

    public String getDetails() {
        return product.getName() + " x " + quantity + " = ₹" + getTotal();
    }
}

class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(Product p, int qty) {
        items.add(new CartItem(p, qty));
        System.out.println("Item added to cart");
    }

    public void displayBill() {
        double total = 0;
        System.out.println("\n---- Bill ----");

        for (CartItem item : items) {
            System.out.println(item.getDetails());
            total += item.getTotal();
        }

        System.out.println("Total Bill: ₹" + total);
    }
}

public class GroceryApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Cart cart = new Cart();

        while (true) {
            System.out.println("\n1. Add Fruit");
            System.out.println("2. Add Vegetable");
            System.out.println("3. Add Dairy");
            System.out.println("4. View Bill");
            System.out.println("5. Exit");

            int choice = sc.nextInt();

            if (choice == 5) break;

            if (choice >= 1 && choice <= 3) {
                sc.nextLine();

                System.out.print("Enter product name: ");
                String name = sc.nextLine();

                System.out.print("Enter price: ");
                double price = sc.nextDouble();

                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();

                Product p;

                if (choice == 1) p = new Fruit(name, price);
                else if (choice == 2) p = new Vegetable(name, price);
                else p = new Dairy(name, price);

                cart.addItem(p, qty);
            } 
            else if (choice == 4) {
                cart.displayBill();
            } 
            else {
                System.out.println("Invalid choice");
            }
        }

        System.out.println("Thank you!");
        sc.close();
    }
}
