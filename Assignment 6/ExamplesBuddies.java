import tester.*;

// runs tests for the buddies problem
class ExamplesBuddies {
  Person Ann;
  Person Bob;
  Person Cole;
  Person Dan;
  Person Ed;
  Person Fay;
  Person Gabi;
  Person Hank;
  Person Jan;
  Person Kim;
  Person Len;

  void initBuddies() {
    this.Ann = new Person("Ann");
    this.Bob = new Person("Bob");
    this.Cole = new Person("Cole");
    this.Dan = new Person("Dan");
    this.Ed = new Person("Ed");
    this.Fay = new Person("Fay");
    this.Gabi = new Person("Gabi");
    this.Hank = new Person("Hank");
    this.Jan = new Person("Jan");
    this.Kim = new Person("Kim");
    this.Len = new Person("Len");

    // Ann's buddies are Bob and Cole
    this.Ann.addBuddy(Bob);
    this.Ann.addBuddy(Cole);

    // Bob's buddies are Ann and Ed and Hank
    this.Bob.addBuddy(Ann);
    this.Bob.addBuddy(Ed);
    this.Bob.addBuddy(Hank);

    // Cole's buddy is Dan
    this.Cole.addBuddy(Dan);

    // Dan's buddy is Cole
    this.Dan.addBuddy(Cole);

    // Ed's buddy is Fay
    this.Ed.addBuddy(Fay);

    // Fay's buddies are Ed and Gabi
    this.Fay.addBuddy(Ed);
    this.Fay.addBuddy(Gabi);

    // Gabi's buddies are Ed and Fay
    this.Gabi.addBuddy(Ed);
    this.Gabi.addBuddy(Fay);

    // Hank does not have any buddies

    // Jan's buddies are Kim and Len
    this.Jan.addBuddy(Kim);
    this.Jan.addBuddy(Len);

    // Kim's buddies are Jan and Len
    this.Kim.addBuddy(Jan);
    this.Kim.addBuddy(Len);

    // Len's buddies are Jan and Kim
    this.Len.addBuddy(Jan);
    this.Len.addBuddy(Kim);

  }

  void testRemoveBlackList(Tester t) {
    this.initBuddies();
    ILoBuddy ListOfBuddies = new ConsLoBuddy(this.Ann,
        new ConsLoBuddy(this.Ed, new ConsLoBuddy(this.Hank, new MTLoBuddy())));
    ILoBuddy ListofBlacklist = new ConsLoBuddy(this.Ann,
        new ConsLoBuddy(this.Bob, new MTLoBuddy()));
    ILoBuddy Result = new ConsLoBuddy(this.Ed, new ConsLoBuddy(this.Hank, new MTLoBuddy()));

    t.checkExpect(ListOfBuddies.removeBlacklist(ListofBlacklist), Result);
  }

  void testInsert(Tester t) {
    this.initBuddies();
    ILoBuddy Expected = new ConsLoBuddy(this.Bob, new ConsLoBuddy(this.Cole, new MTLoBuddy()));

    ILoBuddy LoBuddies = new MTLoBuddy();
    LoBuddies = LoBuddies.insert(Bob);
    LoBuddies = LoBuddies.insert(Cole);

    t.checkExpect(LoBuddies, Expected);

  }

