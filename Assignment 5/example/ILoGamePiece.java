import tester.Tester;
import javalib.funworld.*;

interface ILoGamePiece {
  // filters a list for the given pred
  ILoGamePiece filter(IPredX<IGamePiece> pred);
  
  // removes all game pieces that are off screen
  ILoGamePiece removeOffScreen();
  
  // determines the length of the list
  int length();
  
  // Removes elements from both lists that have collided
  ILoGamePiece removeCollided(ILoGamePiece that);
  
  // Helper that removes elements from both lists that have collided
  ILoGamePiece removeCollidedHelper(IGamePiece first, ILoGamePiece rest);
  
  // removes elements from the list that are collided with the game piece
  ILoGamePiece removeGamePiece(IGamePiece gamepiece);
  
  // returns a list of the collided game pieces
  ILoGamePiece returnCollided(ILoGamePiece that);
  
  // returns a list of the collided game pieces
  ILoGamePiece returnCollidedHelper(IGamePiece first, ILoGamePiece rest);
  
  // returns elements from the list that are collided with the game piece
  ILoGamePiece returnGamePiece(IGamePiece gamepiece);
  
  // adds the given the list to this list
  ILoGamePiece appendList(ILoGamePiece that);
  
  // places all the elements of the list on World Scene
  WorldScene placeAll(WorldScene ws);
  
  // calls move on all game pieces
  ILoGamePiece moveAll();
  
  // explodes all the bullets that are collided
  ILoGamePiece bulletExplodeAll();
  
}


class MtLoGamePiece implements ILoGamePiece {

  // filters out items for the given predicate
  public ILoGamePiece filter(IPredX<IGamePiece> pred) {
    return this;
  }

  // removes items that are off screen
  public ILoGamePiece removeOffScreen() {
    return this;
  }

  // returns the length of the list
  public int length() {
    return 0;
  }

  // Removes elements from both lists that have collided
  public ILoGamePiece removeCollided(ILoGamePiece list) {
    return this;
  }

  // Helper that removes elements from both lists that have collided
  public ILoGamePiece removeCollidedHelper(IGamePiece first, ILoGamePiece rest) {
    return new ConsLoGamePiece(first, rest);
  }

  // removes elements from the list that are collided with the game piece
  public ILoGamePiece removeGamePiece(IGamePiece gamepiece) {
    return this;
  }

  // returns a list of the collided game pieces
  public ILoGamePiece returnCollided(ILoGamePiece that) {
    return this;
  }

  // returns a list of the collided game pieces
  public ILoGamePiece returnCollidedHelper(IGamePiece first,ILoGamePiece rest) {
    return this;
  }

  //returns elements from the list that are collided with the game piece
  public ILoGamePiece returnGamePiece(IGamePiece gamepiece) {
    return this;
  }

  // adds the given the list to this list
  public ILoGamePiece appendList(ILoGamePiece that) {
    return that;
  }

  // places all the elements of the list on World Scene
  public WorldScene placeAll(WorldScene ws) {
    return ws;
  }

  // moves all of the elements in the list
  public ILoGamePiece moveAll() {
    return this;
  }

  // explodes all the bullets that are collided
  public ILoGamePiece bulletExplodeAll() {
    return this;
  }
  
  

}

class ConsLoGamePiece implements ILoGamePiece {
  IGamePiece first;
  ILoGamePiece rest;
  
  ConsLoGamePiece(IGamePiece first, ILoGamePiece rest) {
    this.first = first;
    this.rest = rest;
  }

