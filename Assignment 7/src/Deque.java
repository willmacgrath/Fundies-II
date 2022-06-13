import tester.Tester;
import java.util.function.Predicate;

class BFinder implements Predicate<String> {
  BFinder() {
  }

  // this checks to see if a node's data starts with the letter b
  public boolean test(String t) {
    return t.substring(0, 1).equals("b");
  }
}

class OneFinder implements Predicate<Integer> {
  OneFinder() {
  }

  // this checks to see if a node's data is the int 1
  public boolean test(Integer data) {
    return data == 1;
  }
}

//Represents either a sentinel or a node
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  abstract int size();

  abstract T breakLink(String where);

  abstract ANode<T> findHelp(Predicate<T> pred);

  abstract void removeNodeHelper(ANode<T> removed);
}

//represents a Node that contains data
class Node<T> extends ANode<T> {
  T data;

  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;

  }

  Node(T data, ANode<T> next, ANode<T> prev) {
    if (next == null || prev == null) {
      throw new IllegalArgumentException("This node does not exist");
    }
    this.next = next;
    this.prev = prev;
    this.data = data;
    this.prev.next = this;
    this.next.prev = this;
  }

  // returns the size of a deque
  int size() {
    return 1 + this.next.size();
  }

  // removes a node from a deque and reconnects the links
  T breakLink(String where) {
    T removed = this.data;
    this.prev.next = this.next;
    this.next.prev = this.prev;
    return removed;
  }

  // works like filter, and goes through the deque until
  // it finds a Node that mathces the predicate
  ANode<T> findHelp(Predicate<T> pred) {
    if (pred.test(this.data)) {
      return this;
    }
    else {
      return this.next.findHelp(pred);
    }
  }

  // removes the Node you tell it to
  void removeNodeHelper(ANode<T> removed) {
    removed.breakLink("word");
  }
}

//represents the sentinel at the start of a deque
class Sentinel<T> extends ANode<T> {
  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  // returns that sentinels have no size
  int size() {
    return 0;
  }

  // adds a new node to the start of the deque
  void addAtHead(T head) {
    new Node<T>(head, this.next, this);
  }

  // adds a new node to the end of the deque
  void addAtTail(T tail) {
    new Node<T>(tail, this, this.prev);
  }

  // throws an exception if the deque is empty,
  // tells it whether to add the new Node at
  // the front or the back
  T breakLink(String where) {
    if (this.next.size() <= 0) {
      throw new RuntimeException("Empty Deque");
    }
    else if (where.equals("Head")) {
      return this.next.breakLink(where);
    }
    else {
      return this.prev.breakLink(where);
    }
  }

  // if the deque is empty, return the sentinel, if not
  // run findHelp() on next
  ANode<T> findHelp(Predicate<T> pred) {
    if (this.next.size() <= 0) {
      return this;
    }
    else {
      return this.next.findHelp(pred);
    }
  }

  // if the deque is empty, return the sentinel, if not
  // run removeNodeHelper on next
  void removeNodeHelper(ANode<T> removed) {
    if (this.next.size() <= 0) {
      return;
    }
    else {
      this.next.removeNodeHelper(removed);
    }
  }
}

//represents a deque that contians nodes and sentinels
class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> sentinel) {
    this.header = sentinel;
  }

  // counts the number of nodes
  int size() {
    return this.header.next.size();
  }

  // Adds a node with T data to the head of the list
  void addAtHead(T head) {
    this.header.addAtHead(head);
  }

  // Adds a node with T data to the tail of the list
  void addAtTail(T tail) {
    this.header.addAtTail(tail);
  }

  // removes a node from the head of the list
  T removeFromHead() {
    return this.header.breakLink("Head");
  }

  // removes a node from the tail of the list
  T removeFromTail() {
    return this.header.breakLink("Tail");
  }

  // finds the first node that satisfies the Predicate
  ANode<T> find(Predicate<T> pred) {
    return this.header.findHelp(pred);
  }

  // removes the given node from it's deque
  void removeNode(ANode<T> removed) {
    this.header.removeNodeHelper(removed);
  }
}

//tests
class ExamplesDeque {
  ExamplesDeque() {
  }

