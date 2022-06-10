
interface IHousing {
}

class Hut implements IHousing {
  int capacity;
  int population;
  
  Hut(int capacity, int population) {
    this.capacity = capacity;
    this.population = population;
  }
}

class Inn implements IHousing {
  String name;
  int capacity;
  int population;
  int stalls;

  Inn(String name, int capacity, int population, int stalls) {
    this.name = name;
    this.capacity = capacity;
    this.population = population;
    this.stalls = stalls;
  }
}

class Castle implements IHousing {
  String name;
  String familyName;
  int population;
  int carriageHouse;

  Castle(String name, String familyName, int population, int carriageHouse) {
    this.name = name;
    this.familyName = familyName;
    this.population = population;
    this.carriageHouse = carriageHouse;
  }
}

interface ITransportation {
}

class Horse implements ITransportation {
  IHousing from;
  IHousing to;
  String name;
  String color;

  Horse(IHousing from, IHousing to, String name, String color) {
    this.from = from;
    this.to = to;
    this.name = name;
    this.color = color;
  }
}

class Carriage implements ITransportation {
  IHousing from;
  IHousing to;
  int tonnage;

  Carriage(IHousing from, IHousing to, int tonnage) {
    this.from = from;
    this.to = to;
    this.tonnage = tonnage;
  }
}

class ExamplesTravel {
  ExamplesTravel() {}
  
  IHousing hovel = new Hut(5, 1);
  IHousing winterfell = new Castle("Winterfell", "Stark", 500, 6);
  IHousing crossroads = new Inn("Inn At The Crossroads", 40, 20, 12);
  IHousing shack = new Hut(4, 3);
  IHousing blackgate = new Castle("Blackgate", "Clipper", 250, 10);
  IHousing bridgewaye = new Inn("Bridgewaye Inn", 18, 8, 6);
  ITransportation horse1 = new Horse(hovel, blackgate, "Jonny", "brown");
  ITransportation horse2 = new Horse(winterfell, shack, "White Lightning", "Black");
  ITransportation carriage1 = new Carriage(winterfell, crossroads, 4);
  ITransportation carriage2 = new Carriage(blackgate, bridgewaye, 8);
}








