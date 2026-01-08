//package ph.com.bpi.m2project2;
package m3project1;
public class User {
	//Name of User
    private String name;
    // Tracks how many books are borrowed
    private int borrowedCount = 0;

    // Creates User object with entered name
    public User(String name) {
        this.name = name;
    }
    //Returns the User's name
    public String getName() { return name; }
    // Returns how many books are borrowed
    public int getBorrowedCount() { return borrowedCount; }
    //Increment
    public void incrementBorrowed() { borrowedCount++; }
    //Decrement
    public void decrementBorrowed() { borrowedCount--; }
}
