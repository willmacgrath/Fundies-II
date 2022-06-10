import java.awt.Color;
import javalib.worldimages.*;
import tester.Tester;
import javalib.funworld.*;

//interface representing a game piec
interface IGamePiece {
 
  int TOTAL_BULLETV = 10;
  int TOTAL_SHIPV = 5;
  int BULLET_RADIUS = 2;
  int SHIP_RADIUS = 10;
  Color SHIP_COLOR = Color.BLACK;
  Color BULLET_COLOR = Color.pink;
 
  // moves the game pieces
  IGamePiece move();
 
  // checks if a game piece is off screen
  boolean isOffScreen(int width, int height);
 
  // draws the game piece
  WorldImage draw();
 
  // places the game piece on the scene
  WorldScene place(WorldScene ws);
 
  // determines if the game piece has collided with another
  boolean collided(IGamePiece that);
 
  // helper that determines if the game piece has collided with another
  boolean collidedHelper(IGamePiece that, int thatSize);
 
  // determines the distance to another game piece
  double distanceTo(IGamePiece that);
 
  // helper determines the distance to another game piece  
  double distanceToHelper(MyPosn thatPosition);
 
  // returns the list of explosion bullets
  ILoGamePiece bulletExplode();
 
  // helper that returns the list of explosion bullets
  ILoGamePiece bulletExplodeHelper(int index);
}

// abstract class representing a game piece
abstract class AGamePiece implements IGamePiece {
  MyPosn position;
  MyPosn velocity;
  int size;
  Color color;
 
  AGamePiece(MyPosn position, MyPosn velocity, int size, Color color) {
    this.position = position;
    this.velocity = velocity;
    this.size = size;
    this.color = color;
  }
 
  //moves the game pieces
  public abstract IGamePiece move();
 
  // checks if a game piece is off screen
  public boolean isOffScreen(int width, int height) {
    return this.position.isOffScreen(width, height);
  }
 
  // draws the game piece
  public WorldImage draw() {
    return new CircleImage(this.size, OutlineMode.SOLID, this.color);
  }
 
  // places the game piece on the scene
  public WorldScene place(WorldScene ws) {
    return ws.placeImageXY(this.draw(), this.position.x, this.position.y);
  }
 
  // determines if the game piece has collided with another
  public boolean collided(IGamePiece that) {
    return that.collidedHelper(this, this.size);
  }
 
  // helper that determines if the game piece has collided with another
  public boolean collidedHelper(IGamePiece that, int thatSize) {
    return this.distanceTo(that) < thatSize + this.size;
  }
 
  // determines the distance to another game piece
  public double distanceTo(IGamePiece that) {
    return that.distanceToHelper(this.position);
  }
 
  // helper determines the distance to another game piece
  public double distanceToHelper(MyPosn thatPosition) {
    return this.position.distanceTo(thatPosition);
  }
 
  // returns the list of explosion bullets
  public abstract ILoGamePiece bulletExplode();
 
  // helper that returns the list of explosion bullets
  public abstract ILoGamePiece bulletExplodeHelper(int index);
 
}

// class to represent a bullet
class Bullet extends AGamePiece {
  int bulletStreak;
 
  Bullet(MyPosn position, MyPosn velocity) {
    super(position, velocity, IGamePiece.BULLET_RADIUS, IGamePiece.BULLET_COLOR);
    this.bulletStreak = 1;
  }
 
  Bullet(MyPosn position, MyPosn velocity, int size, int bulletStreak) {
    super(position, velocity, size, IGamePiece.BULLET_COLOR);
    this.bulletStreak = bulletStreak;
  }
 
  Bullet() {
    this(new MyPosn(IConstants.WIDTH / 2, IConstants.HEIGHT - 2),
        new MyPosn(0, - IConstants.BULLET_SPEED));
  }
 
  //moves the game pieces
  public IGamePiece move() {
    return new Bullet(this.position.add(this.velocity), this.velocity,
        this.size, this.bulletStreak);
  }

  // returns the list of exploded bullets
  public ILoGamePiece bulletExplode() {
    return this.bulletExplodeHelper(0);
  }

