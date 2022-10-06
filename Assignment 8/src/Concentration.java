import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//Return true if the given CartPt is contained within points
class SamePosn implements Predicate<Posn> {
  Posn posn;

  SamePosn(Posn posn) {
    this.posn = posn;
  }

  // return true if the given CartPt is contained within points and false
  // otherwise.
  public boolean test(Posn pos) {
    return posn.x < (pos.x + 23) && posn.x > (pos.x - 23) && posn.y < (pos.y + 35)
        && posn.y > (pos.y - 35);
  }
}

//Represents an individual Card

class Card {

  Posn init = new Posn(0, 0);
  // Card Rank (Basically what card is it)
  int rank;
  // Card Suit
  String suit;
  // Card's position on the board
  Posn posn;
  //Suit Color
  Color cardC;

  Card(int rank, String suit) {
    this.rank = rank;
    this.suit = suit;
    this.posn = init;
    this.cardC = Color.black;

  }

  // Draws the card face down
  WorldImage drawHidden() {
    WorldImage cardBackground = new RectangleImage(40, 64, "Solid", Color.black);
    WorldImage cardOutline = new RectangleImage(45, 69, "Solid", Color.white);
    WorldImage cardSolids = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE,
        cardBackground, 0.0, 0.0, cardOutline);
    WorldImage cardImage = new TextImage("♛", 25, Color.WHITE);
    WorldImage cardBack = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE, cardImage,
        0.0, 0.0, cardSolids);

    return cardBack;
  }

  // Draws the card faceUp
  WorldImage drawRevealed() {
    WorldImage cardBackground = new RectangleImage(45, 69, "Solid", Color.white);

    if (this.suit.equals("♥") || this.suit.equals("♦")) {
      this.cardC = Color.RED;
    }
    WorldImage cardRank = new TextImage(Integer.toString(rank), 18, this.cardC);
    WorldImage cardSuit = new TextImage(this.suit, 18, this.cardC);
    WorldImage cardWithSuit = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE, cardSuit,
        0.0, 0.0, cardBackground);

    if (this.rank < 11 && this.rank > 1) {
      return new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, cardRank, 0.0, 0.0,
          cardWithSuit);
    }
    else if (this.rank == 11) {
      return new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
          new TextImage("J", 13, this.cardC), 0.0, 0.0, cardWithSuit);
    }
    else if (this.rank == 12) {
      return new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
          new TextImage("Q", 18, this.cardC), 0.0, 0.0, cardWithSuit);
    }
    else if (this.rank == 13) {
      return new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
          new TextImage("K", 18, this.cardC), 0.0, 0.0, cardWithSuit);
    }
    else {
      return new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
          new TextImage("A", 18, this.cardC), 0.0, 0.0, cardWithSuit);
    }
  }
}

//Represents a shuffled deck of cards
class DeckOfCards {
  ArrayList<Card> deckOfCards;

  DeckOfCards() {
    this.deckOfCards = this.createDeck();
  }

  // Makes an ordered deck of cards for testing purposes
  DeckOfCards(String order) {
    this.deckOfCards = this.orderedDeck();
  }
  
  // Makes an empty deck for even MORE testing purposes...
  DeckOfCards(int i) {
    this.deckOfCards = new ArrayList<Card>();
  }

