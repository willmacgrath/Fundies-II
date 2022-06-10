
// CS 2510, Assignment 3

import tester.Tester;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();
    
  // searches through a list of Strings for the first word, and replaces 
  // it with the second word if found
  ILoString findAndReplace(String word1, String word2);
    
  // Searches to see if there are any duplicate words in a list of Strings
  boolean anyDupes();
    
  // checks the first word in a list compared to the rest in a list
  boolean anyDupesHelper(String word);
    
  // sorts a list in alphabetical order
  ILoString sort();
    
  // compares the two strings and inserts the word into othe right place
  ILoString sortInsert(String that);
    
  // determines to see if a list is sorted in alphabetical order
  boolean isSorted();
  
  // compares the first string in the list to the given string
  boolean isSortedHelper(String word); 
  
  // combines two lists, interweaving them together
  ILoString interleave(ILoString given);  
  
  // checks to see if a list contains pairs of identical strings
  boolean isDoubledList();
  
  // checks to see if the first and second word are equal
  boolean isDoubledListHelper(String word1);
  
  String reverseConcat();
  
  String reverseConcatAcc(String acc);
  
  ILoString merge(ILoString that);
  
  ILoString mergeHelp(ILoString that, String acc);
  
  boolean sameList(ILoString that);
  
  boolean sameConsList(ConsLoString that);
  
  boolean sameMtList(MtLoString that);
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString(){}
    
  /*
  TEMPLATE
  FIELDS:
  ... this.first ...         -- String
  ... this.rest ...          -- ILoString
  
  METHODS
  ... this.combine() ...     -- String
  ... this.interleave(ILoString) -- ILoString
   ... this.isDoubledList ... -- Boolean
  
  METHODS FOR FIELDS
  ... this.first.concat(String) ...        -- String
  ... this.first.compareTo(String) ...     -- int
  ... this.rest.combine() ...              -- String
  ... this.rest.findAndReplace(String, String) ...  -- ILoString
  ... this.rest.anyDupes() ...               -- boolean
  ... this.rest.anyDupesHelper(String) ... -- boolean
  ... this.rest.sort() ...                 -- ILoString
  ... this.rest.sortInsert(String) ...     -- ILoString
  ... this.rest.isSorted() ...             -- boolean
  ... this.rest.isSortedHelper(String) ... -- boolean
  ... this.rest.isDoubledListHelper(String) -- boolean
  */
   

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  // searches through a list of Strings for the first word, and replaces 
  // it with the second word if found
  public ILoString findAndReplace(String word1, String word2) {
    // TODO Auto-generated method stub
    return this;
  }
    
  // Searches to see if there are any duplicate words in a list of Strings
  public boolean anyDupes() {
    // TODO Auto-generated method stub
    return false;
  }
  
  // checks the first word in a list compared to the rest in a list
  public boolean anyDupesHelper(String word) {
    // TODO Auto-generated method stub
    return false;
  }

  // sorts a list in alphabetical order
  public ILoString sort() {
    // TODO Auto-generated method stub
    return this;
  }

  // compares the two strings and inserts the word into othe right place
  public ILoString sortInsert(String that) {
    // TODO Auto-generated method stub
    return new ConsLoString(that, this);
  }

  //determines to see if a list is sorted in alphabetical order
  public boolean isSorted() {
    // TODO Auto-generated method stub
    return true;
  }

  //compares the first string in the list to the given string
  public boolean isSortedHelper(String word) {
    // TODO Auto-generated method stub
    return false;
  }

  //combines two lists, interweaving them together
  public ILoString interleave(ILoString given) {
    // TODO Auto-generated method stub
    return this;
  }
  
  //checks to see if a list contains pairs of identical strings
  public boolean isDoubledList() {
    // TODO Auto-generated method stub
    return false;
  }

  //checks to see if the first and second word are equal
  public boolean isDoubledListHelper(String word1) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String reverseConcat() {
    // TODO Auto-generated method stub
    return this.reverseConcatAcc("");
  }

  @Override
  public String reverseConcatAcc(String acc) {
    // TODO Auto-generated method stub
    return acc;
  }

  @Override
  public ILoString merge(ILoString that) {
    // TODO Auto-generated method stub
    return that;
  }

  @Override
  public ILoString mergeHelp(ILoString that, String acc) {
    // TODO Auto-generated method stub
    return that;
  }

  @Override
  public boolean sameList(ILoString that) {
    // TODO Auto-generated method stub
    return that.sameMtList(this);
  }
  
  @Override
  public boolean sameConsList(ConsLoString that) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean sameMtList(MtLoString that) {
    // TODO Auto-generated method stub
    return true;
  }   
}

   


// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;
    
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;  
  }
    
  /*
   TEMPLATE
   FIELDS:
   ... this.first ...         -- String
   ... this.rest ...          -- ILoString
   
   METHODS
   ... this.combine() ...     -- String
   ... this.interleave(ILoString) -- ILoString
   ... this.isDoubledList ... -- Boolean
     
   METHODS FOR FIELDS
   ... this.first.concat(String) ...        -- String
   ... this.first.compareTo(String) ...     -- int
   ... this.rest.combine() ...              -- String
   ... this.rest.findAndReplace(String, String) ...  -- ILoString
   ... this.rest.anyDupes() ...               -- boolean
   ... this.rest.anyDupesHelper(String) ... -- boolean
   ... this.rest.sort() ...                 -- ILoString
   ... this.rest.sortInsert(String) ...     -- ILoString
   ... this.rest.isSorted() ...             -- boolean
   ... this.rest.isSortedHealper(String) ... -- boolean
   ... this.rest.isDoubledListHelper(String) -- boolean
   */
    
  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  //searches through a list of Strings for the first word, and replaces 
  // it with the second word if found
  public ILoString findAndReplace(String word1, String word2) {
    // TODO Auto-generated method stub      
    if (this.first.equals(word1)) {
      return new ConsLoString(word2, this.rest.findAndReplace(word1, word2));
    }
    else {
      return new ConsLoString(this.first, this.rest.findAndReplace(word1, word2));
    }
  }

  //Searches to see if there are any duplicate words in a list of Strings
  public boolean anyDupes() {
    // TODO Auto-generated method stub
    return this.rest.anyDupesHelper(this.first) 
        || this.rest.anyDupes();
  }
  
  //checks the first word in a list compared to the rest in a list
  public boolean anyDupesHelper(String word) {
    // TODO Auto-generated method stub
    return this.first.equals(word) 
        || this.rest.anyDupesHelper(word);
  }
  
  // sorts a list in alphabetical order
  public ILoString sort() {
    // TODO Auto-generated method stub
    return this.rest.sort()
        .sortInsert(this.first); // sort the first item in the list
  }

  // compares the two strings and inserts the word into othe right place
  public ILoString sortInsert(String that) {
    // TODO Auto-generated method stub
    if (this.first.toLowerCase().compareTo(that.toLowerCase()) <= 0) {
      return new ConsLoString(this.first, this.rest.sortInsert(that));
    }
    else {
      return new ConsLoString(that, this);
    }
  }

  //determines to see if a list is sorted in alphabetical order
  public boolean isSorted() {
    return this.rest.isSorted() 
        && this.rest.isSortedHelper(first);
  }

  //compares the first string in the list to the given string
  public boolean isSortedHelper(String word) {
    // TODO Auto-generated method stub
    return this.first.toLowerCase().compareTo(word) >= 0;
  }
    
  //combines two lists, interweaving them together
  public ILoString interleave(ILoString given) {
    // TODO Auto-generated method stub
    return new ConsLoString(this.first, given.interleave(this.rest));
  }

  //checks to see if a list contains pairs of identical strings
  public boolean isDoubledList() {
    // TODO Auto-generated method stub
    return this.rest.isDoubledListHelper(this.first);
  }

  //checks to see if the first and second word are equal
  public boolean isDoubledListHelper(String word1) {
    // TODO Auto-generated method stub
    if (this.first.equals(word1)) {
      return this.rest.isDoubledListHelper(word1);
    }
    else {
      return false;
    }
  }

  @Override
  public String reverseConcat() {
    // TODO Auto-generated method stub
    return this.rest.reverseConcatAcc(this.first);
  }

  @Override
  public String reverseConcatAcc(String acc) {
    // TODO Auto-generated method stub
    return this.rest.reverseConcatAcc(acc + this.first);
  }

  @Override
  public ILoString merge(ILoString that) {
    // TODO Auto-generated method stub
    return that.mergeHelp(this.rest, this.first);
  }

  @Override
  public ILoString mergeHelp(ILoString that, String acc) {
    // TODO Auto-generated method stub
   if (this.first.compareTo(acc) < 0) {
     return new ConsLoString(this.first, this.rest.mergeHelp(that, acc));
   }
   else {
     return new ConsLoString(acc, that.mergeHelp(this.rest, this.first));
   }
  }

  @Override
  public boolean sameList(ILoString that) {
    // TODO Auto-generated method stub
    return that.sameConsList(this);
  }

  @Override
  public boolean sameConsList(ConsLoString that) {
    // TODO Auto-generated method stub
    return this.first.equals(that.first) 
        && this.rest.sameList(that.rest);
  }

  @Override
  public boolean sameMtList(MtLoString that) {
    // TODO Auto-generated method stub
    return false;
  }
   
} 


