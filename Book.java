package m3project1;

public abstract class Book {

	// Unique identifier for the book
	protected Integer id;
    // Title of the book
    protected String title;
    // Author of the book
    protected String author;
    // Indication if the book is borrowed
    protected boolean borrowed;

    /* 
     Constructor to initialize a Book object with ID, title, and author.
   */
    public Book()
    {
    	
    }
    public Book(Integer id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.borrowed = false;
    }
    
    //Getter for book ID
    public Integer getId() 
    { 
    	return id; 
    }
    //Getter for book title
    public String getTitle() 
    { 
    	return title; 
    }
    // Getter for book author
    public String getAuthor() 
    { 
    	return author; 
    }
    // Returns true if the book is borrowed
    public boolean isBorrowed() 
    { 
    	return borrowed; 
    }
    
  //Setter for book ID
    public void setId(Integer id) 
    { 
    	this.id=id; 
    }
    //Setter for book title
    public void setTitle(String title) 
    { 
    	this.title=title; 
    }
    //Setter for book author
    public void setAuthor(String author) 
    { 
    	this.author=author; 
    }
    // status of the book (true = borrowed, false = available)
    public void setBorrowed(boolean borrowed) 
    { 
    	this.borrowed = borrowed; 
    }
}

