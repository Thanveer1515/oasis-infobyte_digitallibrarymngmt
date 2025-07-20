import java.util.*;

public class LibraryManagementSystem {
    static Scanner sc = new Scanner(System.in);

    // Book class
    static class Book {
        String id, title, author;
        boolean isIssued = false;

        Book(String id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
        }

        public String toString() {
            return "[" + id + "] " + title + " by " + author + (isIssued ? " (Issued)" : " (Available)");
        }
    }

    // Member class
    static class Member {
        String name, email;
        List<String> issuedBooks = new ArrayList<>();

        Member(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

    static Map<String, Book> books = new LinkedHashMap<>();
    static Map<String, Member> members = new LinkedHashMap<>();

    // Admin credentials
    static String adminUser = "admin";
    static String adminPass = "lib@123";

    public static void main(String[] args) {
        System.out.println("📘 Welcome to Digital Library Management System");
        while (true) {
            System.out.println("\nLogin as: 1. Admin  2. User  3. Exit");
            System.out.print("Enter option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> userPanel();
                case 3 -> {
                    System.out.println("👋 Exiting... Thank you!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option.");
            }
        }
    }

    // Admin functionality
    static void adminLogin() {
        System.out.print("Enter admin username: ");
        String user = sc.nextLine();
        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        if (user.equals(adminUser) && pass.equals(adminPass)) {
            System.out.println("✅ Admin Login Successful!");

            while (true) {
                System.out.println("\nAdmin Panel:");
                System.out.println("1. Add Book");
                System.out.println("2. Remove Book");
                System.out.println("3. Add Member");
                System.out.println("4. View All Books");
                System.out.println("5. View All Members");
                System.out.println("6. Logout");

                int option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 1 -> addBook();
                    case 2 -> removeBook();
                    case 3 -> addMember();
                    case 4 -> viewBooks();
                    case 5 -> viewMembers();
                    case 6 -> {
                        System.out.println("🔒 Admin Logged Out.");
                        return;
                    }
                    default -> System.out.println("⚠️ Invalid option.");
                }
            }
        } else {
            System.out.println("❌ Wrong credentials.");
        }
    }

    // User functionality
    static void userPanel() {
        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        if (!members.containsKey(email)) {
            System.out.println("❌ Member not found.");
            return;
        }

        Member user = members.get(email);
        System.out.println("✅ Welcome, " + user.name);

        while (true) {
            System.out.println("\nUser Panel:");
            System.out.println("1. View Available Books");
            System.out.println("2. Search Book by Title");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Issued Books");
            System.out.println("6. Logout");

            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1 -> viewBooks();
                case 2 -> searchBook();
                case 3 -> issueBook(user);
                case 4 -> returnBook(user);
                case 5 -> viewIssued(user);
                case 6 -> {
                    System.out.println("👋 User Logged Out.");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option.");
            }
        }
    }

    static void addBook() {
        System.out.print("Enter Book ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        books.put(id, new Book(id, title, author));
        System.out.println("📗 Book added successfully.");
    }

    static void removeBook() {
        System.out.print("Enter Book ID to remove: ");
        String id = sc.nextLine();
        if (books.containsKey(id)) {
            books.remove(id);
            System.out.println("🗑️ Book removed.");
        } else {
            System.out.println("❌ Book not found.");
        }
    }

    static void addMember() {
        System.out.print("Enter member name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        members.put(email, new Member(name, email));
        System.out.println("👤 Member added.");
    }

    static void viewBooks() {
        System.out.println("\n📚 Book List:");
        books.values().forEach(book -> System.out.println(book));
    }

    static void viewMembers() {
        System.out.println("\n👥 Member List:");
        members.values().forEach(m -> System.out.println(m.name + " (" + m.email + ")"));
    }

    static void searchBook() {
        System.out.print("Enter book title to search: ");
        String title = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Book book : books.values()) {
            if (book.title.toLowerCase().contains(title)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) System.out.println("🔍 No matching book found.");
    }

    static void issueBook(Member user) {
        System.out.print("Enter Book ID to issue: ");
        String id = sc.nextLine();
        if (books.containsKey(id) && !books.get(id).isIssued) {
            books.get(id).isIssued = true;
            user.issuedBooks.add(id);
            System.out.println("✅ Book Issued.");
        } else {
            System.out.println("❌ Book not available or doesn't exist.");
        }
    }

    static void returnBook(Member user) {
        System.out.print("Enter Book ID to return: ");
        String id = sc.nextLine();
        if (user.issuedBooks.contains(id)) {
            books.get(id).isIssued = false;
            user.issuedBooks.remove(id);
            System.out.println("🔁 Book Returned.");
        } else {
            System.out.println("❌ You haven’t issued this book.");
        }
    }

    static void viewIssued(Member user) {
        System.out.println("\n📦 Issued Books:");
        if (user.issuedBooks.isEmpty()) {
            System.out.println("No books issued.");
        } else {
            user.issuedBooks.forEach(id -> System.out.println(books.get(id)));
        }
    }
}