  void testAddBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(this.Ann.buddies,
        new ConsLoBuddy(this.Bob, new ConsLoBuddy(this.Cole, new MTLoBuddy())));
    this.Ann.addBuddy(Dan);
    t.checkExpect(this.Ann.buddies, new ConsLoBuddy(this.Bob,
        new ConsLoBuddy(this.Cole, new ConsLoBuddy(this.Dan, new MTLoBuddy()))));
  }

  void testHasDirectBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(this.Bob.hasDirectBuddy(Kim), false);
    t.checkExpect(this.Ann.hasDirectBuddy(Bob), true);
    t.checkExpect(this.Ann.hasDirectBuddy(Cole), true);
    this.Ann.addBuddy(Dan);
    t.checkExpect(this.Ann.hasDirectBuddy(Dan), true);
    this.Ann.addBuddy(Len);
    t.checkExpect(this.Ann.hasDirectBuddy(Len), true);
  }

  void testHasDirectBuddyH(Tester t) {
    this.initBuddies();
    ILoBuddy LoB = new ConsLoBuddy(this.Bob, new ConsLoBuddy(this.Cole, new MTLoBuddy()));
    t.checkExpect(LoB.hasDirectBuddyH("Kim"), false);
    t.checkExpect(LoB.hasDirectBuddyH("Bob"), true);
  }

  void testCountCommonBuddies(Tester t) {
    this.initBuddies();
    t.checkExpect(this.Jan.countCommonBuddies(Bob), 0);
    t.checkExpect(this.Jan.countCommonBuddies(Kim), 1);
    this.Bob.addBuddy(Len);
    this.Bob.addBuddy(Kim);
    t.checkExpect(this.Jan.countCommonBuddies(Bob), 2);
  }

  void testCountCommonBuddiesH(Tester t) {
    this.initBuddies();
    ILoBuddy Expected = new ConsLoBuddy(this.Ed, new ConsLoBuddy(this.Gabi, new MTLoBuddy()));
    t.checkExpect(Expected.countCommonBuddiesH(Gabi, 0), 1);
    t.checkExpect(Expected.countCommonBuddiesH(Len, 0), 0);
  }

  void testHasExtendedBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(this.Ann.hasExtendedBuddy(Bob), true);
    t.checkExpect(this.Ann.hasExtendedBuddy(Ann), true);
    t.checkExpect(this.Ann.hasExtendedBuddy(Ed), true);
    t.checkExpect(this.Ann.hasExtendedBuddy(Len), false);
    this.Bob.addBuddy(Len);
    t.checkExpect(this.Ann.hasExtendedBuddy(Len), true);
  }

  //  void testHasExtendedBuddyH(Tester t ) {
  //    this.initBuddies();
  //    ILoBuddy LoB = new ConsLoBuddy(this.Bob,
  //        new ConsLoBuddy(this.Cole, new MTLoBuddy())); 
  //    
  //    t.checkExpect(LoB.hasExtendedBuddyH(Ann), true);
  //    t.checkExpect(LoB.hasExtendedBuddyH(Len), false); 
  //    this.Bob.addBuddy(Len);
  //    t.checkExpect(LoB.hasExtendedBuddyH(Len), true); 
  //    this.Len.addBuddy(Fay);
  //    t.checkExpect(LoB.hasExtendedBuddyH(Fay), true); 
  //  }

  //  //Should it count dupes?
  //  void testPartyCount(Tester t) {
  //    this.initBuddies();
  //    t.checkExpect(this.Ann.partyCount(), 7);
  //    t.checkExpect(this.Cole.partyCount(), 2);
  //    t.checkExpect(this.Fay.partyCount(), 1);
  //  }

  //void testCountAll(Tester t) {
  //this.initBuddies();
  //
  //}

  void testCountFriends(Tester t) {
    this.initBuddies();
    t.checkExpect(this.Ann.buddies().countFriends(0), 2);
    t.checkExpect(this.Bob.buddies().countFriends(0), 3);
    t.checkExpect(this.Cole.buddies().countFriends(0), 1);

  }

  void testUsername(Tester t) {
    this.initBuddies();
    t.checkExpect(this.Ann.username(), "Ann");
    t.checkExpect(this.Bob.username(), "Bob");
    t.checkExpect(this.Cole.username(), "Cole");
  }

  void testBuddies(Tester t) {
    this.initBuddies();
    t.checkExpect(this.Ann.buddies(), new ConsLoBuddy(Bob, new ConsLoBuddy(Cole, new MTLoBuddy())));
    t.checkExpect(this.Bob.buddies(),
        new ConsLoBuddy(Ann, new ConsLoBuddy(Ed, new ConsLoBuddy(Hank, new MTLoBuddy()))));
    t.checkExpect(this.Cole.buddies(), new ConsLoBuddy(Dan, new MTLoBuddy()));
  }

  void testSame(Tester t) {
    this.initBuddies();
    t.checkExpect(this.Ann.same(Ann), true);
    t.checkExpect(this.Bob.same(Dan), false);
  }

  void testSaysTo(Tester t) {
    this.Ann.diction = (0.47);
    this.Bob.diction = (0.82);
    this.Dan.hearing = (0.7);
    
    t.checkInexact(this.Ann.saysTo(Dan), .33, 0.01);
    t.checkInexact(this.Bob.saysTo(Dan), .57, 0.01);
  }
  
  void testMaxLikelihood(Tester t) {
    this.initBuddies();
    t.checkExpect(this.Ann.maxLikelihood(Dan), 3.0);
    t.checkExpect(this.Ann.maxLikelihood(Bob), 3.0);
    t.checkExpect(this.Ann.maxLikelihood(Cole), 3.0);
  }
}