  // helper that returns the list of explosion bullets
  public ILoGamePiece bulletExplodeHelper(int index) {
    if (index <= this.bulletStreak) {
      if (this.size >= 10) {
        int newSize = 10;
        return new ConsLoGamePiece(new Bullet(this.position,
            this.velocity.indexToPosn(index, this.bulletStreak),
            newSize, this.bulletStreak + 1), this.bulletExplodeHelper(index + 1));
      }
      else {
        return new ConsLoGamePiece(new Bullet(this.position,
            this.velocity.indexToPosn(index, this.bulletStreak),
            this.size + 2, this.bulletStreak + 1), this.bulletExplodeHelper(index + 1));
      }
    }
    else {
      return new MtLoGamePiece();
    }
  }  
}

// class to represent a ship
class Ship extends AGamePiece {
 
  Ship(MyPosn position, MyPosn velocity) {
    super(position, velocity, IGamePiece.SHIP_RADIUS, IGamePiece.SHIP_COLOR);
  }
 
  // moves the game pieces
  public IGamePiece move() {
    return new Ship(this.position.add(this.velocity), this.velocity);
  }

  // returns the list of exploded bullets
  public ILoGamePiece bulletExplode() {
    return new MtLoGamePiece();
  }

  // helper that returns the list of exploded bullets
  public ILoGamePiece bulletExplodeHelper(int index) {
    return new MtLoGamePiece();
  }
}

// tests IGamePiece methods
class ExamplesGamePiece {
  IGamePiece bullet1 = new Bullet(new MyPosn(200, 200), new MyPosn(0, -1));
  IGamePiece bullet2 = new Bullet(new MyPosn(200, 199), new MyPosn(0, -1));
  IGamePiece bullet3 = new Bullet(new MyPosn(13003100, 200), new MyPosn(0, -1));
 
  IGamePiece bulletHope = new Bullet(new MyPosn(200, 200), new MyPosn(0, -8));
 
  IGamePiece ship1 = new Ship(new MyPosn(200, 200), new MyPosn(5, 0));
  IGamePiece ship2 = new Ship(new MyPosn(-100, 300), new MyPosn(5, 0));
  IGamePiece ship3 = new Ship(new MyPosn(10, 200), new MyPosn(5, 0));
 
  WorldImage bullet1draw = new CircleImage(2, OutlineMode.SOLID, Color.pink);
  WorldImage ship1draw = new CircleImage(10, OutlineMode.SOLID, Color.black);
  WorldScene ws = new WorldScene(500, 300);
  WorldScene bullet1Scene = ws.placeImageXY(this.bullet1draw, 200, 200);
  WorldScene ship1Scene = ws.placeImageXY(this.ship1draw, 200, 200);
 
  ILoGamePiece list = new ConsLoGamePiece(new Bullet(new MyPosn(200, 200),
      new MyPosn(8, 0), 4, 2),
      new ConsLoGamePiece(new Bullet(new MyPosn(200, 200), new MyPosn(-8, 0), 4, 2),
          new MtLoGamePiece()));
 
  // tests move method
  boolean testMove(Tester t) {
    return t.checkExpect(this.bullet1.move(), this.bullet2);
  }
 
  // tests isOffScreen method
  boolean testisOffScreen(Tester t) {
    return t.checkExpect(this.bullet3.isOffScreen(500, 300), true)
        && t.checkExpect(this.bullet1.isOffScreen(500, 300), false);
  }
 
  // tests bulletExplode method
  boolean testBulletExplode(Tester t) {
    return t.checkExpect(bulletHope.bulletExplode(), this.list);
  }
 
  // tests draw method
  boolean testDraw(Tester t) {
    return t.checkExpect(bullet1.draw(), this.bullet1draw)
        && t.checkExpect(ship1.draw(), this.ship1draw);
  }
 
  // tests place method
  boolean testPlace(Tester t) {
    return t.checkExpect(bullet1.place(this.ws), this.bullet1Scene)
        && t.checkExpect(ship1.place(this.ws), this.ship1Scene);
  }
 
  //tests collided method
  boolean testCollided(Tester t) {
    return t.checkExpect(this.bullet1.collided(this.bullet1), true)
        && t.checkExpect(this.bullet1.collided(this.ship1), true)
        && t.checkExpect(this.bullet1.collided(this.bullet3), false);
  }
}