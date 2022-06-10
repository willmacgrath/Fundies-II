// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

  Person first;
  ILoBuddy rest;

  ConsLoBuddy(Person first, ILoBuddy rest) {
    this.first = first;
    this.rest = rest;
  }

  // Inserts a buddy to the given buddy list
  public ILoBuddy insert(Person buddy) {
    return new ConsLoBuddy(this.first, this.rest.insert(buddy));
  }

  // Checks if given list contains this person
  public boolean contains(Person buddy) {
    return this.first.same(buddy) || this.rest.contains(buddy);
    
  }

  // Returns a list without the names that have already showed up
  public ILoBuddy removeBlacklist(ILoBuddy blacklist) {
    if (blacklist.contains(this.first)) {
      return this.rest.removeBlacklist(blacklist);
    }
    else {
      return new ConsLoBuddy(this.first, this.rest.removeBlacklist(blacklist));
    }
  }

  // Returns true if this Person has that as a direct buddy
  public boolean hasDirectBuddyH(String name) {
    return this.first.username().equals(name) || this.rest.hasDirectBuddyH(name);
  }

  // counts the number of people in common and returns the count
  public int countCommonBuddiesH(Person that, int count) {
    if (that.hasDirectBuddy(first)) {
      return this.rest.countCommonBuddiesH(that, count + 1);
    }
    else {
      return this.rest.countCommonBuddiesH(that, count);
    }
  }

  // Checks if this list of buddies has this person as a direct friend
  public boolean hasExtendedBuddyH(Person that, ILoBuddy blacklist) {
    if (this.first.same(that)) {
      return true;
    } else if (!blacklist.contains(this.first)) {
      return first.hasExtendedBuddy(that);
    }
    else {
      return this.rest.removeBlacklist(blacklist).hasExtendedBuddyH(that,
          new ConsLoBuddy(this.first, blacklist));
    }
  }
  
  //this.first.hasExtendedBuddy(that))

  // Count the number of friends a given person has
  public int countFriends(int count) {
    return (count + 1) + this.rest.countFriends(count);
  }

  // Calls countFriends on a list of buddies
  public int countAll(int count) {
    return (1 + this.first.buddies().countFriends(count)) 
        + this.rest.countAll(count);
  }
}

