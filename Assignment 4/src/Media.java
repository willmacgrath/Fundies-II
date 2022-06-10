
import tester.Tester;

// a piece of media
interface IMedia {
  
  // is this media really old?
  boolean isReallyOld();
  
  // are captions available in this language?
  boolean isCaptionAvailable(String language);
  
  // a string showing the proper display of the media
  String format();
  
  boolean sameMedia(IMedia that);
  
  boolean sameMovie(Movie that);
  
}

abstract class AMedia implements IMedia {
  String title;
  ILoString captionOptions;
  
  /*
  TEMPLATE
  FIELDS:
  ... this.title ...                -- String
  ... this.captionOptions ...       -- ILoString
  
  METHODS
  ... this.isReallyOld() ...         -- boolean
  ... this.isCaptionAvailable() ...  -- boolean
  ... this.format() ...              -- String


  METHODS FOR FIELDS
  */
  
  
  AMedia(String title, ILoString captionOptions) {
    this.title = title;
    this.captionOptions = captionOptions;
  }
  
  // is this media really old?
  public boolean isReallyOld() {
    return false;
  }
  
  //are captions available in this language?
  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.contains(language);
  }
  
  //a string showing the proper display of the media
  public abstract String format();
}

// represents a movie
class Movie extends AMedia {
  int year;
  
  /*
  TEMPLATE
  FIELDS:
  ... this.year ...                 -- int
  ... this.title ...                -- String
  ... this.captionOptions ...       -- ILoString
  
  METHODS
  ... this.isReallyOld() ...         -- boolean
  ... this.isCaptionAvailable() ...  -- boolean
  ... this.format() ...              -- String


  METHODS FOR FIELDS
  ... this.captionOptions.contains() ... -- boolean
  */
  
  
  Movie(String title, int year, ILoString captionOptions) {
    super(title, captionOptions);
    this.year = year;
    
  }
  
  // is this media really old?
  public boolean isReallyOld() {
    return this.year < 1930;
  }
  
  //a string showing the proper display of the media
  public String format() {
    return this.title + " (" + Integer.toString(this.year) + ")";
  }

  @Override
  public boolean sameMedia(IMedia that) {
    // TODO Auto-generated method stub
    return that.sameMovie(this);
  }

  @Override
  public boolean sameMovie(Movie that) {
    // TODO Auto-generated method stub
    return this.title.equals(that.title )
        && this.year == that.year;
  }
}

// represents a TV episode
class TVEpisode extends AMedia {
  String showName;
  int seasonNumber;
  int episodeOfSeason;
  
  /*
  TEMPLATE
  FIELDS:
  ... this.title ...                -- String
  ... this.showName ...             -- String
  ... this.seasonNumber ...         -- int
  ... this.seasonNumber ...         -- int
  ... this.captionOptions ...       -- ILoString
  
  METHODS
  ... this.isReallyOld() ...         -- boolean
  ... this.isCaptionAvailable() ...  -- boolean
  ... this.format() ...              -- String


  METHODS FOR FIELDS
  ... this.captionOptions.contains() ... -- boolean
  */
  

  TVEpisode(String title, String showName, int seasonNumber, int episodeOfSeason,
      ILoString captionOptions) {
    super(title, captionOptions);
    this.showName = showName;
    this.seasonNumber = seasonNumber;
    this.episodeOfSeason = episodeOfSeason;
  }

  // a string showing the proper display of the media
  public String format() {
    return this.showName + " " + Integer.toString(this.seasonNumber)  
        + "." + Integer.toString(this.episodeOfSeason) + " - " + this.title;
  }

  @Override
  public boolean sameMedia(IMedia other) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean sameMovie(Movie that) {
    // TODO Auto-generated method stub
    return false;
  }
}

// represents a YouTube video
class YTVideo extends AMedia {
  String channelName;
  
