/*import java.awt.Color;
import java.util.Random;

import javalib.funworld.World;
import javalib.funworld.WorldScene;

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
    return this.invaderBullets.place(playerBullets.place(invaders.place(this.spaceship.place(new WorldScene(800, 800)))));
  }

  // changes the world on every tick
  public World onTick() {
    Random rand = new Random();
    int randomNum = rand.nextInt(36);
    ILoInvaderBullets addI = new ConsLoInvaderBullets
        (new InvaderBullet(5, Color.black, (this.invaders.getNth(randomNum))), this.invaderBullets);

    if (this.invaderBullets.countInvaderBullets(0) <= 9) {
      return new SpaceInvaders(this.spaceship.moveShip(), this.invaders, this.playerBullets.playerShootBulletX().onScreen(), addI.invaderShootBulletX().onScreen());
  }
    else {
      return new SpaceInvaders(this.spaceship.moveShip(), this.invaders, this.playerBullets.playerShootBulletX().onScreen(), this.invaderBullets.invaderShootBulletX().onScreen());
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
    if (key.equals(" ") && playerBullets.countPlayerBullets(0) < 3){
      return new SpaceInvaders(this.spaceship, this.invaders, add, this.invaderBullets);
    }
    else {
      return this;
    }
  }
}*/