  // Represents an ArrayList of all the possible card ranks
  ArrayList<Integer> ranks = new ArrayList<Integer>(
      Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13));

  // Represents an ArrayList of all the possible card suits
  ArrayList<String> suits = new ArrayList<String>(Arrays.asList("♣", "♦", "♥", "♠"));

  // Creates a shuffled deck of cards
  ArrayList<Card> createDeck() {
    // CartPt initPosn = new CartPt(0, 0);
    ArrayList<Card> deck = new ArrayList<Card>(52);
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 4; j++) {
        deck.add(new Card(ranks.get(i), suits.get(j)));
      }
    }
    Collections.shuffle(deck);
    return deck;
  }

  // Creates an Ordered Deck of Cards
  ArrayList<Card> orderedDeck() {
    ArrayList<Card> deck = new ArrayList<Card>(52);
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 4; j++) {
        deck.add(new Card(ranks.get(i), suits.get(j)));
      }
    }
    return deck;
  }

  // Returns the card at the given position
  Card getCardAt(int x) {
    return this.deckOfCards.get(x);
  }

  // Makes the board with the cards faceDown
  public WorldScene makeHiddenBoard(int row, int columns) {
    WorldScene start = new WorldScene(800, 400);
    WorldImage background = new RectangleImage(800, 400, "Solid", Color.green);
    WorldImage cardBackground = new RectangleImage(40, 64, "Solid", Color.black);
    WorldImage cardOutline = new RectangleImage(45, 69, "Solid", Color.white);
    WorldImage cardSolids = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE,
        cardBackground, 0.0, 0.0, cardOutline);
    WorldImage cardImage = new TextImage("♛", 25, Color.WHITE);
    WorldImage cardBack = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE, cardImage,
        0.0, 0.0, cardSolids);

    start.placeImageXY(background, 400, 200);
    int xPosn = 80;
    int yPosn = 0;
    int cardNum = 0;

    for (int j = 0; j < row; j++) {
      yPosn = yPosn + 80;
      xPosn = 80;
      for (int i = 0; i < columns; i++) {
        cardNum = i;
        if (j > 0) {
          cardNum = i + columns * j;
        }

        this.getCardAt(cardNum).posn = new Posn(xPosn, yPosn);
        start.placeImageXY(cardBack, xPosn, yPosn);
        xPosn = xPosn + 50;
      }
    }
    return start;
  }

  // Makes the board with the cards faceUp
  public WorldScene makeRevealedBoard(int row, int columns) {
    WorldScene start = new WorldScene(800, 400);
    WorldImage background = new RectangleImage(800, 400, "Solid", Color.green);
    start.placeImageXY(background, 400, 200);

    int xPosn = 80;
    int yPosn = 0;
    int cardNum = 0;

    for (int j = 0; j < row; j++) {
      yPosn = yPosn + 80;
      xPosn = 80;
      for (int i = 0; i < columns; i++) {
        cardNum = i;
        if (j > 0) {
          cardNum = i + columns * j;
        }

        this.getCardAt(cardNum).posn = new Posn(xPosn, yPosn);
        start.placeImageXY(this.deckOfCards.get(cardNum).drawRevealed(), xPosn, yPosn);
        xPosn = xPosn + 50;
      }
    }
    return start;
  }

  // Returns the card that is at the mouse position
  // If there is not card at mouse position, return an empty array list
  public ArrayList<Card> filterCards(Predicate<Posn> pred) {
    ArrayList<Card> cards = new ArrayList<Card>();
    for (int i = 0; i < this.deckOfCards.size(); i++) {
      if (pred.test(this.deckOfCards.get(i).posn)) {
        cards.add(this.getCardAt(i));
      }
    }
    return cards;
  }
}

// represents the game
class Game extends World {
  DeckOfCards deck;
  WorldScene current;
  int cardCount;
  int score;
  ArrayList<Card> revealedCards;
  double timer;
  int movesLeft;

  Game(DeckOfCards deck) {
    this.deck = deck;
    this.current = this.deck.makeHiddenBoard(4,13);
    this.cardCount = 0;
    this.revealedCards = new ArrayList<Card>();
    this.score = 26;
    this.timer = 0;
    this.movesLeft = 200;
  }