// to represent examples for lists of strings
class ExamplesStrings {
    
  ILoString mary = new ConsLoString("Mary ",
                  new ConsLoString("had ",
                      new ConsLoString("a ",
                          new ConsLoString("little ",
                              new ConsLoString("lamb.", new MtLoString())))));
   
  ILoString maryDupes = new ConsLoString("Mary ",
      new ConsLoString("had ",
          new ConsLoString("a ",
              new ConsLoString("a ",
                new ConsLoString("little ",
                  new ConsLoString("lamb.",
                      new ConsLoString("lamb.", new MtLoString())))))));
  
  ILoString marySorted = new ConsLoString("a ",
        new ConsLoString("had ",
          new ConsLoString("lamb.",
            new ConsLoString("little ",
              new ConsLoString("mary ",new MtLoString())))));
   
  ILoString abc =  new ConsLoString("a ",
        new ConsLoString("b ",
          new ConsLoString("c.",
            new ConsLoString("d ", new MtLoString()))));
  
  ILoString maryDoubled =  new ConsLoString("Mary ",
      new ConsLoString("Mary ",
          new ConsLoString("a ",
              new ConsLoString("a ",
                  new ConsLoString("lamb.", 
                      new ConsLoString("lamb. ", new MtLoString()))))));

    
  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return 
            t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
  }
  
  boolean testFindAndReplace(Tester t) {
    return 
          t.checkExpect(this.mary.findAndReplace("a ", "one "), 
              new ConsLoString("Mary ",
                new ConsLoString("had ",
                  new ConsLoString("one ",
                      new ConsLoString("little ",
                          new ConsLoString("lamb.", new MtLoString()))))));
  }
  
  boolean testAnyDupes(Tester t) {
    return 
          t.checkExpect(this.mary.anyDupes(), false) 
          && t.checkExpect(this.maryDupes.anyDupes(), true) ;
  }
    
  boolean testSort(Tester t) {
    return 
          t.checkExpect(this.mary.sort(), 
              (new ConsLoString("a ",
                new ConsLoString("had ",
                  new ConsLoString("lamb.",
                      new ConsLoString("little ",
                          (new ConsLoString("Mary ",new MtLoString()))))))));
  }
    
  boolean testIsSorted(Tester t) {
    return 
          t.checkExpect(this.mary.isSorted(), false)
          && t.checkExpect(this.marySorted.isSorted(), true);
  }  
    
  boolean testInterleave(Tester t) {
    return t.checkExpect(this.mary.interleave(abc), new ConsLoString("Mary ",
           new ConsLoString("a ",
           new ConsLoString("had ",
               new ConsLoString("b ",
               new ConsLoString("a ",
                       new ConsLoString("c.",
                           new ConsLoString("little ",
                               new ConsLoString("d ",
                                   new ConsLoString("lamb.", new MtLoString()))))))))));
  }
  
  boolean testisDoubledList(Tester t) {
    return t.checkExpect(this.mary.isDoubledList(), false)
        && t.checkExpect(this.maryDoubled.isDoubledList(), true);
  }
}
