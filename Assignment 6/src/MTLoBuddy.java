
// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
    MTLoBuddy() {}
    
    public ILoBuddy insert(Person buddy) {
      return new ConsLoBuddy (buddy, this);
    }
    
    public boolean hasDirectBuddyH(Person that, String name) {
      return false;
    }
}