  // draws the game
  public WorldScene makeScene() {
    WorldImage scoreBack = new RectangleImage(75, 150, "Solid", Color.green);
    WorldImage scoreNum = new TextImage(Integer.toString(score), 18, Color.BLACK);
    WorldImage scoreText = new TextImage("Score", 18, Color.BLACK);
    WorldImage scoreNumText = new AboveImage(scoreText, scoreNum);
    WorldImage scoreBoard = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE,
        scoreNumText, 0.0, 0.0, scoreBack);
    current.placeImageXY(scoreBoard, 750, 100);
    WorldImage movesBack = new RectangleImage(75, 200, "Solid", Color.green);
    WorldImage movesText = new TextImage("Moves Left", 18, Color.BLACK);
    WorldImage movesNum = new TextImage(Integer.toString(this.movesLeft), 18, Color.BLACK);
    WorldImage movesNumText = new AboveImage(movesText, movesNum);
    WorldImage movesBoard = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE,
        movesNumText, 0.0, 0.0, movesBack);
    current.placeImageXY(movesBoard, 750, 250);
    WorldImage timerBack = new RectangleImage(75, 100, "Solid", Color.green);
    WorldImage timerd = new TextImage(Double.toString(timer), 18, Color.BLACK);
    WorldImage timerBoard = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.MIDDLE, timerd,
        0.0, 0.0, timerBack);
    current.placeImageXY(timerBoard, 750, 175);

    return current;
  }

  // adds one to the timer on each ticl
  public void onTick() {
    timer++;

  }

  // performs an action on a pressed key
  // in this case, r resets the game
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      this.deck = new DeckOfCards();
      this.current = this.deck.makeHiddenBoard(4,13);
      this.cardCount = 0;
      this.revealedCards = new ArrayList<Card>();
      this.score = 26;
      this.movesLeft = 200;
      this.timer = 0;
    }
  }

  // when the mouse is clicked, it first checks if it is clicking a card, and if it is 
  // reveals the card. Once you select a second card, it checks if they match. If they
  // do the cards are removed, and if not, the are flipped back over
  public void onMouseClicked(Posn pos) {
    WorldImage test = new RectangleImage(48, 69, "Solid", Color.green);
    ArrayList<Card> cards = this.deck.filterCards(new SamePosn(pos));
    WorldImage youWon = new TextImage("You Won", 30, FontStyle.BOLD, Color.black);

    if (cards.size() > 0) {
      Card currentCard = this.deck.filterCards(new SamePosn(pos)).get(0);
      if (score > 0) {
        cardCount = cardCount + 1;
        if (cardCount <= 2) {
          // Draws the current flipped card
          current.placeImageXY(currentCard.drawRevealed(), currentCard.posn.x, currentCard.posn.y);
          revealedCards.add(currentCard);
        }
        else {
          // If there are two cards revealed, check if they are equal to each other
          // if they are, cover the cards, take them out of teh list, and remove score and movesLeft
          // and reset card count
          if (revealedCards.get(0).rank == revealedCards.get(1).rank
              && revealedCards.get(0).suit != revealedCards.get(1).suit) {
            score--;
            current.placeImageXY(test, revealedCards.get(0).posn.x, revealedCards.get(0).posn.y);
            current.placeImageXY(test, revealedCards.get(1).posn.x, revealedCards.get(1).posn.y);
            this.deck.deckOfCards.remove(revealedCards.get(0));
            this.deck.deckOfCards.remove(revealedCards.get(1));
            this.movesLeft--;
            cardCount = cardCount - 3;
            revealedCards.removeAll(revealedCards);
            if (this.score == 0) {
              current.placeImageXY(youWon, 400, 200);
              this.endOfWorld("You Won");
            }
          }
          else {
            // if they dont match, flip them back over and remove 1 movesLeft, and reset cardCount
            current.placeImageXY(revealedCards.get(0).drawHidden(), revealedCards.get(0).posn.x,
                revealedCards.get(0).posn.y);
            current.placeImageXY(revealedCards.get(1).drawHidden(), revealedCards.get(1).posn.x,
                revealedCards.get(1).posn.y);
            cardCount = cardCount - 3;
            this.movesLeft--;
            revealedCards.removeAll(revealedCards);
          }
        }
      }
    }
  }
}

