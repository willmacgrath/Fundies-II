
import tester.*;     
import java.util.Random;           
import javalib.worldimages.*;   
import javalib.funworld.*;      
import java.awt.Color;     

//a class to represent a dot with a radius, color and location
class Dot {
  Random random;
  int radius;
  Color c;
  int x;
  int y;
 
  
  Dot(int radius, Color c, Random random) {
    this.random = random;
    this.radius = radius;
    this.c = c;
    this.x = random.nextInt(600);
    this.y = random.nextInt(400);
  }
  
  Dot(int radius, Color c, int x, int y) {
    this.radius = radius;
    this.c = c;
    this.x = x;
    this.y = y;
    this.random = new Random();
  }
  
  //draw this Dot as CircleImage with its size and color
  WorldImage draw() {
    return new CircleImage(this.radius, "solid", this.c);
  }
  
  //create a new dot that is like this Dot but is shifted on the x-axis
  Dot move() {
    return new Dot(this.radius, this.c, this.x + 5, this.y + 3);
  }
  
  Dot green() {
    return new Dot(this.radius, Color.green , this.x, this.y);
  }
  
}

//represents a list of Dots
interface ILoDot {
  //draws Dots from this list onto the given scene
  WorldScene draw(WorldScene acc);
  
  //moves this list of Dots
  ILoDot move();
  
  ILoDot green();
}

//represents an empty list of Dots
class MtLoDot implements ILoDot {

  //draws Dots from this empty list onto the accumulated
  //image of the scene so far
  public WorldScene draw(WorldScene acc) {
    return acc;
  }

  //move the Dots in this empty list
  public ILoDot move() {
    return this;
  }

  @Override
  public ILoDot green() {
    // TODO Auto-generated method stub
    return this;
  }
}

//represents a non-empty list of Dots
class ConsLoDot implements ILoDot {
  Dot first;
  ILoDot rest;
  
  ConsLoDot(Dot first, ILoDot rest) {
    this.first = first;
    this.rest = rest;
  }
  
  //draws dots from this non-empty list onto the accumulated
  //image of the scene so far
  public WorldScene draw(WorldScene acc) {
    return this.rest.draw(acc.placeImageXY(this.first.draw(), this.first.x, this.first.y));
  }
  
  //move the dots in this non-empty list
  public ILoDot move() {
    return new ConsLoDot(this.first.move(), this.rest.move());
  }

  @Override
  public ILoDot green() {
    // TODO Auto-generated method stub
    return new ConsLoDot(this.first.green(), this.rest.green());
  } 
}

//represents a world class to animate a list of Dots on a scene
class DotsWorld extends World {
  ILoDot dots;
  Random random;
  
  DotsWorld(ILoDot dots, Random random) {
    this.dots = dots;
    this.random = random;
  }
  
  DotsWorld(ILoDot dots) {
    this(dots, new Random());
  }
  
  //draws the dots onto the background
  public WorldScene makeScene() {
    return this.dots.draw(new WorldScene(600, 400));
  }
  
  //move the Dots on the scene. Adds a new Dot at a random location at every tick of the clock
  public World onTick() {
    ILoDot add = new ConsLoDot(new Dot(10, Color.magenta, this.random), this.dots);
    return new DotsWorld(add.move(), this.random);
  }
  
  //changes the colors of the existing Dots in this World to green when the "g" key is pressed
  public World onKeyEvent(String key) {
    if (key.equals("g")) {
      ILoDot add = new ConsLoDot(new Dot(10, Color.green, this.random), this.dots);
      return new DotsWorld(add.green(), this.random);
    }
    else {
      return this;
    }
  }
}

class ExamplesAnimation {
  
  Random randomSeed1 = new Random(20);
  Random randomSeed2 = new Random(10);
  Random randomSeed3 = new Random(30);
  Dot d1 = new Dot(10, Color.magenta, randomSeed1);
  Dot d2 = new Dot(10, Color.magenta, randomSeed2);
  Dot d3 = new Dot(10, Color.magenta, randomSeed3);
 
  
  ILoDot mt = new MtLoDot();
  ILoDot lod1 = new ConsLoDot(this.d1, this.mt);
  ILoDot lod2 = new ConsLoDot(this.d2, this.lod1);
  ILoDot lod3 = new ConsLoDot(this.d3, this.lod2);
  
  //add test for all methods above and any that you add
  
  boolean testBigBang(Tester t) {
    DotsWorld world = new DotsWorld(this.mt);
    int worldWidth = 600;
    int worldHeight = 400;
    double tickRate = .1;
    return world.bigBang(worldWidth, worldHeight, tickRate);
  }

  boolean testDraw(Tester t) {
    return 
        t.checkExpect(this.d1.draw(), new CircleImage(10, "solid", Color.magenta));
  }
  
  boolean testMove(Tester t) {
    return 
        t.checkExpect(this.d1.move(), new Dot(10, Color.magenta, 58, 239));
  }


  boolean testMakeScene(Tester t) {
    return 
        t.checkExpect(new DotsWorld(mt, randomSeed1).makeScene(), new WorldScene(600, 400))
        && t.checkExpect(new DotsWorld(lod1, randomSeed1).makeScene(), 
            new WorldScene(600, 400).placeImageXY(
                new CircleImage( 10, "solid", Color.magenta), 53, 236));
  }
  
  boolean testOnTick(Tester t) {
    return 
        t.checkExpect(new DotsWorld(mt, randomSeed1).onTick(), new DotsWorld(new ConsLoDot(
            new Dot(10, Color.magenta, new Random(20)), new MtLoDot()), randomSeed1));
  }
   
  boolean testOnKeyEvent(Tester t) {
    return 
        t.checkExpect(new DotsWorld(mt, randomSeed1).onKeyEvent("g"), new DotsWorld(new ConsLoDot(
            new Dot(10, Color.green, new Random(20)), new MtLoDot()), randomSeed1));
  }
  
  boolean testGreen(Tester t) {
    return 
        t.checkExpect(this.d1.green(), new Dot(10, Color.green , 53, 236));      
  }
}
