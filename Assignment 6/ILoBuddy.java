// represents a list of Person's buddies
interface ILoBuddy {

  // Inserts a buddy to the given buddy list
  ILoBuddy insert(Person buddy);

  // Returns a list without the names that have already showed up
  ILoBuddy removeBlacklist(ILoBuddy blacklist);

  // Checks if given list contains this person
  boolean contains(Person buddy);

  // returns true if this Person has that as a direct buddy
  boolean hasDirectBuddyH(String name);

  // counts the number of people in common and returns the count
  int countCommonBuddiesH(Person that, int count);

  // Checks if this list of buddies has this person as a direct friend
  boolean hasExtendedBuddyH(Person that, ILoBuddy blacklist);

  // Counts the number of friends this person has
  int countFriends(int count);

  // Calls countFriends on a list of buddies
  int countAll(int count);

}

