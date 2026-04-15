import java.util.*;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean issued;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issued = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIssued() {
        return issued;
    }

    public void issueBook() {
        issued = true;
    }

    public void returnBook() {
        issued = false;
    }

    public String getDetails() {
        return id + " | " + title + " by " + author + " | " + (issued ? "Issued" : "Available");
    }
}

class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book b) {
        books.add(b);
        System.out.println("Book added successfully");
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available");
            return;
        }

        System.out.println("\nLibrary Books:");
        for (Book b : books) {
            System.out.println(b.getDetails());
        }
    }

    public Book findBook(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    public void issueBook(int id) {
        Book b = findBook(id);

        if (b == null) {
            System.out.println("Book not found");
        } else if (b.isIssued()) {
            System.out.println("Book already issued");
        } else {
            b.issueBook();
            System.out.println("Book issued successfully");
        }
    }

    public void returnBook(int id) {
        Book b = findBook(id);

        if (b == null) {
            System.out.println("Book not found");
        } else if (!b.isIssued()) {
            System.out.println("Book was not issued");
        } else {
            b.returnBook();
            System.out.println("Book returned successfully");
        }
    }

    public void searchBook(String keyword) {
        boolean found = false;

        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("Found: " + b.getDetails());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching books found");
        }
    }

    public void removeBook(int id) {
        Iterator<Book> it = books.iterator();
        boolean removed = false;

        while (it.hasNext()) {
            Book b = it.next();

            if (b.getId() == id) {
                it.remove();
                removed = true;
                System.out.println("Book removed successfully");
                break;
            }
        }

        if (!removed) {
            System.out.println("Book not found");
        }
    }
}

public class LibraryApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library lib = new Library();

        while (true) {
            System.out.println("\n===== Library Menu =====");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. Remove Book");
            System.out.println("7. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();

                    lib.addBook(new Book(id, title, author));
                    break;

                case 2:
                    lib.displayBooks();
                    break;

                case 3:
                    System.out.print("Enter Book ID to issue: ");
                    lib.issueBook(sc.nextInt());
                    break;

                case 4:
                    System.out.print("Enter Book ID to return: ");
                    lib.returnBook(sc.nextInt());
                    break;

                case 5:
                    sc.nextLine();
                    System.out.print("Enter keyword: ");
                    String keyword = sc.nextLine();
                    lib.searchBook(keyword);
                    break;

                case 6:
                    System.out.print("Enter Book ID to remove: ");
                    lib.removeBook(sc.nextInt());
                    break;

                case 7:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
