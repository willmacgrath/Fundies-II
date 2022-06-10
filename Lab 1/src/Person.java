//+----------------+
//| Person         |
//+----------------+
//| String name    |
//| int age        |
//| String gender  |
//| Address address|
//+----------------+

class Address {
  String city;
  String state;
  
  Address (String city, String state) {
    this.city = city;
    this.state = state;
  }
 }


class Person {
  String name;
  int age;
  String gender;
  Address address;
  
  Person(String name, int age, String gender, Address address) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.address = address;
  }
}


class ExamplesPerson {
  ExamplesPerson () {}
  
  Address forTim = new Address ("Boston", "MA");
  Address forKate = new Address ("Warwick", "RI");
  Address forRebecca = new Address ("Nashua", "NH");
  Address forWill = new Address ("Marshfield", "MA");
  Address forJohn = new Address ("Miami", "FL");
  Person tim = new Person ("Tim", 23, "Male", this.forTim);
  Person kate = new Person ("Kate", 22, "Female", this.forKate);
  Person rebecca = new Person ("Rebecca", 31, "Non-binary", this.forRebecca);
  Person will = new Person ("Will", 19, "Male", this.forWill);
  Person john= new Person ("John", 72, "Female", this.forJohn);
}
