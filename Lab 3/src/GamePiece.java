import tester.Tester;

interface IGamePiece{
  int getValue();
  MergeTile merge(IGamePiece givenPiece);
  boolean isValid();
  
}

class BaseTile implements IGamePiece {
  int value;
  
  BaseTile(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    // TODO Auto-generated method stub
    return this.value;
  }

  @Override
  public MergeTile merge(IGamePiece givenPiece) {
    // TODO Auto-generated method stub
    return new MergeTile(this, givenPiece);
  }

  @Override
  public boolean isValid() {
    // TODO Auto-generated method stub
    return true;
  }
}

class MergeTile implements IGamePiece {
  IGamePiece piece1;
  IGamePiece piece2;
  
  MergeTile(IGamePiece piece1, IGamePiece piece2) {
    this.piece1 = piece1;
    this.piece2 = piece2;
  }

  @Override
  public int getValue() {
    // TODO Auto-generated method stub
    return this.piece1.getValue() + this.piece2.getValue();
  }

  @Override
  public MergeTile merge(IGamePiece givenPiece) {
    // TODO Auto-generated method stub
    return new MergeTile(this, givenPiece);
  }

  @Override
  public boolean isValid() {
    // TODO Auto-generated method stub
    return this.piece1.getValue() == this.piece2.getValue();
  }
}

class ExamplesGamePiece {
  ExamplesGamePiece() {
}
  
  BaseTile tile1 = new BaseTile(1);
  BaseTile tile2 = new BaseTile(2);
  BaseTile tile3 = new BaseTile(3);
  BaseTile tile4 = new BaseTile(4);
  BaseTile tile5 = new BaseTile(5);
  MergeTile mtile3 = new MergeTile(tile1, tile2);
  MergeTile mtile7 = new MergeTile(tile3, tile4);
  MergeTile mtile6 = new MergeTile(tile5, tile1);
  
  boolean testGetValue(Tester t) {
    return t.checkExpect(tile1.getValue(), 1)
          && t.checkExpect(tile4.getValue(), 4)
          && t.checkExpect(mtile3.getValue(), 3)
          && t.checkExpect(mtile6.getValue(), 6);
  }
}

  
   
  
  