  //filters out items for the given predicate
  public ILoGamePiece filter(IPredX<IGamePiece> pred) {
    if (!pred.apply(this.first)) {
      return new ConsLoGamePiece(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  // removes items offscreen
  public ILoGamePiece removeOffScreen() {
    return this.filter(new IsOffScreen());
  }

  // returns the length of the list
  public int length() {
    return 1 + this.rest.length();
  }

  // Removes elements from both lists that have collided
  public ILoGamePiece removeCollided(ILoGamePiece that) {
    return that.removeCollidedHelper(this.first, this.rest);

  }

  // Helper that removes elements from both lists that have collided
  public ILoGamePiece removeCollidedHelper(IGamePiece first, ILoGamePiece rest) {
    return new ConsLoGamePiece(first, rest).removeGamePiece(this.first).removeCollided(this.rest);
  }

  // removes elements from the list that are collided with the game piece
  public ILoGamePiece removeGamePiece(IGamePiece gamepiece) {
    if (this.first.collided(gamepiece)) {
      return this.rest.removeGamePiece(gamepiece);
    }
    else {
      return new ConsLoGamePiece(this.first, this.rest.removeGamePiece(gamepiece));
    }
  }

  // returns the list of items that collided with given
  public ILoGamePiece returnCollided(ILoGamePiece that) {
    return that.returnCollidedHelper(this.first, this.rest);
  }

  // helper that returns the list of items that collided with given
  public ILoGamePiece returnCollidedHelper(IGamePiece first, ILoGamePiece rest) {
    return new ConsLoGamePiece(first,
        rest).returnGamePiece(this.first).appendList(new ConsLoGamePiece(first, 
            rest).returnCollided(this.rest));
  }

  // adds the given the list to this list
  public ILoGamePiece returnGamePiece(IGamePiece gamepiece) {
    if (this.first.collided(gamepiece)) {
      return new ConsLoGamePiece(this.first, this.rest.returnGamePiece(gamepiece));
    }
    else {
      return this.rest.returnGamePiece(gamepiece);
    }
  }

  // appends the two lists together
  public ILoGamePiece appendList(ILoGamePiece that) {
    return new ConsLoGamePiece(this.first, this.rest.appendList(that));
  }

  // places all the elements of the list on World Scene
  public WorldScene placeAll(WorldScene ws) {
    return this.first.place(this.rest.placeAll(ws));
  }

  // moves all elements
  public ILoGamePiece moveAll() {
    return new ConsLoGamePiece(this.first.move(), this.rest.moveAll());
  }

  // explodes all the bullets that are collided
  public ILoGamePiece bulletExplodeAll() {
    return this.first.bulletExplode().appendList(this.rest.bulletExplodeAll());
  }
  
}

class ExamplesILoGamePiece {
  
  ILoGamePiece empty = new MtLoGamePiece();
  
  IGamePiece bullet1 = new Bullet(new MyPosn(200, 200), new MyPosn(0, -1));
  IGamePiece bullet2 = new Bullet(new MyPosn(400, 200), new MyPosn(0, -1));
  IGamePiece bullet3 = new Bullet(new MyPosn(200, 600), new MyPosn(0, -1));
  
  IGamePiece bullet1m = new Bullet(new MyPosn(200, 199), new MyPosn(0, -1));
  IGamePiece bullet2m = new Bullet(new MyPosn(400, 199), new MyPosn(0, -1));
  IGamePiece bullet3m = new Bullet(new MyPosn(200, 599), new MyPosn(0, -1));
  
  IGamePiece ship1 = new Ship(new MyPosn(200, 200), new MyPosn(5, 0));
  IGamePiece ship2 = new Ship(new MyPosn(-100, 300), new MyPosn(5, 0));
  IGamePiece ship3 = new Ship(new MyPosn(10, 200), new MyPosn(5, 0));
  
  IGamePiece shipOutsider = new Ship(new MyPosn(500, 500), new MyPosn(5, 0));
  
  ILoGamePiece collideList = new ConsLoGamePiece(new Bullet(new MyPosn(200, 200),
      new MyPosn(0, -1)), this.empty);
  
  ILoGamePiece bullet1List = new ConsLoGamePiece(this.bullet1, this.empty);
  
  ILoGamePiece removeList = new ConsLoGamePiece(new Bullet(new MyPosn(400, 200),
      new MyPosn(0, -1)), 
      new ConsLoGamePiece(new Bullet(new MyPosn(200, 600), new MyPosn(0, -1)),
          this.empty));
  
  ILoGamePiece shipList = new ConsLoGamePiece(this.ship1,
      new ConsLoGamePiece(this.ship2,
          new ConsLoGamePiece(this.ship3, this.empty)));
  
  ILoGamePiece shipListRest = new ConsLoGamePiece(this.ship2,
          new ConsLoGamePiece(this.ship3, this.empty));
  
  ILoGamePiece shipListFinal = new ConsLoGamePiece(this.ship2,
      new ConsLoGamePiece(this.ship3, this.empty));
  
  ILoGamePiece bulletList = new ConsLoGamePiece(this.bullet1,
      new ConsLoGamePiece(this.bullet2, 
          new ConsLoGamePiece(this.bullet3, this.empty)));
  
  ILoGamePiece bulletListMove = new ConsLoGamePiece(this.bullet1m,
      new ConsLoGamePiece(this.bullet2m, 
          new ConsLoGamePiece(this.bullet3m, this.empty)));
  
  ILoGamePiece bulletListRest = new ConsLoGamePiece(this.bullet2, 
          new ConsLoGamePiece(this.bullet3, this.empty));
  
  ILoGamePiece bulletList2 = new ConsLoGamePiece(this.bullet1,
      new ConsLoGamePiece(this.bullet2, this.empty));
  
  ILoGamePiece combineList = new ConsLoGamePiece(this.bullet1,
      new ConsLoGamePiece(this.bullet2, 
          new ConsLoGamePiece(this.bullet3, 
              new ConsLoGamePiece(this.ship1,
              new ConsLoGamePiece(this.ship2,
                  new ConsLoGamePiece(this.ship3, this.empty))))));
  
  // tests filter
  boolean testFilter(Tester t) {
    return t.checkExpect(this.bulletList.filter(new IsOffScreen()), this.bulletList2)
        && t.checkExpect(this.bulletList2.filter(new IsOffScreen()), this.bulletList2)
        && t.checkExpect(this.empty.filter(new IsOffScreen()), this.empty);
  }
  
  // tests remove offscreen method
  boolean testRemoveOffScreen(Tester t) {
    return t.checkExpect(this.bulletList.removeOffScreen(), this.bulletList2)
        && t.checkExpect(this.bulletList2.removeOffScreen(), this.bulletList2)
        && t.checkExpect(this.empty.removeOffScreen(), this.empty);
  }
  
  // tests length method
  boolean testLength(Tester t) {
    return t.checkExpect(bulletList.length(), 3) &&
        t.checkExpect(this.empty.length(), 0) &&
        t.checkExpect(bulletList2.length(), 2);
  }
  
  // tests remove collided method
  boolean testRemoveCollided(Tester t) {
    return t.checkExpect(this.bulletList.removeCollided(this.bulletList), this.empty) &&
        t.checkExpect(this.bulletList.removeCollided(this.shipList), this.removeList) &&
        t.checkExpect(this.shipList.removeCollided(this.bulletList), this.shipListFinal);
  }
  
  // tests remove collided helper method
  boolean testRemoveCollidedHelper(Tester t) {
    return t.checkExpect(this.bulletList.removeCollidedHelper(this.bullet1,
        this.bulletListRest), this.empty)
        && t.checkExpect(this.shipList.removeCollidedHelper(this.bullet1,
            this.bulletListRest), this.removeList)
        && t.checkExpect(this.bulletList.removeCollidedHelper(this.ship1,
            this.shipListRest), this.shipListFinal);
  }
  
  // tests remove game piece method
  boolean testRemoveGamePiece(Tester t) {
    return t.checkExpect(this.bulletList.removeGamePiece(this.bullet1),
        this.bulletListRest) &&
        t.checkExpect(this.bulletList.removeGamePiece(this.ship1), 
            this.bulletListRest) &&
        t.checkExpect(this.bulletList.removeGamePiece(this.shipOutsider), 
            this.bulletList) &&
        t.checkExpect(this.empty.removeGamePiece(this.ship1), this.empty);
  }
  
  // tests return collided method
  boolean testReturnCollided(Tester t) {
    return t.checkExpect(this.bulletList.returnCollided(this.bulletList), this.bulletList)
        && t.checkExpect(this.bulletList.returnCollided(this.shipList), this.collideList)
        && t.checkExpect(this.bulletList.returnCollided(this.empty), this.empty)
        && t.checkExpect(this.empty.returnCollided(this.empty), this.empty)
        && t.checkExpect(this.empty.returnCollided(this.bulletList), this.empty);
  }
  
  // tests return collided helper method
  boolean testReturnCollidedHelper(Tester t) {
    return t.checkExpect(this.bulletList.returnCollidedHelper(this.bullet1,
        this.bulletListRest),this.bulletList)
        && t.checkExpect(this.shipList.returnCollidedHelper(this.bullet1,
            this.bulletListRest), this.collideList)
        && t.checkExpect(this.empty.returnCollidedHelper(this.ship1,
            this.shipListRest), this.empty);
  }
 
  // tests returnGamePiece method
  boolean testReturnGamePiece(Tester t) {
    return t.checkExpect(this.bulletList.returnGamePiece(this.bullet1), this.bullet1List) &&
        t.checkExpect(this.bulletList.returnGamePiece(this.shipOutsider), this.empty) &&
        t.checkExpect(this.empty.returnGamePiece(this.bullet1), this.empty);
  }
  
  // tests the appendList method
  boolean testAppendList(Tester t) {
    return t.checkExpect(this.bulletList.appendList(this.shipList), this.combineList) &&
        t.checkExpect(this.empty.appendList(this.shipList), this.shipList) &&
        t.checkExpect(this.bulletList.appendList(this.empty), this.bulletList);
  }
  
  // tests the move all method
  boolean testMoveAll(Tester t) {
    return t.checkExpect(this.bulletList.moveAll(), this.bulletListMove) &&
        t.checkExpect(this.empty.moveAll(), this.empty);
  }
  
}
