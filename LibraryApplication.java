//package ph.com.bpi.m2project2;
package M2Project2;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
public class LibraryApplication {
	
	// Name of user
    private User user;
    
    // Library that stores books and loan
    private Library library;
    private final Queue<Books> listbook = new LinkedList<>();
    static class Books {
    	private final int id;
        private String title;
        private String author;

        public Books(int id, String title, String author) {
    		this.id = id;
            this.title = title;
            this.author = author;
        }
        public int getId() 
        { 
        	return id; 
        }
        public String getTitle() 
        { 
        	return title; 
        }
        public String getAuthor() 
        { 
        	return author;
        }
        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
        @Override
        public String toString() {
            return id+ " " + title +" " +author;
        }
    }
    // Handles user input, initializes data, and displays the main menu.
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        this.user = new User(name);

        this.library = new Library();
        initialBooks();
        boolean running = true;

        // Main Menu
        while (running) {
            System.out.println("\n===== LIBRARY MENU =====");
            System.out.println("[1] Display All Books");
            System.out.println("[2] Display Available Books");
            System.out.println("[3] Display Borrowed Books");
            System.out.println("[4] Borrow Book");
            System.out.println("[5] Return Book");
            System.out.println("[6] Add Book");
            System.out.println("[7] Remove Book");
            System.out.println("[8] Update Book");
            System.out.println("[0] Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            //Menu Selection
            if (choice == 1) displayAllBooks();
            else if (choice == 2) displayAvailableBooks();
            else if (choice == 3) displayBorrowedBooks();
            else if (choice == 4) borrowBook(scanner);
            else if (choice == 5) returnBook(scanner);
            else if (choice == 6) addBook(scanner);
            else if (choice == 7) removeBook(scanner);
            else if (choice == 8) updateBook(scanner);
            else if (choice == 0) running = false;
            else System.out.println("Invalid selection.");
        }

        System.out.println("Program ended.");
    }
    //initial Books
    public void initialBooks() {
    	listbook.add(new Books(1,"Java Basics", "Author A"));
    	listbook.add(new Books(2,"Advanced Java", "Author B"));
    	listbook.add(new Books(3,"OOP Concepts", "Author C"));
    	listbook.add(new Books(4,"Data Structures", "Author D"));
    	listbook.add(new Books(5,"Algorithms", "Author E"));
       }

    //displaying of books
    public void displayAllBooks() 
    {

        System.out.println("\n--- ALL BOOKS ---");
        System.out.println("Book ID | Title | Author");


        for (Books b : listbook) {
            System.out.println(b.getId() + " | " + b.getTitle() + " | " + b.getAuthor());
        }

        LinkedList<Loan> loans = library.getLoans();
        if (loans != null && !loans.isEmpty()) 
        {
            for (Loan loan : loans) 
            {
                Books borrowedBook = loan.getBook();

                System.out.println(borrowedBook.getId() + " | " + borrowedBook.getTitle() + " | " +
                                   borrowedBook.getAuthor() + " (Borrowed)");
            }
        }

        if (listbook.isEmpty() && (loans == null || loans.isEmpty())) 
        {
            System.out.println("Nothing to display.");
        }
    }

    //displaying of Current Available books
    public void displayAvailableBooks() {
       System.out.println("\n--- AVAILABLE BOOKS ---");
       System.out.println("Book ID | Title | Author");
        boolean found = false;
        for (Books b : listbook) {
        	
                System.out.println(b.getId() + " | " + b.getTitle() + " | " + b.getAuthor());
                found = true;
        }
        if (!found) {
            System.out.println("Nothing to display.");
        }
    }
    // Displaying borrowed books
    private void displayBorrowedBooks() {
        System.out.println("\n--- BORROWED BOOKS ---");
        LinkedList<Loan> loans= library.getLoans();
        if (loans.isEmpty()) {
            System.out.println("Nothing to display.");
            return;
        }

        for (Loan loan : loans) {
            System.out.println(loan.getBook().getTitle() +
                    " borrowed by " + loan.getUser().getName());
        }
    }
    // Borrow a book
    private void borrowBook(Scanner scanner) {
        if (user.getBorrowedCount() >= 5) {
            System.out.println("Nothing to borrow");
            return;
        }

        System.out.println("\n--- BORROW A BOOK ---");
        displayAllBooks();

        System.out.print("Enter Book ID to borrow: ");
        
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid Book ID.");
            scanner.next();
        }
        int id = scanner.nextInt();

        Books bookToBorrow = null;

