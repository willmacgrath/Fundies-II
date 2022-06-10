import javalib.worldimages.*;
import tester.Tester;

// makes a posn class that we can make methods for
class MyPosn extends Posn {
  // standard constructor
  MyPosn(int x, int y) {
    super(x, y);
  }
 
  // constructor to convert from a Posn to a MyPosn
  MyPosn(Posn p) {
    this(p.x, p.y);
  }
 
  // adds this MyPosn to that MyPosn
  MyPosn add(MyPosn that) {
    return new MyPosn(this.x + that.x, this.y + that.y);
  }

  // checks if the MyPosn is offscreen
  boolean isOffScreen(int width, int height) {
    return 0 > this.x || this.x > width || 0 > this.y || this.y > height;
  }
 
  // finds the distance between this MyPosn and that MyPosn
  double distanceTo(MyPosn that) {
    return Math.hypot((double)Math.abs(this.x - that.x), (double)Math.abs(this.y - that.y));
  }
 
  // converts velocity depending on index and bullet streak
  MyPosn indexToPosn(int index, int bulletStreak) {
    double angle = Math.toRadians(index * (360.0 / (bulletStreak + 1)));
    double length = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    return new MyPosn((int) (length * Math.cos(angle)),
        (int) (length * Math.sin(angle)));
  }
}

// tests posn methods
class ExamplesMyPosn {
  MyPosn posn1 = new MyPosn(5, 5);
  MyPosn posn2 = new MyPosn(10, 10);
  MyPosn posn3 = new MyPosn(8, 9);
  MyPosn posn4 = new MyPosn(0, -8);
  MyPosn posn5 = new MyPosn(8, 0);
  MyPosn posn6 = new MyPosn(-8, 0);
  MyPosn posn7 = new MyPosn(0, 0);
  MyPosn posn8 = new MyPosn(-4, 4);
 
  // tests add method
  boolean testAdd(Tester t) {
    return t.checkExpect(this.posn1.add(this.posn1), this.posn2)
        && t.checkExpect(this.posn5.add(this.posn6), this.posn7)
        && t.checkExpect(this.posn7.add(this.posn7), this.posn7);
  }

  // tests isOffScreen Method
  boolean testIsOffScreen(Tester t) {
    return t.checkExpect(this.posn1.isOffScreen(500, 500), false)
        && t.checkExpect(this.posn1.isOffScreen(0, 0), true)
        && t.checkExpect(this.posn7.isOffScreen(0, 0), false);
  }

  // tests distanceTo method
  boolean testDistancTo(Tester t) {
    return t.checkExpect(this.posn1.distanceTo(this.posn1), 0.0)
        && t.checkExpect(this.posn1.distanceTo(this.posn3), 5.0)
        && t.checkExpect(this.posn3.distanceTo(this.posn1), 5.0);
  }

  // tests indexToPosn method
  boolean testIndexToPosn(Tester t) {
    return t.checkExpect(this.posn1.indexToPosn(0, 1), this.posn5)
        && t.checkExpect(this.posn1.indexToPosn(1, 1), this.posn6)
        && t.checkExpect(this.posn5.indexToPosn(3, 7), this.posn8);
  }
}