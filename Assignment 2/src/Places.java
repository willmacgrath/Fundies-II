import tester.Tester;

// to represent a place that contains features
class Place {
  String name;
  ILoFeature features;

  // the constructor
  Place(String name, ILoFeature features) {
    this.name = name;
    this.features = features;
  }

  double foodinessRating() {
    if (this.features.featureNumber() == 0) {
      return 0.0;
    }
    else {
      return this.features.totalRating() / this.features.featureNumber();
    }
  }

  int featureNumber() {
    return this.features.featureNumber();
  }

  double totalRating() {
    return this.features.totalRating();
  }

  String restaurantInfo() {
    return this.features.restaurantNameType();
  }

  //In Place
  /*
   * TEMPLATE: Fields: 
   * ... this.name ... -- String 
   * ... this.features ... -- ILoFeature
   * 
   * Methods: 
   * ... this.foodinessRating()... -- double
   * 
   * Methods for fields: 
   * ... this.features.featureNumber() ... -- int 
   * ... this.features.totalRating() ... -- double 
   * ... this.features.restaurantNameType... -- String
   */

}

// To represent a list of features 
interface ILoFeature {
  int featureNumber();

  double totalRating();

  String restaurantNameType();

  //In ILoFeature
  /*
   * TEMPLATE: Fields: n/a
   * 
   * Methods: ... this.featureNumber()... -- int ... this.totalRating()... --
   * double ... this.restaurantNameType -- String
   * 
   * Methods for fields:
   */
}

// To represent a feature
interface IFeature {
  int featureNumber();

  double totalRating();

  String restaurantNameType();

  // In IFeature
  /*
   * TEMPLATE: Fields: n/a
   * 
   * Methods: 
   * ... this.featureNumber()... -- int 
   * ... this.totalRating()... -- double 
   * ... this.restaurantNameType -- String
   * 
   * Methods for fields:
   */
}

// to represent an empty list
class MtLoFeature implements ILoFeature {
  // constructor
  MtLoFeature() {
  }

  @Override
  public int featureNumber() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double totalRating() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String restaurantNameType() {
    // TODO Auto-generated method stub
    return "";
  }

  //In MtLoFeature
  /*
   * TEMPLATE: Fields: n/a
   * 
   * Methods: 
   * ... this.featureNumber()... -- int 
   * ... this.totalRating()... -- double
   * ... this.restaurantNameType -- String
   * 
   * Methods for fields:
   */
}

// to represent a list with objects in it
class ConsLoFeature implements ILoFeature {
  IFeature first;
  ILoFeature rest;

  // constructor
  ConsLoFeature(IFeature first, ILoFeature rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  public int featureNumber() {
    // TODO Auto-generated method stub
    return this.first.featureNumber() + this.rest.featureNumber();
  }

  @Override
  public double totalRating() {
    // TODO Auto-generated method stub
    return this.first.totalRating() + this.rest.totalRating();
  }

  @Override
  public String restaurantNameType() {
    // TODO Auto-generated method stub
    if (this.first.restaurantNameType().equals("")) {
      return this.rest.restaurantNameType();
    }
    else if (this.rest.restaurantNameType().equals("")) {
      return this.first.restaurantNameType() + this.rest.restaurantNameType();
    }
    else {
      return this.first.restaurantNameType() + ", " + this.rest.restaurantNameType();
    }
  }

  // In ConsLoFeatures
  /*
  * TEMPLATE: Fields: 
  * ...this.first... -- IFeature 
  * ...this.rest... -- ILoFeature
  * 
  * Methods: 
  * ... this.featureNumber()... -- int 
  * ... this.totalRating()... -- double 
  * ... this.restaurantNameType -- String
  * 
  * Methods for fields: n/a
  */
}

// to represent a restaurant
class Restaurant implements IFeature {
  String name;
  String type;
  double averageRating;

  // constructor
  Restaurant(String name, String type, double averageRating) {
    this.name = name;
    this.type = type;
    this.averageRating = averageRating;
  }

  // adds 1 to the amount of restaurants
  public int featureNumber() {
    // TODO Auto-generated method stub
    return 1;
  }

  // adds the rating to the total of restaurant ratings
  public double totalRating() {
    // TODO Auto-generated method stub
    return this.averageRating;
  }

  // combines the name of the restaurant and the type into a name(type) format
  public String restaurantNameType() {
    return this.name + "(" + this.type + ")";
  }

  // In Restaurants
  /*
  * TEMPLATE: Fields: ...this.name... -- String ...this.type... -- String
  * ...this.averageRating... -- double
  * 
  * Methods: ... this.featureNumber()... -- int ... this.totalRating()... --
  * double ... this.restaurantNameType -- String
  * 
  * Methods for fields: n/a
  */
}

// to represent a venue  
class Venue implements IFeature {
  String name;
  String type;
  int capacity;

  //constructor
  Venue(String name, String type, int capacity) {
    this.name = name;
    this.type = type;
    this.capacity = capacity;
  }

  @Override
    public int featureNumber() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
    public double totalRating() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
    public String restaurantNameType() {
    // TODO Auto-generated method stub
    return "";
  }

  // In Venue
  /*
   * TEMPLATE: Fields:
   *  ...this.name... -- String 
   *  ...this.type... -- String
   * ...this.capacity... -- int
   * 
   * Methods: 
   * ... this.featureNumber()... -- int
   *  ... this.totalRating()... -- double 
   * ... this.restaurantNameType -- String
   * 
   * Methods for fields: n/a
   */
}

// to represent a shuttle bus from one place to another
class ShuttleBus implements IFeature {
  String name;
  Place destination;

