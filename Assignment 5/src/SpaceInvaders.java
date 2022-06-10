import java.awt.Color;
import java.util.Random;
//import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
//import javalib.worldimages.CircleImage;
//import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;
import tester.Tester;

interface IList<T> {
  // filter this IList using the given predicate
  IList<T> filter(Predicate<T> pred);

  // map the given function onto every member of this IList
  <U> IList<U> map(Function<T, U> converter);

  // combine the items in this IList using the given function
  <U> U fold(BiFunction<T, U, U> converter, U initial);

  // checks to see if all items in a list match the criteria
  boolean andmap(Predicate<T> pred);

  // checks to see if any items in a list match the criteria
  boolean ormap(Predicate<T> pred);
}

class Expander implements Function<Integer, Integer> {
  Expander() {}

  // returns the number its called on plus 4
  public Integer apply(Integer t) {
    return t + 4;
  }
}

class GreaterThan implements Predicate<Integer> {
  public boolean test(Integer n) {
    return n > 5;
  }
}

class EndWithEr implements BiFunction<String,Integer,Integer> {
  EndWithEr() {}

  //counts how many words end with "er" 
  public Integer apply(String t, Integer u) {
    if (t.substring(t.length() - 2, t.length()).equals("er")) {
      return u + 1;
    }
    else {
      return u;
    }
  }
}

//Represents a Cartesian point
class CartPt {
  int x;
  int y;

