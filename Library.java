//package ph.com.bpi.m2project2;
package m3project1;

import java.util.LinkedList;
public class Library extends Book{

	private LinkedList<Loan> loans = new LinkedList<>();
	public Library()
	{
		
	}
	public Library(Integer id, String title, String author)
	{
		super();
		this.setId(id);
		this.setTitle(title);
		this.setAuthor(author);
		this.isBorrowed();
	}

    public LinkedList<Loan> getLoans() { 
        return loans; 
    }
    public void removeLoan(int id) {
        // This removes any loan where the book's ID matches the input
        loans.removeIf(loan -> loan.getBook().getId() == id);
    }
    public void addLoan(Loan loan) {
        loans.add(loan); // Automatically adds to the end of the list
    }

}
