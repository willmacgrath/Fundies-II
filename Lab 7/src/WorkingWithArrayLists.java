import tester.Tester;
import java.util.ArrayList;
import java.util.function.Predicate;


class evens implements Predicate<Integer>{
  public boolean test(Integer n) {
    return n % 2 == 0;
  }
}

class endsWithO implements Predicate<String>{
  public boolean test(String s) {
    return s.endsWith("o");
  }
}

class Util {
  Util(){}
  
  //Calls filter or filterNot depending on given boolean
  <T> ArrayList<T> customFilter(ArrayList<T> arr, Predicate<T> pred, boolean keepPassing) {
    if (keepPassing == true) {
      return filter(arr, pred);    
    } else {
      return filterNot(arr, pred);
      }
    }
   
  //Filters the Arr based on predicate
  <T> ArrayList<T> filter(ArrayList<T> arr, Predicate<T> pred) {
    ArrayList<T> result = new ArrayList<T>();
    for ( T elem : arr ) {
      if (pred.test(elem)) {
        result.add(elem);
        }         
      }
    return result;
  }

  //Filters the Arr based on it not passing the predicate
  <T> ArrayList<T> filterNot(ArrayList<T> arr, Predicate<T> pred) {
    ArrayList<T> result = new ArrayList<T>();
    for ( T elem : arr ) {
      if (!pred.test(elem)) {
        result.add(elem);
      }           
      }
    return result;
    }
  
  //Calls remove failing or remove passing based on boolean
  <T> void customRemove(ArrayList<T> arr, Predicate<T> pred, boolean keepPassing) {
    if (keepPassing == true) {
      removePassing(arr, pred);    
    } else {
      removeFailing(arr, pred);
      }
    }
  
  //modifies the given list to remove everything that passes the predicate.
  <T> void removeFailing(ArrayList<T> arr, Predicate<T> pred) {
     for( int i = 0; i < arr.size(); i++ ) {
       if (!pred.test(arr.get(i))) {
         arr.remove(i);
         i --;
       }
     } 
  }
  
  //modifies the given list to remove everything that fails the predicate.
  <T> void removePassing(ArrayList<T> arr, Predicate<T> pred) {
    for( int i = 0; i < arr.size(); i++ ) {
      if (pred.test(arr.get(i))) {
        arr.remove(i);
        i --;
      }
    } 
 }
  
  <T> ArrayList<T> interweaveV1(ArrayList<T> arr1, ArrayList<T> arr2) {
    ArrayList<T> result = new ArrayList<T>();
    
    for (int i = 0; i < Math.max(arr1.size(), arr2.size()); i++) {
       if (i < arr2.size() && i < arr1.size()) {
        result.add(arr1.get(i));
        result.add(arr2.get(i));  
        
      } if (i == arr1.size()) {
        arr2.removeAll(result);
        result.addAll(arr2);
      } if (i == arr2.size()) {
        arr1.removeAll(result);
        result.addAll(arr1);
      }
    }
      return result;
    }
 
  <T> ArrayList <T> interweaveV2(ArrayList<T> arr1, ArrayList<T> arr2, int getFrom1, int getFrom2) {
    ArrayList<T> result = new ArrayList<T>();
    int counterArr1 = 0;
    int counterArr2 = 0;
    
    for (int i = 0; i < Math.max(arr1.size(), arr2.size()); i++) {
      for (int x = counterArr1; x < counterArr1 + getFrom1; x++) {
        if ( arr1.size() - 1 >= x ) {
          result.add(arr1.get(x));
        }
      } counterArr1 = counterArr1 + getFrom1;
      
      for (int y = counterArr2; y < counterArr2 + getFrom2; y++) {
        if ( arr2.size() - 1 >= y ) {
          result.add(arr2.get(y));
        }
      } counterArr2 = counterArr2 + getFrom2; 
    } 
      return result;
}
  
  <T> ArrayList<T> interweaveV3(ArrayList<T> arr1, ArrayList<T> arr2) {
    return interweaveV2(arr1, arr2, 1, 1);
    }
  }


class ExamplesArrayList {
  ExamplesArrayList(){}
  
  void testInterweaveV3(Tester t) {
    ArrayList<String> stringList1 = new ArrayList<String>();
    ArrayList<String> stringList2 = new ArrayList<String>();
    ArrayList<String> expected = new ArrayList<String>();
    Util util = new Util();
    stringList1.add("Hi");
    stringList1.add("I");
    stringList1.add("Am");
    stringList2.add("Bob");
    stringList2.add("the");
    stringList2.add("Man");

    
    expected.add("Hi");
    expected.add("Bob");
    expected.add("I");
    expected.add("the");
    expected.add("Am");
    expected.add("Man");
    
    t.checkExpect(util.interweaveV3(stringList1, stringList2), expected);
  }
  
  
  
