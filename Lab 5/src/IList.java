import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.Tester;



interface IList<T> {
  //filter this IList using the given predicate
  IList<T> filter(Predicate<T> pred);

  //map the given function onto every member of this IList
  <U> IList<U> map(Function<T,U> converter);

  //combine the items in this IList using the given function
  <U> U fold(BiFunction<T,U,U> converter,U initial);  
}


class tFinder implements Predicate<String> {
  tFinder() {}

  //this tests each String in the list to return a list starting with only T
  public boolean test(String t) {
    // TODO Auto-generated method stub
    return t.substring(0, 1).equals("T");
  }
}

//
class endWithEr implements BiFunction<String,Integer,Integer> {
  endWithEr() {}

  //counts how many months end with "er" 
  public Integer apply(String t, Integer u) {
    if (t.substring(t.length() - 2, t.length()).equals("er")) {
      return u + 1;
    }
    else {
      return u;
    }
  }
}

class abbreviationer implements Function<String, String> {
  abbreviationer() {}

  // returns only the first three letters of the word
  public String apply(String t) {
    return t.substring(0, 3);
  }
}

class MtList<T> implements IList<T> {
  
  MtList() {}

  //filter this MtList using the given predicate
  public IList<T> filter(Predicate<T> pred) {
    return new MtList<T>();
  }

  //map the given function onto every member of this MtList
  public <U> IList<U> map(Function<T, U> converter) {
    return new MtList<U>();
  }

  //combine the items in this MtList using the given function
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    return initial;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  
  ConsList(T first,IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  //filter this ConsList using the given predicate
  public IList<T> filter(Predicate<T> pred) {
    if (pred.test(this.first)) {
      return new ConsList<T>(this.first,this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  //map the given function onto every member of this ConsList
  public <U> IList<U> map(Function<T, U> converter) {
    return new ConsList<U>(converter.apply(this.first),this.rest.map(converter));
  }

  //combine the items in this ConsList using the given function
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    return converter.apply(this.first, this.rest.fold(converter,initial));
  }
}

class ExamplesLists{
  ExamplesLists() {}
  
  IList<String> months = new ConsList<String>("January",
      new ConsList<String>("February",
          new ConsList<String>("March", 
              new ConsList<String>("April", 
                  new ConsList<String>("May", 
                      new ConsList<String>("June", 
                          new ConsList<String>("July",
                              new ConsList<String>("August",
                                  new ConsList<String>("September",
                                      new ConsList<String>("November",
                                          new ConsList<String>("December", new MtList<String>())))))))))));

  //tests tFinder
  boolean testTFinder(Tester t) {
    return 
        t.checkExpect(this.months.filter(new tFinder()), new MtList<String>());
  }
  
  //tests endWithEr
  boolean testEndWithER(Tester t) {
    return 
        t.checkExpect(this.months.fold(new endWithEr(), 0), 3);
  }
  
  //tests abbreviationer
  boolean testAbbreviator(Tester t) {
    return 
        t.checkExpect(this.months.map(new abbreviationer()), new ConsList<String>("Jan",
            new ConsList<String>("Feb",
                new ConsList<String>("Mar", 
                    new ConsList<String>("Apr", 
                        new ConsList<String>("May", 
                            new ConsList<String>("Jun", 
                                new ConsList<String>("Jul",
                                    new ConsList<String>("Aug",
                                        new ConsList<String>("Sep",
                                            new ConsList<String>("Nov",
                                                new ConsList<String>("Dec", new MtList<String>()))))))))))));
  }
}