  CartPt(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

//Return true if the given CartPt is contained within points
class SameCartPt implements Predicate<CartPt> {
  CartPt posn;

  SameCartPt(CartPt posn) {
    this.posn = posn;
  }

  // return true if the given CartPt is contained within points and false
  // otherwise.
  public boolean test(CartPt pos) {
    return posn.x < (pos.x + 10) 
        && posn.x > (pos.x - 10) 
        && posn.y < (pos.y + 10)
        && posn.y > (pos.y - 10);
  }
}

class MtList<T> implements IList<T> {

  MtList() {
  }

  // filter this MtList using the given predicate
  public IList<T> filter(Predicate<T> pred) {
    return new MtList<T>();
  }

  // map the given function onto every member of this MtList
  public <U> IList<U> map(Function<T, U> converter) {
    return new MtList<U>();
  }

  // combine the items in this MtList using the given function
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    return initial;
  }

  // Checks if every element in the list passes the given predicate
  public boolean andmap(Predicate<T> pred) {
    return true;
  }

  // Checks if a single element of the list passes the given predicate
  public boolean ormap(Predicate<T> pred) {
    return false;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // filter this ConsList using the given predicate
  public IList<T> filter(Predicate<T> pred) {
    if (pred.test(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  // map the given function onto every member of this ConsList
  public <U> IList<U> map(Function<T, U> converter) {
    return new ConsList<U>(converter.apply(this.first), this.rest.map(converter));
  }

  // combine the items in this ConsList using the given function
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    return converter.apply(this.first, this.rest.fold(converter, initial));
  }

  // Returns true if all elements pass the predicate
  public boolean andmap(Predicate<T> pred) {
    return pred.test(first) && this.rest.andmap(pred);
  }

  // Returns true if at least one element passes the predicate
  public boolean ormap(Predicate<T> pred) {
    return pred.test(first) || this.rest.andmap(pred);
  }
}

//interface representing a game piece
interface IGamePiece {

  // draws the game piece
  WorldImage draw();

  // places the game piece on the scene
  WorldScene place(WorldScene ws);
}

//a class to represent a spaceship with a height, width, color and location
class Spaceship implements IGamePiece {
  int height;
  int width;
  Color c;
  CartPt cartPt;
  String direction;

  Spaceship(int height, int width, Color c, CartPt cartPt, String direction) {
    this.height = height;
    this.width = width;
    this.c = c;
    this.cartPt = cartPt;
    this.direction = direction;

  }

  // draws the spaceship
  public WorldImage draw() {
    return new RectangleImage(this.width, this.height, "Solid", this.c);
  }

  // places the game piece on the scene
  public WorldScene place(WorldScene ws) {
    return ws.placeImageXY(this.draw(), this.cartPt.x, this.cartPt.y);
  }

  // Changes the direction of the spaceship to go left
  public Spaceship turnLeft() {
    return new Spaceship(25, 60, Color.black, new CartPt(this.cartPt.x, this.cartPt.y), "left");
  }

  // Moves the spaceship to the left
  public Spaceship moveLeft() {
    return new Spaceship(25, 60, Color.black, new CartPt(this.cartPt.x - 2, this.cartPt.y), "left");
  }

  // Changes the direction of the spaceship to go right
  public Spaceship turnRight() {
    return new Spaceship(25, 60, Color.black, new CartPt(this.cartPt.x, this.cartPt.y), "right");
  }

  // Moves the spaceship to the right
  public Spaceship moveRight() {
    return new Spaceship(25, 60, Color.black, 
        new CartPt(this.cartPt.x + 2, this.cartPt.y), "right");
  }

  public Spaceship moveShip() {
    if (this.cartPt.x == 770) {
      return this.moveLeft();
    }
    if (this.cartPt.x == 30) {
      return this.moveRight();
    }
    if (this.direction.equals("right")) {
      return this.moveRight();
    }
    if (this.direction.equals("left")) {
      return this.moveLeft();
    }
    else {
      return this;
    }
  }
}

//a class to represent an Invader with a height, width, color and location
class Invader implements IGamePiece {
  int height;
  int width;
  Color c;
  CartPt cartPt;

  Invader(int height, int width, Color c, CartPt cartPt) {
    this.height = height;
    this.width = width;
    this.c = c;
    this.cartPt = cartPt;
  }

  // draws the invader
  public WorldImage draw() {
    return new RectangleImage(this.width, this.height, "Solid", this.c);
  }

  // places the Invader on the scene
  public WorldScene place(WorldScene ws) {
    return ws.placeImageXY(this.draw(), this.cartPt.x, this.cartPt.y);
  }
}

//represents a list of Invaders
interface ILoInvaders {

  // places the Invader on the list of invaders
  WorldScene place(WorldScene ws);
    
  //Returns the cartesian point of the nth element in the list
  CartPt getNth(int n);
     
  //Counts the elements in the string until the given nth number.
  CartPt getNthHelper(int n, int count);
}

//represents an empty list of Invaders
class MtLoInvaders implements ILoInvaders {

  // returns world scene
  public WorldScene place(WorldScene ws) {
    return ws;
  }
    
  //Returns an empty string if no index exists
  public CartPt getNth(int n) {
    return new CartPt(0, 0);
  }
    
  //Returns an empty string if no index exists
  public CartPt getNthHelper(int n, int count) {
    return new CartPt(0, 0);
  }
}

//represents a non-empty list of Invaders
class ConsLoInvaders implements ILoInvaders {
  Invader first;
  ILoInvaders rest;

  ConsLoInvaders(Invader first, ILoInvaders rest) {
    this.first = first;
    this.rest = rest;
  }

  // Remember to change this to foldr for next HW
  // Recursively places invaders onto the World Scene
  public WorldScene place(WorldScene ws) {
    return this.rest
        .place(ws.placeImageXY(this.first.draw(), this.first.cartPt.x, this.first.cartPt.y));
  }

  //Returns the nth element in the list with first index being 0
  public CartPt getNth(int n) {
    return this.getNthHelper(n, 0);
  }
    
  //Counts the elements in the string until the given nth number
  public CartPt getNthHelper(int n, int count) {
    if (n == count) {
      return this.first.cartPt;
    } else {
      return this.rest.getNthHelper(n, count + 1);
    }
  }
}
  
//a class to represent a bullet, with a radius, color and location
class InvaderBullet implements IGamePiece {
  int radius;
  Color c;
  CartPt cartPt;

  InvaderBullet(int radius, Color c, CartPt cartPt) {
    this.radius = 5;
    this.c = Color.red;
    this.cartPt = cartPt;
  }

  // draw this Bullet as CircleImage with its size and color
  public WorldImage draw() {
    return new CircleImage(this.radius, "solid", this.c);
  }

  // places the Bullet piece on the scene
  public WorldScene place(WorldScene ws) {
    return ws.placeImageXY(this.draw(), this.cartPt.x, this.cartPt.y);
  }

  // Moves the spaceship to the left
  public InvaderBullet invaderShootBullet() {
    return new InvaderBullet(this.radius, Color.black, 
        new CartPt(this.cartPt.x, this.cartPt.y + 2));
  }
}

//represents a list of Bullets
interface ILoInvaderBullets {

  //draws Bullets from this list onto the given scene
  WorldScene place(WorldScene ws);

  //moves an alien bullet
  ILoInvaderBullets invaderShootBulletX();
  
  //makes sure bullets can only be onscreen
  ILoInvaderBullets onScreen();
   
  // counts the amount of invader bullets
  int countInvaderBullets(int acc);
}

//represents an empty list of Bullets
class MtLoInvaderBullets implements ILoInvaderBullets {
    
  //draws Dots from this empty list onto the accumulated
  //image of the scene so far
  public WorldScene place(WorldScene ws) {
    return ws;
  }
    
  //Moves the bullets in this empty list
  public ILoInvaderBullets invaderShootBulletX() {
    return this;
  }

   
  public ILoInvaderBullets onScreen() {
    return this;
  }
  
  public int countInvaderBullets(int acc) {
    return acc;
  }  
}

//represents a non-empty list of Bullets
class ConsLoInvaderBullets implements ILoInvaderBullets {
  InvaderBullet first;
  ILoInvaderBullets rest;

  ConsLoInvaderBullets(InvaderBullet first, ILoInvaderBullets rest) {
    this.first = first;
    this.rest = rest;
  }

  //Recursively places the list of bullets onto the World Scene
  public WorldScene place(WorldScene ws) {
    return this.rest
        .place(ws.placeImageXY(this.first.draw(), this.first.cartPt.x, this.first.cartPt.y));
  }
    
  //Makes a lsit with the moving invader bullets
  public ILoInvaderBullets invaderShootBulletX() {
    return new ConsLoInvaderBullets(this.first.invaderShootBullet(), 
        this.rest.invaderShootBulletX());
  }

  public ILoInvaderBullets onScreen() {
    if (this.first.cartPt.y <= 750) {
      return new ConsLoInvaderBullets(this.first, this.rest);
    }
    else {
      return this.rest.onScreen();
    }
  }
    
  public int countInvaderBullets(int acc) {
    return this.rest.countInvaderBullets(acc + 1);
  } 
}

//a class to represent a bullet, with a radius, color and location
class PlayerBullet implements IGamePiece {
  int radius;
  Color c;
  CartPt cartPt;

  PlayerBullet(int radius, Color c, CartPt cartPt) {
    this.radius = radius;
    this.c = c;
    this.cartPt = cartPt;
  }

  //draw this Bullet as CircleImage with its size and color
  public WorldImage draw() {
    return new CircleImage(this.radius, "solid", this.c);
  }

  //places the Bullet piece on the scene
  public WorldScene place(WorldScene ws) {
    return ws.placeImageXY(this.draw(), this.cartPt.x, this.cartPt.y);
  }
    
  //the player shoots a bullet
  public PlayerBullet playerShootBullet() {
    return new PlayerBullet(5, Color.black, new CartPt(this.cartPt.x, this.cartPt.y - 4));
  }
}

//represents a list of Bullets
interface ILoPlayerBullets {

  // draws Bullets from this list onto the given scene
  WorldScene place(WorldScene ws);
    
  // moves a bullet
  ILoPlayerBullets playerShootBulletX();
    
  // removes off screen bullets
  ILoPlayerBullets onScreen();
    
  //counts the numebr of bullets in the list
  int countPlayerBullets(int acc);
}

//represents an empty list of Bullets
class MtLoPlayerBullets implements ILoPlayerBullets {

  // draws Dots from this empty list onto the accumulated
  // image of the scene so far
  public WorldScene place(WorldScene ws) {
    return ws;
  }

    
  public ILoPlayerBullets playerShootBulletX() {
    return this;
  }

  public int countPlayerBullets(int acc) {
    return acc;
  }


  public ILoPlayerBullets onScreen() {
    return this;
  }
}
 
//represents a non-empty list of Bullets
class ConsLoPlayerBullets implements ILoPlayerBullets {
  PlayerBullet first;
  ILoPlayerBullets rest;

  ConsLoPlayerBullets(PlayerBullet first, ILoPlayerBullets rest) {
    this.first = first;
    this.rest = rest;
  }

  // Recursively places the list of bullets onto the World Scene
  public WorldScene place(WorldScene ws) {
    return this.rest
       .place(ws.placeImageXY(this.first.draw(), this.first.cartPt.x, this.first.cartPt.y));
  }
    
  public ILoPlayerBullets playerShootBulletX() {
    return new ConsLoPlayerBullets(this.first.playerShootBullet(), this.rest.playerShootBulletX());
  }
    
  //counts the amount of PlayerBullets in the ILoPlayerBullets
  public int countPlayerBullets(int acc) {
    return this.rest.countPlayerBullets(acc + 1);
  }
    
  public ILoPlayerBullets onScreen() {
    if (this.first.cartPt.y >= 0) {
      return new ConsLoPlayerBullets(this.first, this.rest);
    }
    else {
      return this.rest.onScreen();
    }
  }
}

class SpaceInvaders extends World {
  Spaceship spaceship;
  ILoInvaders invaders;
  ILoPlayerBullets playerBullets;
  ILoInvaderBullets invaderBullets;

  SpaceInvaders(Spaceship spaceship, ILoInvaders invaders, ILoPlayerBullets playerBullets,
      ILoInvaderBullets invaderBullets) {
    this.spaceship = spaceship;
    this.invaders = invaders;
    this.playerBullets = playerBullets;
    this.invaderBullets = invaderBullets;
  }

  public WorldScene makeScene() {
    return this.invaderBullets.place(playerBullets.place(
        invaders.place(this.spaceship.place(new WorldScene(800, 800)))));
  }

  // changes the world on every tick
  public World onTick() {
    Random rand = new Random();
    int randomNum = rand.nextInt(36);
    ILoInvaderBullets addI = new ConsLoInvaderBullets(
        new InvaderBullet(5, Color.black, (this.invaders.getNth(randomNum))), this.invaderBullets);
    if (this.invaderBullets.countInvaderBullets(0) <= 9) {
      return new SpaceInvaders(this.spaceship.moveShip(), 
          this.invaders, this.playerBullets.playerShootBulletX().onScreen(), 
          addI.invaderShootBulletX().onScreen());
    }
    else {
      return new SpaceInvaders(this.spaceship.moveShip(), this.invaders, 
          this.playerBullets.playerShootBulletX().onScreen(), this.invaderBullets.
          invaderShootBulletX().onScreen());
    }
  }

  // changes the world depending on what key is pressed
  public World onKeyEvent(String key) {
    ILoPlayerBullets add = new ConsLoPlayerBullets(new PlayerBullet(5, Color.black,
        new CartPt(this.spaceship.cartPt.x, this.spaceship.cartPt.y - 10)), this.playerBullets);
    if (key.equals("left")) {
      return new SpaceInvaders(this.spaceship.turnLeft(), this.invaders, this.playerBullets,
          this.invaderBullets);
    }
    if (key.equals("right")) {
      return new SpaceInvaders(this.spaceship.turnRight(), this.invaders, this.playerBullets,
          this.invaderBullets);
    }
    if (key.equals(" ") && playerBullets.countPlayerBullets(0) < 3) {
      return new SpaceInvaders(this.spaceship, this.invaders, add, this.invaderBullets);
    }
    else {
      return this;
    }
  }
}
  
class ExamplesSpaceInvaders {
  ExamplesSpaceInvaders() {}
  
  ILoInvaders mt = new MtLoInvaders();
  
  Invader oneOne = new Invader(30, 30, Color.red, new CartPt(160, 50));
  Invader oneTwo = new Invader(30, 30, Color.red, new CartPt(213, 50));
  Invader oneThree = new Invader(30, 30, Color.red, new CartPt(266, 50));
  Invader oneFour = new Invader(30, 30, Color.red, new CartPt(319, 50));
  Invader oneFive = new Invader(30, 30, Color.red, new CartPt(372, 50));
  Invader oneSix = new Invader(30, 30, Color.red, new CartPt(425, 50));
  Invader oneSeven = new Invader(30, 30, Color.red, new CartPt(478, 50));
  Invader oneEight = new Invader(30, 30, Color.red, new CartPt(531, 50));
  Invader oneNine = new Invader(30, 30, Color.red, new CartPt(584, 50));
  
  Invader twoOne = new Invader(30, 30, Color.red, new CartPt(160, 100));
  Invader twoTwo = new Invader(30, 30, Color.red, new CartPt(213, 100));
  Invader twoThree = new Invader(30, 30, Color.red, new CartPt(266, 100));
  Invader twoFour = new Invader(30, 30, Color.red, new CartPt(319, 100));
  Invader twoFive = new Invader(30, 30, Color.red, new CartPt(372, 100));
  Invader twoSix = new Invader(30, 30, Color.red, new CartPt(425, 100));
  Invader twoSeven = new Invader(30, 30, Color.red, new CartPt(478, 100));
  Invader twoEight = new Invader(30, 30, Color.red, new CartPt(531, 100));
  Invader twoNine = new Invader(30, 30, Color.red, new CartPt(584, 100));
  
  Invader threeOne = new Invader(30, 30, Color.red, new CartPt(160, 150));
  Invader threeTwo = new Invader(30, 30, Color.red, new CartPt(213, 150));
  Invader threeThree = new Invader(30, 30, Color.red, new CartPt(266, 150));
  Invader threeFour = new Invader(30, 30, Color.red, new CartPt(319, 150));
  Invader threeFive = new Invader(30, 30, Color.red, new CartPt(372, 150));
  Invader threeSix = new Invader(30, 30, Color.red, new CartPt(425, 150));
  Invader threeSeven = new Invader(30, 30, Color.red, new CartPt(478, 150));
  Invader threeEight = new Invader(30, 30, Color.red, new CartPt(531, 150));
  Invader threeNine = new Invader(30, 30, Color.red, new CartPt(584, 150));
  
  Invader fourOne = new Invader(30, 30, Color.red, new CartPt(160, 200));
  Invader fourTwo = new Invader(30, 30, Color.red, new CartPt(213, 200));
  Invader fourThree = new Invader(30, 30, Color.red, new CartPt(266, 200));
  Invader fourFour = new Invader(30, 30, Color.red, new CartPt(319, 200));
  Invader fourFive = new Invader(30, 30, Color.red, new CartPt(372, 200));
  Invader fourSix = new Invader(30, 30, Color.red, new CartPt(425, 200));
  Invader fourSeven = new Invader(30, 30, Color.red, new CartPt(478, 200));
  Invader fourEight = new Invader(30, 30, Color.red, new CartPt(531, 200));
  Invader fourNine = new Invader(30, 30, Color.red, new CartPt(584, 200));
  
  Spaceship S1 = new Spaceship(25, 60, Color.black, new CartPt(400, 750), "");
  
  ILoPlayerBullets PBMT = new MtLoPlayerBullets();
  ILoPlayerBullets PB1 = new ConsLoPlayerBullets(new PlayerBullet(5, Color.red, new CartPt(0, 0)), 
      new ConsLoPlayerBullets(new PlayerBullet(5, Color.red, new CartPt(2, 2)), 
          new ConsLoPlayerBullets(new PlayerBullet(5, Color.red, new CartPt(4, 4)), 
              new MtLoPlayerBullets())));
  
  ILoInvaderBullets IBMT = new MtLoInvaderBullets();
  ILoInvaderBullets IB1 = new ConsLoInvaderBullets(new InvaderBullet(5, Color.red, 
      new CartPt(0, 0)), new ConsLoInvaderBullets(new InvaderBullet(
          5, Color.red, new CartPt(2, 2)), new ConsLoInvaderBullets(new InvaderBullet(
              5, Color.red, new CartPt(4, 4)), new MtLoInvaderBullets())));
  
  CartPt testPt = new CartPt(0, 0);
  
  ILoInvaders Row34 = new ConsLoInvaders(threeOne,
      new ConsLoInvaders(threeTwo, new ConsLoInvaders(threeThree,
          new ConsLoInvaders(threeFour, new ConsLoInvaders(threeFive,
              new ConsLoInvaders(threeSix, new ConsLoInvaders(threeSeven,
                  new ConsLoInvaders(threeEight, new ConsLoInvaders(threeNine,
                      new ConsLoInvaders(fourOne,
                          new ConsLoInvaders(fourTwo, 
                              new ConsLoInvaders(fourThree,
                              new ConsLoInvaders(fourFour,
                                  new ConsLoInvaders(fourFive,
                                  new ConsLoInvaders(fourSix,
                                      new ConsLoInvaders(fourSeven,
                                      new ConsLoInvaders(fourEight,
                                          new ConsLoInvaders(fourNine, 
                                          mt))))))))))))))))));
  
  ILoInvaders AllAliens = new ConsLoInvaders(oneOne,
      new ConsLoInvaders(oneTwo, new ConsLoInvaders(oneThree,
          new ConsLoInvaders(oneFour, new ConsLoInvaders(oneFive,
              new ConsLoInvaders(oneSix, new ConsLoInvaders(oneSeven,
                  new ConsLoInvaders(oneEight, new ConsLoInvaders(oneNine,
                      new ConsLoInvaders(twoOne,
                          new ConsLoInvaders(twoTwo,
                              new ConsLoInvaders(twoThree,
                              new ConsLoInvaders(twoFour,
                                  new ConsLoInvaders(twoFive,
                                  new ConsLoInvaders(twoSix,
                                      new ConsLoInvaders(twoSeven,
                                      new ConsLoInvaders(twoEight,
                                          new ConsLoInvaders(twoNine, 
                                          Row34))))))))))))))))));
      
  Predicate<Integer> greaterthan = new GreaterThan();
  Predicate<CartPt> goodCartPt = new SameCartPt(new CartPt(160, 200));
  Predicate<CartPt> badCartPt = new SameCartPt(new CartPt(300, 200));
  
  boolean testBigBang(Tester t) {
    SpaceInvaders world = new SpaceInvaders(S1, AllAliens, PBMT, IBMT);
    int worldWidth = 800;
    int worldHeight = 800;
    double tickRate = 0.001;
    return world.bigBang(worldWidth, worldHeight, tickRate);
  }
  
  IList<Integer> startWithExpected = new ConsList<Integer>(1, 
      new ConsList<Integer>(10, new ConsList<Integer>(6,
          new MtList<Integer>())));
  
  IList<Integer> startWithExpected2 = new ConsList<Integer>(8,    
      new ConsList<Integer>(10, new ConsList<Integer>(6,
          new MtList<Integer>())));
  
  IList<CartPt> listOfPoints = new ConsList<CartPt>(new CartPt(160, 200),
      new ConsList<CartPt>(new CartPt(213,200), new MtList<CartPt>()));
  
  IList<String> listOfWords = new ConsList<String>("Hello",
      new ConsList<String>("Teller",
          new ConsList<String>("random",
              new ConsList<String>("Yeller", new MtList<String>()))));
  
  
  boolean testFilter(Tester t) {
    return t.checkExpect(this.startWithExpected.filter(greaterthan),
        new ConsList(10, new ConsList(6, new MtList())))
        && t.checkExpect(this.startWithExpected2.filter(greaterthan), 
            new ConsList(8,  new ConsList(10, new ConsList(6, new MtList()))));
  }
  
  boolean testMap(Tester t) {
    return t.checkExpect(this.startWithExpected.map(new Expander()), 
        new ConsList(5, new ConsList(14, new ConsList(10, new MtList()))))
        && t.checkExpect(this.startWithExpected2.map(new Expander()), 
            new ConsList(12, new ConsList(14, new ConsList(10, new MtList()))));
  }
  
  boolean testFold(Tester t) {
    return t.checkExpect(this.listOfWords.fold(new EndWithEr(), 0), 2);
  }
    
  boolean testAndMap(Tester t) {
    return t.checkExpect(this.startWithExpected.andmap(greaterthan), false)
        && t.checkExpect(this.startWithExpected2.andmap(greaterthan), true);
  }
  
  boolean testOrMap(Tester t) {
    return t.checkExpect(this.startWithExpected.ormap(greaterthan), true)
        && t.checkExpect(this.startWithExpected2.ormap(greaterthan), true);
  }
  
  boolean testDraw(Tester t) {
    return t.checkExpect(this.oneOne.draw(), new RectangleImage(30, 30, "solid", Color.red));
  }
  
  boolean testSameCartPt(Tester t) {
    return t.checkExpect(this.listOfPoints.ormap(goodCartPt), true)
        && t.checkExpect(this.listOfPoints.ormap(badCartPt), false);
  }
  
  WorldScene testWorld = new WorldScene(800, 800);
  WorldScene testShipWorld = this.S1.place(testWorld);
  
  boolean testPlace(Tester t) {
    return t.checkExpect(this.S1.place(testWorld), testWorld.placeImageXY(S1.draw(), 400, 750));
  }
  
  boolean testTurnLeft(Tester t) {
    return t.checkExpect(this.S1.turnLeft(), new Spaceship(25, 60, Color.black, 
        new CartPt(400, 750), "left"));
  }
  
  boolean testTurnRight(Tester t) {
    return t.checkExpect(this.S1.turnRight(), new Spaceship(25, 60, Color.black,
        new CartPt(400, 750), "right"));
  }
  
  boolean testMoveLeft(Tester t) {
    return t.checkExpect(this.S1.moveLeft(), new Spaceship(25, 60, Color.black, 
        new CartPt(398, 750), "left"));   
  }
  
  boolean testMoveRight(Tester t) {
    return t.checkExpect(this.S1.moveRight(), new Spaceship(25, 60, Color.black,
        new CartPt(402, 750), "right"));
  }
  
  boolean testMoveShip(Tester t) {
    return t.checkExpect(this.S1.moveShip(), new Spaceship(25, 60, Color.black, 
        new CartPt(400, 750), ""));
  }
  
  boolean testGetNth(Tester t) {
    return t.checkExpect(this.AllAliens.getNth(2), new CartPt(266, 50)); 
  }
  
  boolean testGetNthHelper(Tester t) {
    return t.checkExpect(this.AllAliens.getNthHelper(2, 0), new CartPt(266, 50));
  }
  
  boolean testInvaderShootBullet(Tester t) {
    return t.checkExpect(new InvaderBullet(5, Color.red, testPt).invaderShootBullet(), 
        new InvaderBullet(5, Color.red, new CartPt(0, 2)));
  }
  
  boolean testInvaderShootBulletX(Tester t) {
    return t.checkExpect(this.IB1.invaderShootBulletX(),
        new ConsLoInvaderBullets(new InvaderBullet(5, Color.red, new CartPt(0, 2)), 
            new ConsLoInvaderBullets(new InvaderBullet(5, Color.red, new CartPt(2, 4)), 
                new ConsLoInvaderBullets(new InvaderBullet(5, Color.red, new CartPt(4, 6)),
                    new MtLoInvaderBullets()))));
  }
  
  boolean testOnScreenInvaders(Tester t) {
    return t.checkExpect(this.IB1.onScreen(), new ConsLoInvaderBullets(new InvaderBullet(
        5, Color.red, new CartPt(0, 0)), 
        new ConsLoInvaderBullets(new InvaderBullet(5, Color.red, new CartPt(2, 2)), 
            new ConsLoInvaderBullets(new InvaderBullet(5, Color.red, new CartPt(4, 4)), 
                new MtLoInvaderBullets()))));
  }
  
  boolean testCountInvaderBullets(Tester t) {
    return t.checkExpect(this.IB1.countInvaderBullets(0), 3);
    
  }
  
  boolean testPlayerShootBullet(Tester t) {
    return t.checkExpect(new PlayerBullet(5, Color.black, testPt).playerShootBullet(), 
        new PlayerBullet(5, Color.black, new CartPt(0, -4)));
  }
  
  boolean testPlayerShootBulletX(Tester t) {
    return t.checkExpect(this.PB1.playerShootBulletX(),
        new ConsLoPlayerBullets(new PlayerBullet(5, Color.black, new CartPt(0, -4)), 
            new ConsLoPlayerBullets(new PlayerBullet(5, Color.black, new CartPt(2, -2)), 
                new ConsLoPlayerBullets(new PlayerBullet(5, Color.black, new CartPt(4, 0)), 
                    new MtLoPlayerBullets()))));
  }
  
  boolean testOnScreenPlayer(Tester t) {
    return t.checkExpect(this.PB1.onScreen(), new ConsLoPlayerBullets(new PlayerBullet(
        5, Color.red, new CartPt(0, 0)), 
        new ConsLoPlayerBullets(new PlayerBullet(5, Color.red, new CartPt(2, 2)), 
            new ConsLoPlayerBullets(new PlayerBullet(5, Color.red, new CartPt(4, 4)), 
                new MtLoPlayerBullets()))));
  }
  
  boolean testCountPlayerBullets(Tester t) {
    return t.checkExpect(this.PB1.countPlayerBullets(0), 3);
  }
  
  boolean testMakeScene(Tester t) {
    return t.checkExpect(new SpaceInvaders(S1, mt, PBMT, IBMT).makeScene(), 
        this.IBMT.place(PBMT.place(mt.place(this.S1.place(new WorldScene(800, 800))))));
  }

  boolean testOnTick(Tester t) {
    return t.checkExpect(new SpaceInvaders(S1, mt, PBMT, IBMT).onTick(), 
        new SpaceInvaders(S1, mt, PBMT, new ConsLoInvaderBullets(new InvaderBullet(
            5, Color.red, new CartPt(0,2)), new MtLoInvaderBullets())));
  }
}
    
    
  
  