  void testInterweaveV2(Tester t) {
    ArrayList<String> stringList1 = new ArrayList<String>();
    ArrayList<String> stringList2 = new ArrayList<String>();
    ArrayList<String> expected = new ArrayList<String>();
    ArrayList<String> expected2 = new ArrayList<String>();
    Util util = new Util();
    stringList1.add("Hi");
    stringList1.add("I");
    stringList1.add("Am");
    
    stringList2.add("Bob");
    stringList2.add("the");
    stringList2.add("Man");

    expected.add("Hi");
    expected.add("I");
    expected.add("Am");
    expected.add("Bob");
    expected.add("the");
    expected.add("Man");
    
    t.checkExpect(util.interweaveV2(stringList1, stringList2, 3, 3), expected);
    
    stringList2.add("And");
    stringList2.add("I");
    stringList2.add("Like");
    stringList2.add("Cake");
    
    expected2.add("Hi");
    expected2.add("I");
    expected2.add("Bob");
    expected2.add("the");
    expected2.add("Man");
    expected2.add("Am");
    expected2.add("And");
    expected2.add("I");
    expected2.add("Like");
    expected2.add("Cake");
    
    t.checkExpect(util.interweaveV2(stringList1, stringList2, 2, 3), expected2);  
  }
  
  void testInterweave(Tester t) {
    ArrayList<String> stringList1 = new ArrayList<String>();
    ArrayList<String> stringList2 = new ArrayList<String>();
    ArrayList<String> expected = new ArrayList<String>();
    Util util = new Util();
    stringList1.add("Red");
    stringList1.add("High");
    stringList1.add("PLS");
    stringList1.add("Yes?");
    stringList2.add("Blue");
    stringList2.add("Low");
   
    expected.add("Red");
    expected.add("Blue");
    expected.add("High");
    expected.add("Low");
    expected.add("PLS");
    expected.add("Yes?");
    
    t.checkExpect(util.interweaveV1(stringList1, stringList2), expected);
  }
  
  void testRemovePassFail(Tester t) {
    ArrayList<String> stringList = new ArrayList<String>();
    Predicate<String> endsWithO = new endsWithO();
    Util util = new Util();
    stringList.add("Bob");
    stringList.add("Rob");
    stringList.add("Cob");
    stringList.add("Boo");
    stringList.add("Mob");
   
    ArrayList<String> result = new ArrayList<String>();
    result.add("Bob");
    result.add("Rob");
    result.add("Cob");
    result.add("Mob");
    
    util.removePassing(stringList, endsWithO);
    t.checkExpect(stringList.equals(result), true);
    
    util.removeFailing(stringList, endsWithO);
    ArrayList<String> empty = new ArrayList<String>();
    t.checkExpect(stringList.equals(empty), true);
    }
  
  void testCustomRemove(Tester t) {
    ArrayList<String> stringList = new ArrayList<String>();
    Predicate<String> endsWithO = new endsWithO();
    Util util = new Util();
    stringList.add("Bob");
    stringList.add("Rob");
    stringList.add("Cob");
    stringList.add("Boo");
    stringList.add("Mob");
   
    ArrayList<String> result = new ArrayList<String>();
    result.add("Bob");
    result.add("Rob");
    result.add("Cob");
    result.add("Mob");
    
    util.customRemove(stringList, endsWithO, true);
    t.checkExpect(stringList.equals(result), true);
    
  }
  
  void testArrayFilter(Tester t) {
    ArrayList<Integer> intList = new ArrayList<Integer>();
    Predicate<Integer> evens = new evens();
    Util util = new Util();
    intList.add(1);
    intList.add(2);
    intList.add(3);
    intList.add(4);
    intList.add(5);
    intList.add(6);
    
    ArrayList<Integer> result = util.filter(intList, evens);
    t.checkExpect(result.size(), 3);
    t.checkExpect(result.get(1), 4);
    }
  
  void testArrayFilterNot(Tester t) {
    ArrayList<Integer> intList = new ArrayList<Integer>();
    Predicate<Integer> evens = new evens();
    Util util = new Util();
    intList.add(1);
    intList.add(2);
    intList.add(3);
    intList.add(4);
    intList.add(5);
    intList.add(6);
    intList.add(7);
    
    ArrayList<Integer> result = util.filterNot(intList, evens);
    t.checkExpect(result.size(), 4);
    t.checkExpect(result.get(1), 3);
    t.checkExpect(result.get(3), 7);
    }
  
  void testArrayCustomFilter(Tester t) {
    ArrayList<Integer> intList = new ArrayList<Integer>();
    Predicate<Integer> evens = new evens();
    Util util = new Util();
    intList.add(1);
    intList.add(2);
    intList.add(3);
    intList.add(4);
    intList.add(5);
    intList.add(6);
    intList.add(7);
    
    ArrayList<Integer> result = util.customFilter(intList, evens, false);
    t.checkExpect(result.size(), 4);
    t.checkExpect(result.get(1), 3);
    t.checkExpect(result.get(3), 7);
    
    ArrayList<Integer> result2 = util.customFilter(intList, evens, true);
    t.checkExpect(result2.size(), 3);
    t.checkExpect(result2.get(1), 4);
    t.checkExpect(result2.get(2), 6);  
    }
}
