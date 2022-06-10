/*import java.awt.Color;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.WorldImage;
import java.util.Random;
import java.util.function.Predicate;  

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
    return new InvaderBullet(this.radius, Color.black, new CartPt(this.cartPt.x, this.cartPt.y + 2));
  }
}

//represents a list of Bullets
interface ILoInvaderBullets {

//draws Bullets from this list onto the given scene
  WorldScene place(WorldScene ws);

  //moves an alien bullet
 ILoInvaderBullets invaderShootBulletX();
 
 ILoInvaderBullets onScreen();
 
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

  @Override
  public ILoInvaderBullets onScreen() {
    return this;
  }

  @Override
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
    return new ConsLoInvaderBullets(this.first.invaderShootBullet(), this.rest.invaderShootBulletX());
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
*/




