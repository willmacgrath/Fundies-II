import tester.Tester;

interface IBook {
  
  // computes the amount of days overdue
  int daysOverdue(int currentDay);
  
  // determines if a IBook is overdue
  boolean isOverdue(int currentDay);
  
  // calculates the fine for a overdue book
  double computeFine(int currentDay);
}

// to represent an item to be taken out of the library
abstract class ABook implements IBook {
  String title;
  int dayTaken;
  
  /*
  TEMPLATE
  FIELDS:
  ... this.title ...         -- String
  ... this.dayTaken ...      -- int
  
  METHODS
  ... daysOverdue(int) ...   -- int
  ... isOverdue(int) ...     -- boolean
  ... computeFine(int) ...   -- double


  METHODS FOR FIELDS
  */
  
  ABook(String title, int dayTaken) {
    this.title = title;
    this.dayTaken = dayTaken;
  }

  // computes the amount of days overdue
  public int daysOverdue(int currentDay) {
    return currentDay - (dayTaken + 14);
  }  
  
  // determines if a IBook is overdue
  public boolean isOverdue(int currentDay) {
    // TODO Auto-generated method stub
    return this.daysOverdue(currentDay) > 0;
  }
  
  //calculates the fine for a overdue book
  public double computeFine(int currentDay) {
    // TODO Auto-generated method stub
    if (this.isOverdue(currentDay)) {
      return this.daysOverdue(currentDay) * .10;
    }
    else {
      return 0;
    }
  }  
}

// to represent a physical book
class Book extends ABook {
  String author;
  
  /*
  TEMPLATE
  FIELDS:
  ... this.title ...         -- String
  ... this.author ...        -- String
  ... this.dayTaken ...      -- int
  
  METHODS
  ... daysOverdue(int) ...   -- int
  ... isOverdue(int) ...     -- boolean
  ... computeFine(int) ...   -- double

  METHODS FOR FIELDS
  */
  
  
  Book(String title, String author, int dayTaken) {
    super(title, dayTaken);
    this.author = author;
  }  
}

// to represent a reference book
class refBook extends ABook {
  String author;
  
  /*
  TEMPLATE
  FIELDS:
  ... this.title ...         -- String
  ... this.dayTaken ...      -- int
  
  METHODS
  ... daysOverdue(int) ...   -- int
  ... isOverdue(int) ...     -- boolean
  ... computeFine(int) ...   -- double

  METHODS FOR FIELDS
  */
  
  refBook(String title, int dayTaken) {
    super(title, dayTaken);    
  }

  //computes the amount of days overdue
  public int daysOverdue(int currentDay) {
    // TODO Auto-generated method stub
    return currentDay - (dayTaken + 2);
  }
}

// to represent an audiobook
class audioBook extends ABook {
  String author;
  
  /*
  TEMPLATE
  FIELDS:
  ... this.title ...         -- String
  ... this.author ...        -- String
  ... this.dayTaken ...      -- int
  
  METHODS

  METHODS FOR FIELDS
  */
  
  audioBook(String title, String author, int dayTaken) {
    super(title, dayTaken);
    this.author = author;
  }

  //calculates the fine for a overdue book
  public double computeFine(int currentDay) {
    // TODO Auto-generated method stub
    if (this.isOverdue(currentDay)) {
      return this.daysOverdue(currentDay) * .20;
    }
    else {
      return 0;
    }  
  }
}

class ExamplesBook {
  ExamplesBook() {
  }
    IBook hp1 = new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 7808);
    IBook hp2 = new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", 7800);
    IBook hp3 = new Book("Harry Potter and  the Prisoner of Azkaban", "J.K. Rowling", 6808);
    IBook dictionary = new refBook("Merriam Webster's Dictionary", 7805);
    IBook encyclopedia = new refBook("Harry Potter and the Sorcerer's Stone", 7811);
    IBook textbook = new refBook("Harry Potter and the Sorcerer's Stone", 7812);
    IBook dwk1= new audioBook("Diary of a Wimpy Kid", "Jeff Kinney", 7799);
    IBook dwk2 = new audioBook("Diary of a Wimpy Kid: Rodrick Rules", "Jeff Kinney", 7812);
    IBook dwk3 = new audioBook("Diary of a Wimpy Kid: The Last Straw", "Jeff Kinney", 7780);
  
    boolean testDaysOverdue(Tester t) {
      return 
          t.checkExpect(this.hp2.daysOverdue(7813), -1)
          && t.checkExpect(this.encyclopedia.daysOverdue(7813), 0)
          && t.checkExpect(this.dwk3.daysOverdue(7813), 19);
  }
    boolean testIsOverdue(Tester t) {
      return 
          t.checkExpect(this.hp2.isOverdue(7813), false)
          && t.checkExpect(this.encyclopedia.isOverdue(7813), false)
          && t.checkExpect(this.dwk3.isOverdue(7813), true);
  }
    boolean testComputeFine(Tester t) {
      return 
          t.checkExpect(this.hp2.computeFine(7813), 0.0)
          && t.checkExpect(this.encyclopedia.computeFine(7813), 0.0)
          && t.checkInexact(this.dwk3.computeFine(7813), 3.8, 0.01)
          && t.checkInexact(this.hp3.computeFine(7813), 99.1, 0.01);
  }
}






