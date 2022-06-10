import tester.Tester*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.Random;

class MyGame extends World implements IConstants {
  
  int numBullets;
  int width;
  int height;
  double tickRate;
  int currentTick;
  
  int score;

  ILoGamePiece bullets;
  ILoGamePiece ships;
  
  MyGame(int numBullets) {
    
    this.width = IConstants.WIDTH;
    this.height = IConstants.HEIGHT;
    this.tickRate = IConstants.TICK_RATE;
    
    this.numBullets = numBullets;
    
    this.bullets = IConstants.initGamePieces;
    this.ships = IConstants.initGamePieces;
    this.currentTick = 0;
    this.score = 0;
  }

  MyGame(int numBullets, ILoGamePiece bullets, ILoGamePiece ships, int currentTick, int score) {
    
    this.width = IConstants.WIDTH;
    this.height = IConstants.HEIGHT;
    this.tickRate = IConstants.TICK_RATE;
    this.bullets = bullets;
    this.ships = ships;
    
    this.currentTick = currentTick;
    
    this.numBullets = numBullets;
    this.score = score;
  }
  
  // Makes the scene
  public WorldScene makeScene() {
    return this.ships.placeAll(this.bullets.placeAll(new WorldScene(this.width,
        this.height))).placeImageXY(new TextImage("Ships Destroyed: " + this.score, 
            Color.BLACK), 
            25, 20).placeImageXY(new TextImage("Bullets Left: " + this.numBullets, Color.BLACK),
                IConstants.WIDTH - 54, 20);
  }

  //This method gets called every tickrate seconds ( see bellow in example class).
  public MyGame onTick() {
    ILoGamePiece newBullets = this.bullets.moveAll().removeOffScreen();
    ILoGamePiece newShips = this.ships.moveAll().removeOffScreen();
    
    ILoGamePiece explodedBullets = newBullets.returnCollided(newShips).bulletExplodeAll();
    int newScore = this.score + newShips.returnCollided(newBullets).length();
    ILoGamePiece notCollidedBullets = newBullets.removeCollided(newShips);
    ILoGamePiece newerShips = newShips.removeCollided(newBullets);
    MyGame gamer = new MyGame(this.numBullets, notCollidedBullets.appendList(explodedBullets),
        newerShips, this.currentTick + 1, newScore);
    
    if (this.currentTick % 28 == 0) {
      return gamer.spawnShips();
    }
    else {
      return gamer;
    }
  }
  
  
  public MyGame onKeyEvent(String key) {
    //did we press the space update the final tick of the game by 10. 
    if (key.equals(" ") && this.numBullets > 0) {
      return new MyGame(this.numBullets - 1, new ConsLoGamePiece(new Bullet(), this.bullets),
          this.ships, this.currentTick, this.score);
    }
    else {
      return this;
    }
  }
 
  //Check to see if we need to end the game.
  public WorldEnd worldEnds() {
    if (this.numBullets <= 0 && this.bullets.length() <= 0) {
      return new WorldEnd(true, this.makeEndScene());
    } else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  public WorldScene makeEndScene() {
    WorldScene endScene = new WorldScene(this.width, this.height);
    return endScene.placeImageXY( new TextImage("Game Over", Color.red),
        this.width / 2, this.height / 2);

  }

  // spawns a random ship
  public IGamePiece spawnShip() {
    Random rand = new Random();
    int xSpawn = 0;
    MyPosn vel = new MyPosn(IConstants.SHIP_SPEED, 0);
    if (rand.nextInt(2) == 0) {
      xSpawn = IConstants.SHIP_SIZE;
    }
    else {
      xSpawn = IConstants.WIDTH - IConstants.SHIP_SIZE;
      vel = new MyPosn(- IConstants.SHIP_SPEED, 0);
    }
    int ySpawn = rand.nextInt(5 * (IConstants.HEIGHT / 7)) + (IConstants.HEIGHT / 7);

    return new Ship(new MyPosn(xSpawn, ySpawn), vel);
  }

  // spawns random ships in middle 5/7ths of the screen
  public MyGame spawnShips() {
    Random random = new Random();
    int randNum = random.nextInt(3);
    ILoGamePiece newShips = new MtLoGamePiece();
    if (randNum == 0) {
      newShips = this.ships.appendList(new ConsLoGamePiece(this.spawnShip(), 
          new MtLoGamePiece()));
    }
    else if (randNum == 1) {
      newShips = this.ships.appendList(new ConsLoGamePiece(this.spawnShip(), 
          new ConsLoGamePiece(this.spawnShip(), new MtLoGamePiece())));
    }
    else {
      newShips = this.ships.appendList(new ConsLoGamePiece(this.spawnShip(),  
          new ConsLoGamePiece(this.spawnShip(), 
              new ConsLoGamePiece(this.spawnShip(), 
                  new MtLoGamePiece()))));
    }
    return new MyGame(this.numBullets, this.bullets, newShips, this.currentTick, this.score);
  }

}

class ExamplesBulletGame implements IConstants {
  boolean testBigBang(Tester t) {
    MyGame world = new MyGame(10);
    //width, height, tickrate = 0.5 means every 0.5 seconds the onTick method will get called.
    return world.bigBang(IConstants.WIDTH, IConstants.HEIGHT, 1.0 / 28.0);
  }
}
