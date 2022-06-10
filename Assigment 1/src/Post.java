

class Post {
  String user;
  String text;
  int likes;
  int timeStamp;
  
  Post(String user, String text, int likes, int timeStamp) {
    this.user = user;
    this.text = text;
    this.likes = likes;
    this.timeStamp = timeStamp;
  }
}

class ExamplesPost {
  ExamplesPost() {}

  Post personalNews = new Post("iheartfundies", 
      "Some personal news: I will be taking "
      + "fundies 2 this fall", 200, 1625699955);
  Post cupcakeAd = new Post("thequeenscups", 
      "life is too short to not eat cupcakes", 48, 1631661555);
  Post weatherUpdate = new Post("theweatherman", 
      "it is going to be a lot warmer starting next week", 445, 1635867569);
}