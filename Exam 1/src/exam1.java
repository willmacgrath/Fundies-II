import tester.Tester;

//represents a list of strings
interface ILoString {

  // gets the nth String in a list
  String getNth(int n);
}

//represents an empty list of strings
class MtLoString implements ILoString {

  
  /*
  TEMPLATE
  FIELDS:
  
  METHODS
  ... this.getNth(int)...  -- String

  METHODS FOR FIELDS
  */
  
  public String getNth(int n) {
    // TODO Auto-generated method stub
    return "";
  }
}

//represents a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  /*
  TEMPLATE
  FIELDS:
  ... this.first ...       -- String 
  ... this.rest ...        -- ILoString
  
  METHODS
  ... this.getNth(int)...  -- String

  METHODS FOR FIELDS
  */
  
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  public String getNth(int n) {
    if (n == 0) {
      return this.first;
    }
    else {
      return this.rest.getNth(n - 1);
    }
  }
}

//represents a meal order for takeout
class TakeoutOrder {
  OrderCode code;
  ILoString entrees;
  ILoString sides;
  ILoString beverages;

  /*
  TEMPLATE
  FIELDS:
  ... this.code ...        -- OrderCode 
  ... this.entrees ...     -- ILoString
  ... this.sides ...       -- ILoString
  ... this.beverages ...   -- ILoString
  
  METHODS
  ... this.translate()...  -- String

  METHODS FOR FIELDS
  ... this.entrees.getNth ...    -- int
  ... this.sides.getNth ...      -- int
  ... this.beverages.getNth ...  -- int
  
  */
  
  TakeoutOrder(OrderCode code, ILoString entrees, ILoString sides, ILoString beverages) {
    this.code = code;
    this.entrees = entrees;
    this.sides = sides;
    this.beverages = beverages;
  }
  
  //translates a TakeoutOrder to a list of the items in the order
  ILoString translate() {
    return new ConsLoString(this.entrees.getNth(this.code.entreeNum - 1), 
        new ConsLoString(this.sides.getNth(this.code.sideNum - 1), 
            new ConsLoString(this.beverages.getNth(this.code.beverageNum - 1), new MtLoString())));
  }
}

//represents a code that corresponds to meal items in an order 
class OrderCode {
  int entreeNum;
  int sideNum;
  int beverageNum;
  
  /*
  TEMPLATE
  FIELDS:
  ... this.entreeNum ...     -- int
  ... this.sideNum...        -- int
  ... this.beverageNum ...   -- int
  
  METHODS

  METHODS FOR FIELDS
  */

  OrderCode(int entreeNum, int sideNum, int beverageNum) {
    this.entreeNum = entreeNum;
    this.sideNum = sideNum;
    this.beverageNum = beverageNum;
  }
}


class ExamplesOrders {
  ILoString mt = new MtLoString();
  ILoString entrees = new ConsLoString("chicken",
      new ConsLoString("pasta", new ConsLoString("salmon", this.mt)));
  ILoString sides = new ConsLoString("mashed potatoes", new ConsLoString("fries",
      new ConsLoString("broccoli", new ConsLoString("rice pilaf", this.mt))));
  ILoString beverages = new ConsLoString("lemonade", new ConsLoString("iced tea", this.mt));

  //examples of takeout order
  TakeoutOrder o = new TakeoutOrder(new OrderCode(2, 3, 1), this.entrees, this.sides,
      this.beverages);
  TakeoutOrder k = new TakeoutOrder(new OrderCode(3, 0, 2), this.entrees, this.sides,
      this.beverages);
  TakeoutOrder z = new TakeoutOrder(new OrderCode(0, 0, 10000), this.entrees, this.sides,
      this.beverages);
  
  //tests getNth(int)
  boolean testGetNth(Tester t) {
    return t.checkExpect(this.sides.getNth(2), "broccoli")
        && t.checkExpect(this.mt.getNth(1), "")
        && t.checkExpect(this.beverages.getNth(10), "");
  }
  
  //tests translate()
  boolean testTranslate(Tester t) {
    return t.checkExpect(this.o.translate(), new ConsLoString("pasta",
        new ConsLoString("broccoli", new ConsLoString("lemonade", this.mt))))
        && t.checkExpect(this.k.translate(), new ConsLoString("salmon",
            new ConsLoString("", new ConsLoString("iced tea", this.mt))))
        && t.checkExpect(this.z.translate(), new ConsLoString("",
            new ConsLoString("", new ConsLoString("", this.mt))));
  }
}