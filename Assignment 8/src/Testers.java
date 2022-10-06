import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;



class ExamplesCode {
  ExamplesCode() {}
  
  void testOnClicked(Tester t) {
    
    WorldImage Test = new RectangleImage(48, 69, "Solid", Color.green);
    DeckOfCards D1 = new DeckOfCards("");
    D1.makeHiddenBoard(1, 4);
    Game g = new Game(D1);
    ArrayList<Card> revealedCards = g.revealedCards;
    Posn cardOnePosn = new Posn(80, 80);
    Card OneClubs = new Card(1, "♣");
    Card TwoClubs = new Card(2, "♣");
    ArrayList<Card> Mt = new ArrayList<Card>();
    DeckOfCards D2 = new DeckOfCards("");
    D2.makeHiddenBoard(1, 4);
    Game g2 = new Game(D2);
    
    // This is going to be the expected result, with a single card over it
    g2.current.placeImageXY(OneClubs.drawRevealed(), 80, 80);

    // Here I am testing mutations, before the click the game g doesn't have
    // the revealed card drawn over it at the cards position.
    t.checkExpect(g.current.equals(g2.current), false);

    // After the mouseclick, the given card (in this case oneclubs) is added to the
    // arraylist of revealed cards, and the game is equal to a game where there is
    // a card drawn over it
    g.onMouseClicked(cardOnePosn);
    OneClubs.posn = cardOnePosn;

    // Testing what happens when clicking the first time (no cards are revealed)
    t.checkExpect(revealedCards.get(0), OneClubs);
    t.checkExpect(g.current.equals(g2.current), true);
    t.checkExpect(g.movesLeft, 200);
    t.checkExpect(g.cardCount, 1);

    // Here, the player clicked a second time (should work the exact same as the
    // first)
    // The position being clicked is 1 of Spades, so the cards match
    g.onMouseClicked(new Posn(230, 80));

    // The third click should register all the decisions (can click anywhere where
    // there is a card
    // But for now we are assuming this specific position is selected
    g.onMouseClicked(new Posn(230, 80));
   
    //The expected game image should have two green squares in the positions where the cards were
    g2.current.placeImageXY(Test, 80, 80);
    g2.current.placeImageXY(Test, 230, 80);
    
    //score should go down by 1, as well as moves left because
    //every two cards revealed counts as one move
    t.checkExpect(g.cardCount, 0);
    t.checkExpect(revealedCards, Mt);
    
    //This checks that the two green squares were properly placed
    t.checkExpect(g.current, g2.current);
    t.checkExpect(g.score, 25);
    t.checkExpect(g.movesLeft, 199);
    
    D1 = new DeckOfCards("");
    D1.makeHiddenBoard(1, 4);
    g = new Game(D1);
    revealedCards = g.revealedCards;
    cardOnePosn = new Posn(80, 80);
    OneClubs = new Card(1, "♣");
    TwoClubs = new Card(2, "♣");
    Mt = new ArrayList<Card>();
    D2 = new DeckOfCards("");
    D2.makeHiddenBoard(1, 4);
    g2 = new Game(D2);
    
    g2.current.placeImageXY(OneClubs.drawRevealed(), 80, 80);
    g.onMouseClicked(cardOnePosn);
    OneClubs.posn = cardOnePosn;

    // Testing what happens when clicking the first time (no cards are revealed)
    t.checkExpect(revealedCards.get(0), OneClubs);
    t.checkExpect(g.current.equals(g2.current), true);
    t.checkExpect(g.movesLeft, 200);
    t.checkExpect(g.cardCount, 1);

    // Here, the player clicked a second time (should work the similar as the
    // first)
    // The position being clicked is 2 of Clubs, so the cards do not match
    g.onMouseClicked(new Posn(230, 80));

    // The third click should register all the decisions (can click anywhere where
    // there is a card
    // But for now we are assuming this specific position is selected
    g.onMouseClicked(new Posn(230, 80));
   
    //The expected game image should flip the cards back over
    g2.current.placeImageXY(OneClubs.drawHidden(), 80, 80);
    g2.current.placeImageXY(TwoClubs.drawHidden(), 230, 80);
    
    //score should go down by 1, as well as moves left because
    //every two cards revealed counts as one move
    t.checkExpect(g.cardCount, 0);
    t.checkExpect(revealedCards, Mt);
    
    //This checks that the score and moves updated correctly
    t.checkExpect(g.score, 25);
    t.checkExpect(g.movesLeft, 199);
      
  }
  
