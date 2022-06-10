
// represents a Person with a user name and a list of buddies
class Person {
  String username;
  ILoBuddy buddies;
  double diction;
  double hearing;

  Person(String username) {
    this.username = username;
    this.buddies = new MTLoBuddy();
    this.diction = 0.0;
    this.hearing = 0.0;
  }

  // Multiply the diction by the hearing to get max likelihood
  Person(String username, double diction, double hearing) {
    this.username = username;
    this.buddies = new MTLoBuddy();
    this.diction = diction;
    this.hearing = hearing;
  }

  // Returns the persons username
  String username() {
    return this.username;
  }

  // Checks if two people are the same
  boolean same(Person other) {
    return this.username.equals(other.username);
  }

  // Returns the list of buddies
  ILoBuddy buddies() {
    return this.buddies;
  }

  // EFFECT:
  // Change this person's buddy list so that it includes the given person
  void addBuddy(Person buddy) {
    this.buddies = this.buddies.insert(buddy);
  }

  // returns true if this Person has that person as a direct buddy
  boolean hasDirectBuddy(Person that) {
    return this.buddies.hasDirectBuddyH(that.username);
  }

  // returns the number of people who will show up at the party
  // given by this person
  int partyCount() {
    return 1 + this.buddies.countAll(0);

  }

  // returns the number of people that are direct buddies
  // of both this and that person
  int countCommonBuddies(Person that) {
    return this.buddies.countCommonBuddiesH(that, 0);
  }

  // will the given person be invited to a party
  // organized by this person?
  boolean hasExtendedBuddy(Person that) {
    return this.hasDirectBuddy(that)
        || this.buddies.hasExtendedBuddyH(that, new ConsLoBuddy(this, new MTLoBuddy()));
  }

  // Dan is person we are talking about

  // returns the maximum likelihood that this person can correctly convey
  // to that person.

  //multiplies a person's diction with their target's hearing
  double saysTo(Person that) {
    return this.diction * that.hearing;
  }

  //the maximum likelihood a person's messae will be delivered
  double maxLikelihood(Person that) {
    if (this.hasExtendedBuddy(that)) {
      return this.saysTo(that);
    } else {
      return 0.0;
    }
  }    
}