        for (Books b : listbook) {
            if (b.getId() == id) {
                bookToBorrow = b;
                break;
            }
        }
        if (bookToBorrow != null) {

            library.setBorrowed(true);

            Loan loan = new Loan(user, bookToBorrow);
            library.addLoan(loan);

            user.incrementBorrowed();

            System.out.println("You borrowed: " + bookToBorrow.getTitle());
            
            listbook.remove(bookToBorrow); 
        } else {
            System.out.println("Error: Book ID " + id + " not found or is unavailable.");
        }
    }
    // Return borrowed book
    private void returnBook(Scanner scanner) {
        System.out.println("\n--- RETURN A BOOK ---");

        LinkedList<Loan> currentLoans = library.getLoans();
        if (currentLoans.isEmpty()) {
            System.out.println("Nothing to return.");
            return;
        }
        // Display current loans
        System.out.println("Book ID: | Title: | Author");
        for (Loan loan : currentLoans) {
            System.out.println(loan.getBook().getId() + " | " + loan.getBook().getTitle()+" | "+loan.getBook().getAuthor());
        }

        System.out.print("Select Book ID to return: ");
        int choice = scanner.nextInt();

        Loan loanToRemove = null;
        for (Loan loan : currentLoans) {
            if (loan.getBook().getId() == choice) {
                loanToRemove = loan;
                break;
            }
        }

        if (loanToRemove != null) {
            library.setBorrowed(false);
            
            listbook.add(loanToRemove.getBook());

            library.removeLoan(choice); 
            user.decrementBorrowed();

            System.out.println("Book '" + loanToRemove.getBook().getTitle() + "' returned successfully.");
        } else {
            System.out.println("Invalid selection. You don't have that book on loan.");
        }
    }
    //add book
    public void addBook(Scanner sc) {
        System.out.println("\n--- ADD NEW BOOK ---");

        // 1. Get and Validate ID
        System.out.print("Enter Book ID: ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input. ID must be a number.");
            sc.next();
            return;
        }
        int id = sc.nextInt();
        sc.nextLine();

        for (Books b : listbook) {
            if (b.getId() == id) {
                System.out.println("Book ID " + id + " already exists.");
                return;
            }
        }

        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        if (title.trim().isEmpty()) {
            System.out.println("Book Title cannot be empty.");
            return;
        }

        System.out.print("Enter Author of Book: ");
        String author = sc.nextLine();
        if (author.trim().isEmpty()) {
            System.out.println("Author of Book cannot be empty.");
            return;
        }

        listbook.add(new Books(id, title, author));
        System.out.println("Successfully Added: \nBook ID | Title | Author \n" +id+" | "+title+" | "+author);

    }
    //remove book
	public void removeBook(Scanner sc) {
	    System.out.println("\n--- REMOVE A BOOK ---");
	    displayAllBooks();
	    System.out.print("Enter Book ID to remove: ");
	
	    if (!sc.hasNextInt()) {
	        System.out.println("Invalid input. Please enter a valid Book ID.");
	        sc.next();
	        return;
	    }
	
	    int id = sc.nextInt();
	    sc.nextLine();

	    Books book = null;
	    for (Books b : listbook) {
	        if (b.getId() == id) {
	            book = b;
	            break;
	        }
	    }
	
	    if (book != null) {
	        listbook.remove(book);
	        System.out.println("Book '" + book.getTitle() + "' has been removed from the library (available list).");
	        System.out.println(book.getId() + " | " + book.getTitle() + " | " + book.getAuthor());
	        return;
	    }
	
	    LinkedList<Loan> currentLoans = library.getLoans();
	    Loan loanToRemove = null;
	    for (Loan loan : currentLoans) {
	        if (loan.getBook().getId() == id) {
	            loanToRemove = loan;
	            break;
	        }
	    }
	
	    if (loanToRemove != null) {
	        // Remove the loan record
	        library.removeLoan(id);
	
	        // Decrement the actual borrower's count if tracked
	        User borrower = loanToRemove.getUser();
	        if (borrower != null) {
	            borrower.decrementBorrowed();
	        }
	
	        // Keep your existing borrowed flag behavior consistent
	        library.setBorrowed(false);
	
	        System.out.println("Book '" + loanToRemove.getBook().getTitle() + "' was on loan and has been removed from the library (loan deleted).");
	        System.out.println(loanToRemove.getBook().getId() + " | " +
	                           loanToRemove.getBook().getTitle() + " | " +
	                           loanToRemove.getBook().getAuthor());
	        return;
	    }
	
	    // Case 3: Not found anywhere
	    System.out.println("Book ID " + id + " not found in the library (neither available nor on loan).");
	}

    //update book
    public void updateBook(Scanner sc) {
        System.out.println("\n--- UPDATE BOOK DETAILS ---");
        for (Books b : listbook) {
        	System.out.println(b.getId()+" | "+b.getTitle()+" | "+b.getAuthor());
        }
    	System.out.print("Enter Book ID to update: ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input. ID must be a number.");
            sc.next(); 
            return;
        }
        int id = sc.nextInt();
        sc.nextLine();
        Books book = null;
        for (Books b : listbook) {
        	System.out.println(b.getId()+" | "+b.getTitle()+" | "+b.getAuthor());
            if (b.getId() == id) {
                book = b;
                break;
            }
        }

        if (book == null) {
            System.out.println("Book ID " + id + " not found.");
            return;
        }

        // 3. Update Title (Leave blank to keep current)
        System.out.println("Current Title: " + book.getTitle());
        System.out.print("Enter New Title (or press Enter to keep current): ");
        String newTitle = sc.nextLine();
        
        if (!newTitle.trim().isEmpty()) {
        	book.setTitle(newTitle); // Update only if not blank
        }

        // 4. Update Author (Leave blank to keep current)
        System.out.println("Current Author: " + book.getAuthor());
        System.out.print("Enter New Author (or press Enter to keep current): ");
        String newAuthor = sc.nextLine();
        
        if (!newAuthor.trim().isEmpty()) {
        	book.setAuthor(newAuthor); // Update only if not blank
        }

        System.out.println("\nSuccessfully Updated!");
        System.out.println("New Details: \nBook ID | Title | Author\n" + book.getId() + " | " + 
                           book.getTitle() + " | " + book.getAuthor());
    }
}