  // tests the Onkey reset
  void testReset(Tester t) {
    
    WorldScene start = new WorldScene(800, 400);
    WorldImage background = new RectangleImage(800, 400, "Solid", Color.green);
    start.placeImageXY(background, 400, 200);
    WorldImage cardBackground = new RectangleImage(40, 64, "Solid", Color.black);
    WorldImage cardOutline = new RectangleImage(45, 69, "Solid", Color.white);
    WorldImage cardSolids = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE,
        cardBackground, 0.0, 0.0, cardOutline);
    WorldImage cardImage = new TextImage("♛", 25, Color.WHITE);
    WorldImage cardBack = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE, cardImage,
        0.0, 0.0, cardSolids);
    
    start.placeImageXY(cardBack, 80, 80);
    
    DeckOfCards D1 = new DeckOfCards();
    Game g = new Game(D1);
    Card SevenHearts = new Card(7, "♥");
   
    g.current = g.deck.makeHiddenBoard(1, 1);
    g.cardCount ++;
    g.revealedCards.add(SevenHearts);
    g.score --;
    g.movesLeft = g.movesLeft - 2;
    g.timer ++;
       
    t.checkExpect(g.current, start);
    t.checkExpect(g.cardCount, 1);
    t.checkExpect(g.revealedCards.size(), 1);
    t.checkExpect(g.score, 25);
    t.checkExpect(g.movesLeft, 198);
    t.checkExpect(g.timer, 1.0);
    
    g.onKeyEvent("r");
    
    t.checkExpect(g.current, g.deck.makeHiddenBoard(4,13));
    t.checkExpect(g.cardCount, 0);
    t.checkExpect(g.revealedCards, new ArrayList<Card>());
    t.checkExpect(g.score, 26);
    t.checkExpect(g.movesLeft, 200);
    t.checkExpect(g.timer, 0.0);  
  }
  
  // tests our method that creates the board with the cards face up
  void testMakeRevealedBoard(Tester t) {
    DeckOfCards doc = new DeckOfCards();
    DeckOfCards result = new DeckOfCards(0);
    
    WorldScene start = new WorldScene(800, 400);
    WorldImage background = new RectangleImage(800, 400, "Solid", Color.green);
    start.placeImageXY(background, 400, 200);
    
    Card SevenHearts = new Card(7, "♥");
    Card NineClubs = new Card(9, "♣");
    Card ThirteenSpades = new Card(13, "♠");
    
    result.deckOfCards.add(SevenHearts);
    result.deckOfCards.add(NineClubs);
    result.deckOfCards.add(ThirteenSpades);
    
    start.placeImageXY(doc.deckOfCards.get(0).drawRevealed(), 80, 80);
    start.placeImageXY(doc.deckOfCards.get(1).drawRevealed(), 130, 80);
    start.placeImageXY(doc.deckOfCards.get(2).drawRevealed(), 180, 80);
    
    WorldScene drawC3 = start;
    
    t.checkExpect(doc.makeRevealedBoard(1, 3), drawC3);
    
  }

  // tests our method that creates the board with the cards face down
  void testMakeHiddenBoard(Tester t) {
    
    DeckOfCards doc = new DeckOfCards();
    DeckOfCards result = new DeckOfCards(0);
    
    WorldScene start = new WorldScene(800, 400);
    WorldImage background = new RectangleImage(800, 400, "Solid", Color.green);
    start.placeImageXY(background, 400, 200);
    
    WorldImage cardBackground = new RectangleImage(40, 64, "Solid", Color.black);
    WorldImage cardOutline = new RectangleImage(45, 69, "Solid", Color.white);
    WorldImage cardSolids = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE,
        cardBackground, 0.0, 0.0, cardOutline);
    WorldImage cardImage = new TextImage("♛", 25, Color.WHITE);
    WorldImage cardBack = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE, cardImage,
        0.0, 0.0, cardSolids);
      
    Card c1 = new Card(1, "");
    Card c2 = new Card(2, "");
    Card c3 = new Card(3, "");
     
    result.deckOfCards.add(c1);
    result.deckOfCards.add(c2);
    result.deckOfCards.add(c3);
    
    start.placeImageXY(cardBack, 80, 80);
    start.placeImageXY(cardBack, 130, 80);
    start.placeImageXY(cardBack, 180, 80);
    WorldScene drawC3 = start;
    
    t.checkExpect(doc.makeHiddenBoard(1,3), drawC3);
  }
   
  // tests our SamePosn fileter 
  void testTest(Tester t) {
    Posn thisPosn = new Posn(0, 30);
    Posn thatPosn = new Posn(50, 100);
    Posn thisPosn2 = new Posn(40, 30);
    Posn thatPosn2 = new Posn(40, 30);
    SamePosn compare = new SamePosn(thisPosn);
    SamePosn compare2 = new SamePosn(thisPosn2);

    t.checkExpect(compare.test(thatPosn), false);
    t.checkExpect(compare2.test(thatPosn2), true);
  }

  
  void testFilter(Tester t) {
    DeckOfCards doc = new DeckOfCards(0);
        
    Card c1 = new Card(1, "");
    Card c2 = new Card(2, "");
    Card c3 = new Card(3, "");
    Card c4 = new Card(4, "");
    Card c5 = new Card(5, "");
    Posn thisPosn = new Posn(50, 100);

    doc.deckOfCards.add(c1);
    c1.posn = new Posn(780, 12);

    doc.deckOfCards.add(c2);
    c2.posn = new Posn(51, 101);

    doc.deckOfCards.add(c3);
    c3.posn = new Posn(50, 100);

    doc.deckOfCards.add(c4);
    c4.posn = new Posn(-5, 20);

    doc.deckOfCards.add(c5);
    c5.posn = new Posn(50, 100);

    ArrayList<Card> d = new ArrayList<Card>();
    d.add(c2);
    d.add(c3);
    d.add(c5);
    
    Predicate<Posn> pred = new SamePosn(thisPosn);
    t.checkExpect(doc.filterCards(pred).size() , 3);
    t.checkExpect(doc.filterCards(pred), d);
  }

  void testDrawHidden(Tester t) {
    Card OneClubs = new Card(1, "♣");
    Card ThirteenSpades = new Card(13, "♠");

    WorldImage cardBackground = new RectangleImage(40, 64, "Solid", Color.black);
    WorldImage cardOutline = new RectangleImage(45, 69, "Solid", Color.white);
    WorldImage cardSolids = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE,
        cardBackground, 0.0, 0.0, cardOutline);
    WorldImage cardImage = new TextImage("♛", 25, Color.WHITE);
    WorldImage cardBack = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE, cardImage,
        0.0, 0.0, cardSolids);

    t.checkExpect(OneClubs.drawHidden(), cardBack);
    t.checkExpect(ThirteenSpades.drawHidden(), cardBack);
  }

  void testDrawRevealed(Tester t) {
    Card OneClubs = new Card(1, "♣");
    WorldImage cardBackground = new RectangleImage(45, 69, "Solid", Color.white);
    WorldImage oneClubsSuit = new TextImage(OneClubs.suit, 18, Color.BLACK);
    WorldImage oneClubsRank = new TextImage("A", 18, Color.BLACK);
    WorldImage oneClubsWSuit = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE,
        oneClubsSuit, 0.0, 0.0, cardBackground);
    WorldImage oneClubsRevealed = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        oneClubsRank, 0.0, 0.0, oneClubsWSuit);

    Card fiveDiamonds = new Card(5, "♦");
    WorldImage fiveDiamondsSuit = new TextImage(fiveDiamonds.suit, 18, Color.RED);
    WorldImage fiveDiamondsRank = new TextImage(Integer.toString(fiveDiamonds.rank), 18, Color.RED);
    WorldImage fiveDiamondswSuit = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE,
        fiveDiamondsSuit, 0.0, 0.0, cardBackground);
    WorldImage fiveDiamondsRevealed = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        fiveDiamondsRank, 0.0, 0.0, fiveDiamondswSuit);

    t.checkExpect(OneClubs.drawRevealed(), oneClubsRevealed);
    t.checkExpect(fiveDiamonds.drawRevealed(), fiveDiamondsRevealed);
  }

  void testGetCardAt(Tester t) {
    DeckOfCards D1 = new DeckOfCards("");
    Card OneClubs = new Card(1, "♣");
    Card ThirteenSpades = new Card(13, "♠");

    t.checkExpect(D1.getCardAt(0), OneClubs);
    t.checkExpect(D1.getCardAt(51), ThirteenSpades);
  }

  void testCreateDeck(Tester t) {
    DeckOfCards D = new DeckOfCards();
    Card current = D.getCardAt(15);
    t.checkExpect(D.getCardAt(15) == current, true);

    // Accounts for the possibility that shuffling might sometimes return the same
    // Card at the same position in the ArrayList
    while (D.getCardAt(15) == current) {
      Collections.shuffle(D.deckOfCards);
    }
    t.checkExpect(D.getCardAt(15) == current, false);
  }

  void testOrderedDeck(Tester t) {
    DeckOfCards D = new DeckOfCards("");
    Card SevenHearts = new Card(7, "♥");
    Card NineClubs = new Card(9, "♣");
    Card OneClubs = new Card(1, "♣");
    Card ThirteenSpades = new Card(13, "♠");

    t.checkExpect(D.getCardAt(0), OneClubs);
    t.checkExpect(D.getCardAt(51), ThirteenSpades);
    t.checkExpect(D.getCardAt(32), NineClubs);
    t.checkExpect(D.getCardAt(26), SevenHearts);
  }

  void testGame(Tester t) {
    DeckOfCards D1 = new DeckOfCards();
    Game g = new Game(D1);
    double tickRate = 1.0;
    g.bigBang(800, 400, tickRate);
  }
}