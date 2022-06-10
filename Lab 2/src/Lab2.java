
import tester.Tester;

// to represent a pet owner
class Person {
    String name;
    IPet pet;
    int age;
 
    Person(String name, IPet pet, int age) {
        this.name = name;
        this.pet = pet;
        this.age = age;
    }
    
    /* TEMPLATE
    Fields:
    ... this.name ...    -- String
    ... this.pet ...     -- pet
    ... this.age ...     -- int

    Methods:
    ... this.age(Person) ... -- boolean
    ... this.pet(Person) ... -- boolean
    ... Person ... -- new Person
 */
    //is this Person older than the given Person?
    boolean isOlder(Person other) {
      return this.age > other.age;
    }
    // does the name of this person's pet match the given name?
    boolean sameNamePet(String givenPetName) {
      return this.pet.samePetName(givenPetName);
      
    }
    //produces a new person whose pet has perished
    Person perish() {
      return new Person(this.name, new NoPet(), this.age);
    }

}
// to represent a pet
interface IPet { 
  //is the givenPet Name the same as the current pet's name
  boolean samePetName(String givenPetName);
  }
 

// to represent a pet cat
class Cat implements IPet  {
    String name;
    String kind;
    boolean longhaired;
 
    Cat(String name, String kind, boolean longhaired) {
        this.name = name;
        this.kind = kind;
        this.longhaired = longhaired;
    }
    
    /* TEMPLATE
    Fields:
    ... this.name ...    -- String
    ... this.kind ...     -- String
    ... this.longhaired ...     -- boolean

    Methods:
    ... this.name(Pet) ... -- boolean
 */
    @Override
    public boolean samePetName(String givenPetName) {
      return givenPetName.equals(this.name);
    }
}
 
// to represent a pet dog
class Dog implements IPet {
    String name;
    String kind;
    boolean male;
 
    Dog(String name, String kind, boolean male) {
        this.name = name;
        this.kind = kind;
        this.male = male;
    }
    /* TEMPLATE
    Fields:
    ... this.name ...    -- String
    ... this.kind ...     -- String
    ... this.male ...     -- boolean

    Methods:
    ... this.name(Pet) ... -- boolean
 */
    @Override
    public boolean samePetName(String givenPetName) {
      return givenPetName.equals(this.name);
    }
    
}

class NoPet implements IPet { 

  
  NoPet() {
 
}

  @Override
  public boolean samePetName(String givenPetName) {
    // TODO Auto-generated method stub
    return false;
  }}



class ExamplesPerson {
  Dog timothy = new Dog("Timothy", "Golden Retriever", true);
  Person carl = new Person("Carl", this.timothy, 12);
  
  Dog bert = new Dog("Bert", "Poodle", false);
  Person ellie = new Person("Ellie", this.bert, 30);
  
  Cat lucky = new Cat("Lucky", "Siberian", true);
  Person steve = new Person("Steve", this.lucky, 24);
  
  Cat oj = new Cat("Lucky", "Sphynx", false);
  Person charlie = new Person("Charlie", this.oj, 20);
  
  NoPet noPet1 = new NoPet();
  Person chris = new Person("Chris", this.noPet1, 22);
  
  NoPet noPet2 = new NoPet();
  Person paulo = new Person("Paulo", this.noPet2, 42);
  
  
  
  boolean testIsOlder(Tester t) {
    return t.checkExpect(ellie.isOlder(carl), true) &&
        t.checkExpect(carl.isOlder(ellie), false) &&
        t.checkExpect(steve.isOlder(charlie), true) &&
        t.checkExpect(charlie.isOlder(ellie), false);
          
  }
  boolean testSameNamePet(Tester t) {
    return t.checkExpect(carl.sameNamePet("Timothy"), true) &&
        t.checkExpect(steve.sameNamePet("Marshmello"), false);
  }
  boolean testPerish(Tester t) {
    return t.checkExpect(carl.perish(), new Person("Carl", new NoPet(), 12))&&
        t.checkExpect(charlie.perish(), new Person("Charlie", new NoPet(), 20));    
  }

}