  Sentinel<String> sent1 = new Sentinel<String>();
  Node<String> nodeabc = new Node<String>("abc", sent1, sent1);
  Node<String> nodebcd = new Node<String>("bcd", sent1, nodeabc);
  Node<String> nodecde = new Node<String>("cde", sent1, nodebcd);
  Node<String> nodedef = new Node<String>("def", sent1, nodecde);
  Sentinel<Integer> sent2 = new Sentinel<Integer>();
  Node<Integer> node2 = new Node<Integer>(2, sent2, sent2);
  Node<Integer> node1 = new Node<Integer>(1, sent2, node2);
  Node<Integer> node3 = new Node<Integer>(3, sent2, node1);
  Deque<String> deque1 = new Deque<String>();
  Deque<String> deque2 = new Deque<String>(sent1);
  Deque<Integer> deque3 = new Deque<Integer>(sent2);
  Sentinel<String> sent3 = new Sentinel<String>();

  void initData() {

    sent1 = new Sentinel<String>();
    nodeabc = new Node<String>("abc", sent1, sent1);
    nodebcd = new Node<String>("bcd", sent1, nodeabc);
    nodecde = new Node<String>("cde", sent1, nodebcd);
    nodedef = new Node<String>("def", sent1, nodecde);

    sent2 = new Sentinel<Integer>();
    node2 = new Node<Integer>(2, sent2, sent2);
    node1 = new Node<Integer>(1, sent2, node2);
    node3 = new Node<Integer>(3, sent2, node1);

    sent3 = new Sentinel<String>();

    deque1 = new Deque<String>(sent3);

    deque2 = new Deque<String>(sent1);

    deque3 = new Deque<Integer>(sent2);
  }

  void testSize(Tester t) {
    this.initData();
    t.checkExpect(deque1.size(), 0);
    t.checkExpect(deque2.size(), 4);
    t.checkExpect(deque3.size(), 3);
  }

  void testaddAtHead(Tester t) {
    this.initData();
    deque1.addAtHead("Yuh");
    deque2.addAtHead("please");
    deque3.addAtHead(4);
    t.checkExpect(deque1.header.next, new Node<String>("Yuh", sent3, sent3));
    t.checkExpect(deque2.header.next, new Node<String>("please", nodeabc, sent1));
    t.checkExpect(deque3.header.next, new Node<Integer>(4, node2, sent2));
  }

  void testaddAtTail(Tester t) {
    this.initData();
    deque1.addAtTail("Yuh");
    deque2.addAtTail("please");
    deque3.addAtTail(4);
    t.checkExpect(deque1.header.prev, new Node<String>("Yuh", sent3, sent3));
    t.checkExpect(deque2.header.prev, new Node<String>("please", sent1, nodedef));
    t.checkExpect(deque3.header.prev, new Node<Integer>(4, sent2, node3));
  }

  void testRemoveFromHead(Tester t) {
    this.initData();
    t.checkException(new RuntimeException("Empty Deque"), deque1, "removeFromHead");
    t.checkExpect(deque2.removeFromHead(), "abc");
    t.checkExpect(deque3.removeFromHead(), 2);
    t.checkExpect(sent1.breakLink("Head"), "bcd");
    t.checkExpect(sent2.breakLink("Head"), 1);
  }

  void testRemoveFromTail(Tester t) {
    this.initData();
    t.checkException(new RuntimeException("Empty Deque"), deque1, "removeFromTail");
    t.checkExpect(deque2.removeFromTail(), "def");
    t.checkExpect(deque3.removeFromTail(), 3);
    t.checkExpect(sent1.breakLink("Tail"), "cde");
    t.checkExpect(sent2.breakLink("Tail"), 1);
  }

  void testFind(Tester t) {
    this.initData();
    t.checkExpect(deque1.find(new BFinder()), sent3);
    t.checkExpect(deque2.find(new BFinder()), nodebcd);
    t.checkExpect(deque3.find(new OneFinder()), node1);
  }

  void testRemoveNode(Tester t) {
    this.initData();
    deque1.removeNode(sent3);
    deque2.removeNode(nodebcd);
    deque3.removeNode(node3);
    t.checkExpect(deque1, deque1);
    t.checkExpect(deque2.header.next.next, nodecde);
    t.checkExpect(deque3.header.prev, node1);
  }

  void testBreakLink(Tester t) {
    this.initData();
    nodebcd.breakLink("test");
    node2.breakLink("test");
    t.checkExpect(nodeabc.next, nodecde);
    t.checkExpect(sent2.next, node1);
  }

  void testFindHelp(Tester t) {
    this.initData();
    t.checkExpect(nodeabc.findHelp(new BFinder()), nodebcd);
    t.checkExpect(nodebcd.findHelp(new BFinder()), nodebcd);
  }

  void testRemoveHelper(Tester t) {
    this.initData();
    nodebcd.removeNodeHelper(nodebcd);
    node2.removeNodeHelper(node1);
    t.checkExpect(nodeabc.next, nodecde);
    t.checkExpect(sent2.next, node2);
  }
}
