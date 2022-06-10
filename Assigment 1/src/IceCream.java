interface IIceCream {
}

class EmptyServing implements IIceCream {
  boolean cone;

  EmptyServing(boolean cone) {
    this.cone = cone;
  }
}

class Scooped implements IIceCream {
  IIceCream more;
  String flavor;

  Scooped(IIceCream more, String flavor) {
    this.more = more;
    this.flavor = flavor;
  }
}

class ExamplesIceCream {
  ExamplesIceCream(){}

  IIceCream order1cup = new EmptyServing(false);
  IIceCream order1scoop1 = new Scooped(order1cup, "caramel swirl");
  IIceCream order1scoop2 = new Scooped(order1scoop1, "black raspberry");
  IIceCream order1scoop3 = new Scooped(order1scoop2, "coffee");
  IIceCream order1 = new Scooped(order1scoop3, "mint chip");

  IIceCream order2cone = new EmptyServing(true);
  IIceCream order2scoop1 = new Scooped(order2cone, "strawberry");
  IIceCream order2scoop2 = new Scooped(order2scoop1, "vanilla");
  IIceCream order2 = new Scooped(order2scoop2, "chocolate");
}