  // constructor
  ShuttleBus(String name, Place destination) {
    this.name = name;
    this.destination = destination;
  }

  // returns the amount of restaurants the ShuttleBuses can access from a Place
  public int featureNumber() {
    // TODO Auto-generated method stub
    return this.destination.featureNumber();
  }

  // returns the total average rating of restaurants the ShuttleBuses can access
  // from a Place
  public double totalRating() {
    // TODO Auto-generated method stub
    return this.destination.totalRating();
  }

  public String restaurantNameType() {
    // TODO Auto-generated method stub
    return "";
  }

  // In Restaurants
  /*
   * TEMPLATE: Fields: ...this.name... -- String ...this.destination... -- Place
   * 
   * Methods: ... this.featureNumber()... -- int ... this.totalRating()... --
   * double ... this.restaurantNameType -- String
   * 
   * Methods for fields: n/a
   */
}

/*
 * A map of Marshfield (my town) The first place is the Town Center. In the Town
 * Center there is a venue called "Levitate Backyard", which has is a "stage"
 * and can hold 100 people. There is also a 3.4 rated restaurant called
 * "Dairy Queen" that serves "Ice Cream." Another Place is called
 * "Humarock Beach." At Humarock Beach there is a restaurant called "The Voyage"
 * which serves "Breakfast" and it is rated 3.7 stars. There is a shuttle bus
 * from here called "Centered" that brings you back to the Town Center. There is
 * also an additional Shuttle Bus that only caters to the elderly that also
 * brings them to the Town Center called "WeDrive". The third Place is called
 * the The Nature Trails has a venue called "The Campground" that is a
 * "campground" that can hold 50 people. There is a shuttle bus that brings you
 * from there to the Town Center called "Back to Life", and there is another
 * called "Sandy Beaches" that brings you to Humarock Beach.
 */

// To represent examples of Places
class ExamplesPlaces {
  ExamplesPlaces() {
  }

  Place townCenter = new Place("Marshfield",
      new ConsLoFeature(new Venue("Levitate Backyard", "stage", 100),
          new ConsLoFeature(new Restaurant("Dairy Queen", "Ice Cream", 3.4), new MtLoFeature())));
  Place humBeach = new Place("Humarock Beack",
      new ConsLoFeature(new Restaurant("The Voyage", "Breakfast", 3.7),
          new ConsLoFeature(new ShuttleBus("Centered", townCenter),
              new ConsLoFeature(new ShuttleBus("WeDrive", townCenter), new MtLoFeature()))));
  Place natureTrails = new Place("Nature Trails",
      new ConsLoFeature(new Venue("The Campground", "capmground", 50),
          new ConsLoFeature(new ShuttleBus("Back to Life", townCenter),
              new ConsLoFeature(new ShuttleBus("Sandy Beaches", humBeach), new MtLoFeature()))));

  Place northEnd = new Place("North End", new ConsLoFeature(
      new Venue("TD Garden", "stadium", 19580),
      new ConsLoFeature(new Restaurant("The Daily Catch", "Sicilian", 4.4), new MtLoFeature())));
  Place harvard = new Place("Harvard",
      new ConsLoFeature(new ShuttleBus("Freshmen-15", northEnd),
          new ConsLoFeature(new Restaurant("Border Cafe", "Tex-Mex", 4.5), new ConsLoFeature(
              new Venue("Harvard Stadium", "football", 30323), new MtLoFeature()))));
  Place southStation = new Place("South Station", new ConsLoFeature(
      new ShuttleBus("Little Italy Express", northEnd),
       new ConsLoFeature(new Restaurant("Regina's Pizza", "pizza", 4.0), new ConsLoFeature(
          new ShuttleBus("Crimson Cruiser", harvard),
            new ConsLoFeature(new Venue("Boston Common", "public", 150000), new MtLoFeature())))));
  Place cambridgeSide = new Place("CambridgeSide Galleria",
      new ConsLoFeature(new Restaurant("Sarku Japan", "teriyaki", 3.9), new ConsLoFeature(
          new Restaurant("Starbucks", "coffee", 4.1),
            new ConsLoFeature(new ShuttleBus("bridge shuttle", southStation), new MtLoFeature()))));

  boolean testFoodinessRating(Tester t) {
    return t.checkExpect(northEnd.foodinessRating(), 4.4)
          && t.checkExpect(harvard.foodinessRating(), 4.45)
          && t.checkExpect(southStation.foodinessRating(), 4.325);
  }

  boolean testFeatureNumber(Tester t) {
    return t.checkExpect(northEnd.featureNumber(), 1)
          && t.checkExpect(harvard.featureNumber(), 2);
  }

  boolean testTotalRating(Tester t) {
    return t.checkExpect(northEnd.totalRating(), 4.4)
          && t.checkExpect(harvard.totalRating(), 8.9);
  }

  boolean testrestaurantInfo(Tester t) {
    return t.checkExpect(northEnd.restaurantInfo(), "The Daily Catch(Sicilian)") && t
          .checkExpect(harvard.restaurantInfo(), "Border Cafe(Tex-Mex), The Daily Catch(Sicilian)");
  }
}


// The reason that some methods here double count some of the data is becuase of the way
// that the shuttlebusses work. For example, there are two buses that go to the North
// End, "Freshmen-15" and "Little ItalyExpress". If the example beng used is the Place South Station
// both buses will be counted, and therefore all features in the North End will count twice. 
