import tester.Tester;

//represents a ternary tree
interface ITree {

  // finds whether the tree contains a given value
  boolean contains(String val);

  // checks a single node for a given value
  boolean containsHelp(String val);

  // returns all of the left tree values in a list
  ILoString allLeft();

  //returns the value of the ITree
  String allLeftVal();
}

//represents a leaf in a ternary tree
class Leaf implements ITree {

  public boolean contains(String val) {
    return false;
  }

  public boolean containsHelp(String val) {
    return false;
  }

  public ILoString allLeft() {
    // TODO Auto-generated method stub
    return new MtLoString();
  }

  
  public String allLeftVal() {
    // TODO Auto-generated method stub
    return "";
  }
}

//represents a node in a ternary tree
class Node implements ITree {
  String val;
  ITree left;
  ITree mid;
  ITree right;

  Node(String val, ITree left, ITree mid, ITree right) {
    this.val = val;
    this.left = left;
    this.mid = mid;
    this.right = right;
  }

  public boolean contains(String val) {
    return this.containsHelp(val) || this.left.contains(val) || this.mid.contains(val)
        || this.right.contains(val);
  }

  public boolean containsHelp(String val) {
    return this.val.equals(val);
  }

  //I am leaving a comment to say I know this is wrong, and I actually got the first test to run, but I couldn't recur
  //it without everything breaking
  //
  public ILoString allLeft() {
    return new ConsLoString(this.left.allLeftVal(), 
        new ConsLoString(this.mid.allLeftVal(), new ConsLoString( this.right.allLeftVal(), new MtLoString())));
  }

  public String allLeftVal() {
    return this.val;
  }
}

class ExamplesTrees {
  // here are some examples you can use
  ITree lf = new Leaf();
  ITree tree1 = new Node("a1", lf, lf, lf);
  ITree tree2 = new Node("b1", lf, lf, lf);
  ITree tree3 = new Node("c1", lf, lf, lf);
  ITree tree4 = new Node("a2", lf, lf, lf);
  ITree tree5 = new Node("b2", lf, lf, lf);
  ITree tree6 = new Node("c2", lf, lf, lf);
  ITree tree7 = new Node("a3", lf, lf, lf);
  ITree tree8 = new Node("b3", lf, lf, lf);
  ITree tree9 = new Node("c3", lf, lf, lf);
  ITree tree10 = new Node("a4", tree1, tree2, tree3);
  ITree tree11 = new Node("b4", tree4, tree5, tree6);
  ITree tree12 = new Node("c4", tree7, tree8, tree9);
  ITree tree13 = new Node("a5", tree10, tree11, tree12);

  // tests contains(String)
  boolean testContains(Tester t) {
    return t.checkExpect(this.tree1.contains("a1"), true)
        && t.checkExpect(this.tree13.contains("a3"), true)
        && t.checkExpect(this.tree11.contains("c4"), false)
        && t.checkExpect(this.lf.contains("c4"), false);
  }

  // tests allLeft()
  boolean testAllLeft(Tester t) {
    return t.checkExpect(this.tree1.allLeft(), new ConsLoString("", new MtLoString()))
        && t.checkExpect(this.tree13.allLeft(),
            new ConsLoString("a4",
                new ConsLoString("a1",
                    new ConsLoString("a2", new ConsLoString("a3", new MtLoString())))))
        && t.checkExpect(this.lf.allLeft(), new ConsLoString("", new MtLoString()));
  }
}