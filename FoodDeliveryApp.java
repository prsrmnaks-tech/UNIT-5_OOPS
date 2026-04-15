import java.util.*;

interface PaymentStrategy {
    void pay(int amount);
}

class UpiPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid ₹" + amount + " using UPI");
    }
}

class CardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid ₹" + amount + " using Card");
    }
}

class CashPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid ₹" + amount + " using Cash on Delivery");
    }
}

class Order {
    private List<String> items = new ArrayList<>();
    private List<Integer> prices = new ArrayList<>();
    private PaymentStrategy paymentStrategy;

    public void addItem(String item, int price) {
        items.add(item);
        prices.add(price);
    }

    public int calculateTotal() {
        int total = 0;
        for (int price : prices) {
            total += price;
        }
        return total;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public int placeOrder() {
        System.out.println("\nYour Order:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i) + " - ₹" + prices.get(i));
        }

        int total = calculateTotal();
        System.out.println("Total amount: ₹" + total);

        if (paymentStrategy != null) {
            paymentStrategy.pay(total);
        }

        return total;
    }

    public List<String> getItems() {
        return items;
    }
}

public class FoodDeliveryApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Order order = new Order();

        System.out.println("Food Delivery CLI App");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Pizza - ₹200");
            System.out.println("2. Burger - ₹150");
            System.out.println("3. Biryani - ₹250");
            System.out.println("4. Done");

            int choice = sc.nextInt();

            if (choice == 1) order.addItem("Pizza", 200);
            else if (choice == 2) order.addItem("Burger", 150);
            else if (choice == 3) order.addItem("Biryani", 250);
            else if (choice == 4) break;
            else System.out.println("Invalid choice");
        }

        if (order.getItems().isEmpty()) {
            System.out.println("No items selected");
            return;
        }

        System.out.println("\nSelect Payment Method:");
        System.out.println("1. UPI");
        System.out.println("2. Card");
        System.out.println("3. Cash");

        int payChoice = sc.nextInt();

        if (payChoice == 1) order.setPaymentStrategy(new UpiPayment());
        else if (payChoice == 2) order.setPaymentStrategy(new CardPayment());
        else if (payChoice == 3) order.setPaymentStrategy(new CashPayment());
        else {
            System.out.println("Invalid payment option");
            return;
        }

        int total = order.placeOrder();
        System.out.println("\nFinal Bill: ₹" + total);

        sc.close();
    }
}
