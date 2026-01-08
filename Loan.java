//package ph.com.bpi.m2project2;
package m3project1;

public class Loan extends LibraryApplication{
	//Name of user who borrowed book
    private User user;
    private Books books;
    private Book book;

    /*
     Creates a Loan object of specific user with a specific book.
     */

    public Loan(User user, Books books) {
        this.user = user;
        this.books = books;
    }
    //Returns the User's name
    public User getUser() { return user; }
    // Returns the book that was borrowed
    public Books getBook() { return books; }
    
    public Book getBook1() { return book; }
}

