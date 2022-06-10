import tester.Tester;

// predicate class for any filters we need
interface IPredX<E> {
  boolean apply(E e);
}

// isOffScreen predicate class
class IsOffScreen implements IPredX<IGamePiece>, IConstants {
  // returns true if the given gp is offscreen, false otherwise
  public boolean apply(IGamePiece gp) {
    return gp.isOffScreen(IConstants.WIDTH, IConstants.HEIGHT);
  }
}

// test IPredX methods
class ExamplesIPredX {
  IsOffScreen applier = new IsOffScreen();

  IGamePiece bullet1 = new Bullet(new MyPosn(200, 200), new MyPosn(0, -1));
  IGamePiece bullet2 = new Bullet(new MyPosn(0, 0), new MyPosn(0, -1));
  IGamePiece bullet3 = new Bullet(new MyPosn(13003100, 200), new MyPosn(0, -1));

  // tests apply method
  boolean testApply(Tester t) {
    return t.checkExpect(this.applier.apply(this.bullet1), false)
        && t.checkExpect(this.applier.apply(this.bullet3), true)
        && t.checkExpect(this.applier.apply(this.bullet2), false);
  }
}