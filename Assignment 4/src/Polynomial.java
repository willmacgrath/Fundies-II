
import tester.Tester;

interface ILoMonomial { 
  
  ILoMonomial samePolynomial(Polynomial that);
  
  ILoMonomial polyInsert(Monomial that);
}

class MtLoMonomial implements ILoMonomial { 
  MtLoMonomial() {
  }

  @Override
  public ILoMonomial samePolynomial(Polynomial that) {
    return this;
  }

  @Override
  public ILoMonomial polyInsert(Monomial that) {
    return this;
  }
}

class ConsLoMonomial implements ILoMonomial { 
  Monomial first;
  ILoMonomial rest;
  
  ConsLoMonomial(Monomial first, ILoMonomial rest) {
  }

  @Override
 //sorts a list in alphabetical order
 public ILoMonomial samePolynomial(Polynomial that) {
    return this.rest.samePolynomial(that)
       .polyInsert(this.first); 
  }

  // compares the two strings and inserts the word into othe right place
  public ILoMonomial polyInsert(Monomial that) {
    if (this.first.degree <= that.degree) {
      return new ConsLoMonomial(this.first, this.rest.polyInsert(that));
    }
    else {
      return new ConsLoMonomial(that, this);
    }
  }
}

class Monomial {
  int degree;
  int coefficient;
  
  Monomial(int degree, int coefficient) {
    if (degree >= 0) {
      this.degree = degree;
    }
    else { 
      throw new IllegalArgumentException("Invalid Degree" + Integer.toString(degree)); 
    }
    this.coefficient = coefficient;
  }
}

class Polynomial {
  ILoMonomial monomials;
  
  Polynomial(ILoMonomial monomials) {
    this.monomials = monomials;
  }
}

class ExamplesPolynomial {
  ExamplesPolynomial() {    
  }
  
  Monomial mono1 = new Monomial(2,3);
  Monomial mono2 = new Monomial(1,4);
  Monomial mono3 = new Monomial(3,2);
  Monomial mono4 = new Monomial(2,1);
  
  ILoMonomial noDupes = new ConsLoMonomial(mono1,
      new ConsLoMonomial(mono2,
          new ConsLoMonomial(mono3,
                   new MtLoMonomial())));

  ILoMonomial dupes = new ConsLoMonomial(mono1,
      new ConsLoMonomial(mono2,
          new ConsLoMonomial(mono3,
              new ConsLoMonomial(mono4,
                   new MtLoMonomial()))));

  boolean testSamePolynomial(Tester t) {
    return 
      t.checkExpect(this.noDupes.samePolynomial(new Polynomial(noDupes)), true);
      
  }
}