  /*
  TEMPLATE
  FIELDS:
  ... this.title ...                -- String
  ... this.captionOptions ...       -- ILoString
  
  METHODS
  ... this.isReallyOld() ...         -- boolean
  ... this.isCaptionAvailable() ...  -- boolean
  ... this.format() ...              -- String


  METHODS FOR FIELDS
  ... this.captionOptions.contains() ... -- boolean
  */
  
  
  public YTVideo(String title, String channelName, ILoString captionOptions) {
    super(title, captionOptions);
    this.channelName = channelName;
  }
  
  // a string showing the proper display of the media
  public String format() {
    return this.title + " by " + this.channelName;
  }

  @Override
  public boolean sameMedia(IMedia that) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean sameMovie(Movie that) {
    // TODO Auto-generated method stub
    return false;
  }
  
}

// lists of strings
interface ILoString {

  // checks to see if list contains a word
  boolean contains(String word);
}

// an empty list of strings
class MtLoString implements ILoString {
  
  /*
  TEMPLATE
  FIELDS:
  
  METHODS
  ... this.contains() ...            -- boolean

  METHODS FOR FIELDS
  */

  //checks to see if list contains a word
  public boolean contains(String word) {
    // TODO Auto-generated method stub
    return false;
  }
}

// a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;
  
  /*
  TEMPLATE
  FIELDS:
  ... this.first ...                -- String
  ... this.rest...                  -- ILoString
  
  METHODS
  ... this.contains() ...            -- boolean

  METHODS FOR FIELDS
  */
  
  
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  public boolean contains(String word) {
    // TODO Auto-generated method stub
    return this.first.equals(word) 
        || this.rest.contains(word);
  }
}

class ExamplesMedia {
  ExamplesMedia() {
  }
  
  IMedia sw = new Movie("Star Wars", 1977, new ConsLoString("English", 
      new ConsLoString("Spanish", new ConsLoString("French", new MtLoString())))); 
  IMedia ij = new Movie("Indiana Jones", 1981, new ConsLoString("English", 
      new ConsLoString("Spanish", new MtLoString()))); 
  IMedia old = new Movie("The Gold Rush", 1925, new MtLoString()); 
  IMedia himym = new TVEpisode("The Naked Man", "How I Met Your Mother", 4, 9, 
      new ConsLoString("English", new ConsLoString("Spanish", 
          new ConsLoString("French", new MtLoString()))));
  IMedia b99 = new TVEpisode("Halloween", "Brooklyn Nine Nine", 1, 6,  new ConsLoString("English", 
      new ConsLoString("French", new MtLoString())));
  IMedia despacito = new YTVideo("Despacito", "Luis Fonsi", 
      new ConsLoString("English", new ConsLoString("Spanish", new MtLoString()))); 
  IMedia baby = new YTVideo("Baby Shark", "Pinkfong Baby Shark", new MtLoString()); 

  boolean testIsReallyOld(Tester t) {
    return 
        t.checkExpect(this.sw.isReallyOld(), false)
        && t.checkExpect(this.old.isReallyOld(), true)
        && t.checkExpect(this.b99.isReallyOld(), false)
        && t.checkExpect(this.baby.isReallyOld(), false);
  }
  
  boolean testIsCaptionAvailable(Tester t) {
    return 
        t.checkExpect(this.sw.isCaptionAvailable("English"), true)
        && t.checkExpect(this.old.isCaptionAvailable("Spanish"), false)
        && t.checkExpect(this.b99.isCaptionAvailable("French"), true)
        && t.checkExpect(this.despacito.isCaptionAvailable("Russian"), false);
  }
  
  boolean testFormat(Tester t) {
    return 
        t.checkExpect(this.sw.format(), "Star Wars (1977)")
        && t.checkExpect(this.b99.format(), "Brooklyn Nine Nine 1.6 - Halloween")
        && t.checkExpect(this.despacito.format(), "Despacito by Luis Fonsi");
  }
}









