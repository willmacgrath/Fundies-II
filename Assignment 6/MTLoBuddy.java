
// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
  MTLoBuddy() {
  }

  // Inserts a buddy to the empty buddy list
  public ILoBuddy insert(Person buddy) {
    return new ConsLoBuddy(buddy, this);
  }

  // Checks if given list contains this person
  public boolean contains(Person buddy) {
    return false;
  }

  // Returns a list without the names that have already showed up
  public ILoBuddy removeBlacklist(ILoBuddy blacklist) {
    return this;
  }

  // Compare a buddy to an empty list, so false
  public boolean hasDirectBuddyH(String name) {
    return false;
  }

  // Returns the count of an empty list
  public int countCommonBuddiesH(Person that, int count) {
    return count;
  }

  // Checks if this list of buddies has this person
  public boolean hasExtendedBuddyH(Person that, ILoBuddy blacklist) {
    return false;
  }

  // Return the count of an empty list
  public int countFriends(int count) {
    return count;
  }

  // Calls countFriends on an empty list
  public int countAll(int count) {
    return count;
  }

}

