import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

interface Vehicle {
    String getType();
    double getRatePerDay();
}

class Car implements Vehicle {
    public String getType() {
        return "Car";
    }

    public double getRatePerDay() {
        return 2000;
    }
}

class Bike implements Vehicle {
    public String getType() {
        return "Bike";
    }

    public double getRatePerDay() {
        return 500;
    }
}

class Truck implements Vehicle {
    public String getType() {
        return "Truck";
    }

    public double getRatePerDay() {
        return 5000;
    }
}

class VehicleFactory {
    public static Vehicle createVehicle(String type) {
        switch (type.toLowerCase()) {
            case "car": return new Car();
            case "bike": return new Bike();
            case "truck": return new Truck();
            default: throw new IllegalArgumentException("Invalid vehicle");
        }
    }
}

class Rental {
    private String customer;
    private Vehicle vehicle;
    private int days;

    public Rental(String customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
    }

    public String getDetails() {
        double total = vehicle.getRatePerDay() * days;
        return customer + " → " + vehicle.getType() +
               " (" + days + " days) = ₹" + total;
    }
}

class RentalService {
    private final List<Rental> rentals =
        Collections.synchronizedList(new ArrayList<>());

    public void book(String name, String type, int days) {
        Vehicle v = VehicleFactory.createVehicle(type);
        Rental r = new Rental(name, v, days);
        rentals.add(r);
    }

    public List<Rental> getAll() {
        return rentals;
    }
}

class RentalUI extends JFrame {
    private JTextField nameField;
    private JComboBox<String> vehicleBox;
    private JTextField daysField;
    private JTextArea outputArea;
    private RentalService service;

    public RentalUI(RentalService service) {
        this.service = service;

        setTitle("Vehicle Rental System 🚗");
        setSize(450, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Customer Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Vehicle:"));
        vehicleBox = new JComboBox<>(new String[]{"Car", "Bike", "Truck"});
        panel.add(vehicleBox);

        panel.add(new JLabel("Days:"));
        daysField = new JTextField();
        panel.add(daysField);

        JButton bookBtn = new JButton("Book");
        panel.add(bookBtn);

        add(panel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        bookBtn.addActionListener((ActionEvent e) -> {
            new Thread(() -> processBooking()).start();
        });

        setVisible(true);
    }

    private void processBooking() {
        String name = nameField.getText();
        String vehicle = vehicleBox.getSelectedItem().toString();
        int days;

        try {
            days = Integer.parseInt(daysField.getText());
        } catch (Exception e) {
            showMessage("Invalid days");
            return;
        }

        service.book(name, vehicle, days);

        SwingUtilities.invokeLater(() -> {
            outputArea.append("Booked: " + name + "\n");
            refreshDisplay();
        });
    }

    private void refreshDisplay() {
        outputArea.append("\nAll Rentals:\n");

        for (Rental r : service.getAll()) {
            outputArea.append(r.getDetails() + "\n");
        }

        outputArea.append("----------------------\n");
    }

    private void showMessage(String msg) {
        SwingUtilities.invokeLater(() ->
            JOptionPane.showMessageDialog(this, msg)
        );
    }
}

public class Factory {
    public static void main(String[] args) {
        RentalService service = new RentalService();
        new RentalUI(service);
    }
}
