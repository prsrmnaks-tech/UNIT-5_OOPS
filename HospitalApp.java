import java.util.*;

class Person {
    protected int id;
    protected String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void showDetails() {
        System.out.println(id + " - " + name);
    }
}

class Patient extends Person {
    private String disease;
    private boolean admitted;

    public Patient(int id, String name, String disease) {
        super(id, name);
        this.disease = disease;
        this.admitted = false;
    }

    public void admit() {
        admitted = true;
    }

    public void discharge() {
        admitted = false;
    }

    @Override
    public void showDetails() {
        System.out.println(
            "Patient: " + name +
            " | Disease: " + disease +
            " | " + (admitted ? "Admitted" : "Not Admitted")
        );
    }
}

class Doctor extends Person {
    private String specialization;

    public Doctor(int id, String name, String specialization) {
        super(id, name);
        this.specialization = specialization;
    }

    @Override
    public void showDetails() {
        System.out.println(
            "Doctor: " + name +
            " | Specialization: " + specialization
        );
    }
}

class Hospital {
    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();

    public void addPatient(Patient p) {
        patients.add(p);
        System.out.println("Patient added");
    }

    public void addDoctor(Doctor d) {
        doctors.add(d);
        System.out.println("Doctor added");
    }

    public Patient findPatient(int id) {
        for (Patient p : patients) {
            if (p.id == id) return p;
        }
        return null;
    }

    public void admitPatient(int id) {
        Patient p = findPatient(id);
        if (p == null) {
            System.out.println("Patient not found");
        } else {
            p.admit();
            System.out.println("Patient admitted");
        }
    }

    public void dischargePatient(int id) {
        Patient p = findPatient(id);
        if (p == null) {
            System.out.println("Patient not found");
        } else {
            p.discharge();
            System.out.println("Patient discharged");
        }
    }

    public void displayPatients() {
        for (Patient p : patients) {
            p.showDetails();
        }
    }

    public void displayDoctors() {
        for (Doctor d : doctors) {
            d.showDetails();
        }
    }
}

public class HospitalApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hospital h = new Hospital();

        while (true) {
            System.out.println("\n1.Add Patient");
            System.out.println("2.Add Doctor");
            System.out.println("3.Admit Patient");
            System.out.println("4.Discharge Patient");
            System.out.println("5.View Patients");
            System.out.println("6.View Doctors");
            System.out.println("7.Exit");

            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("ID: ");
                    int pid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Name: ");
                    String pname = sc.nextLine();

                    System.out.print("Disease: ");
                    String disease = sc.nextLine();

                    h.addPatient(new Patient(pid, pname, disease));
                    break;

                case 2:
                    System.out.print("ID: ");
                    int did = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Name: ");
                    String dname = sc.nextLine();

                    System.out.print("Specialization: ");
                    String spec = sc.nextLine();

                    h.addDoctor(new Doctor(did, dname, spec));
                    break;

                case 3:
                    System.out.print("Enter Patient ID: ");
                    h.admitPatient(sc.nextInt());
                    break;

                case 4:
                    System.out.print("Enter Patient ID: ");
                    h.dischargePatient(sc.nextInt());
                    break;

                case 5:
                    h.displayPatients();
                    break;

                case 6:
                    h.displayDoctors();
                    break;

                case 7:
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
