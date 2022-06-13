import tester.Tester;

import java.util.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {

  // The original list of characters to be encoded
  ArrayList<Character> alphabet = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand;

  // Give it a random object, new random with given seed
  PermutationCode(ArrayList<Character> code, int initialSeed) {
    Random rand = new Random(initialSeed);
    this.rand = rand;
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with a new permutation code
  PermutationCode() {
    this.rand = new Random();
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
    this.rand = new Random();
  }

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    return this.initEncoderH(this.code, this.alphabet);
  }

  // Initializes the encoding permutation with the given alphabet
  // And the created code
  ArrayList<Character> initEncoderH(ArrayList<Character> encode, ArrayList<Character> alphabet) {
    if (alphabet.size() == 0) {
      return code;
    }
    else {
      int i = this.rand.nextInt(alphabet.size());
      code.add(alphabet.get(i));
      alphabet.remove(i);
      return this.initEncoderH(code, alphabet);
    }

  }

  // produce an encoded String from the given String
  String encode(String notCoded) {
    return this.encodeH(notCoded, "");
  }

  // Helper function that encodes the message and adds it to a new string
  String encodeH(String notCoded, String encoded) {
    for (int i = 0; i < notCoded.length(); i++) {

      Character currentChar = notCoded.charAt(i);

      if (this.alphabet.contains(notCoded.charAt(i))) {
        encoded = encoded.concat(((code.get(alphabet.indexOf(currentChar))).toString()));
      }
      else {
        encoded = encoded + currentChar;
      }
    }
    return encoded;
  }

  // produce a decoded String from the given String
  String decode(String code) {
    return this.decodeH(code, "");
  }

  // Helper function that decodes the message and adds it to a new string
  String decodeH(String encoded, String result) {
    for (int i = 0; i < encoded.length(); i++) {

      Character currentChar = encoded.charAt(i);

      if (this.alphabet.contains(encoded.charAt(i))) {
        result = result.concat(((alphabet.get(code.indexOf(currentChar))).toString()));
      }
      else {
        result = result + currentChar;
      }
    }
    return result;
  }
}

class ExamplesCode { 
  ExamplesCode() {}

  void testEncodeAndHelper(Tester t) {
    ArrayList<Character> encoded = new ArrayList<Character>(
        Arrays.asList('v', 'y', 'l', 'n', 'm', 'u', 'j', 'd', 'k', 'g', 'p', 'f', 'x', 'c', 'h',
            'o', 'b', 'z', 'r', 'i', 'a', 'e', 'w', 't', 's', 'q'));

    String NotCodedMessage = new String("i love pancakes");
    String CodedMessage = new String("k fhem ovclvpmr");
    String HelperExpected = new String("#$%! school k fhem ovclvpmr");

    ArrayList<Character> AssignmentCode = new ArrayList<Character>(
        Arrays.asList('b', 'e', 'a', 'c', 'd'));
    String NotCodedMessage2 = new String("badace");
    String CodedMessage2 = new String("ebcbad");
    String HelperExpected2 = new String("this is the code from the assignment: ebcbad");

    PermutationCode EncodedA = new PermutationCode(encoded);
    PermutationCode EncodedB = new PermutationCode(AssignmentCode);

    t.checkExpect(EncodedA.encode(NotCodedMessage), CodedMessage);
    t.checkExpect(EncodedB.encode(NotCodedMessage2), CodedMessage2);

    t.checkExpect(EncodedA.encodeH(NotCodedMessage, ""), CodedMessage);
    t.checkExpect(EncodedA.encodeH(NotCodedMessage, "#$%! school "), HelperExpected);
    t.checkExpect(EncodedB.encodeH(NotCodedMessage2, "this is the code from the assignment: "),
        HelperExpected2);

  }

  void testDecodeandHelper(Tester t) {
    ArrayList<Character> encoded1 = new ArrayList<Character>(
        Arrays.asList('c', 'b', 'z', 'a', 'm', 'q', 'j', 'd', 'e', 'v', 'p', 'w', 'o', 'g', 'y',
            'x', 'h', 'l', 'r', 'i', 'n', 'k', 'f', 't', 's', 'u'));

    String encodedMessage = new String("abeedc");
    String decodedMessage = new String("dbiiha");

    String encodedMessage2 = new String("e co c jmgenr");
    String decodedMessage2 = new String("i am a genius");

    PermutationCode EncodedA = new PermutationCode(encoded1);

    t.checkExpect(EncodedA.decode(encodedMessage), decodedMessage);
    t.checkExpect(EncodedA.decode(encodedMessage2), decodedMessage2);

    ArrayList<Character> AssignmentCode = new ArrayList<Character>(
        Arrays.asList('b', 'e', 'a', 'c', 'd'));

    String CodedMessage2 = new String("abeedc");
    String DeCodedMessage2 = new String("cabbed");
    String HelperExpected2 = new String("this is the decoded string from the assignment: cabbed");
    PermutationCode EncodedB = new PermutationCode(AssignmentCode);

    t.checkExpect(EncodedB.decodeH(CodedMessage2, ""), DeCodedMessage2);
    t.checkExpect(
        EncodedB.decodeH(CodedMessage2, "this is the decoded string from the assignment: "),
        HelperExpected2);
  }

  void testInitEncoderandHelper(Tester t) {
    ArrayList<Character> alphabet = new ArrayList<Character>(
        Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

    ArrayList<Character> notRandExpected = new ArrayList<Character>(
        Arrays.asList('w', 'o', 'l', 'p', 'a', 'u', 'i', 'j', 'r', 'g', 'q', 'n', 'b', 'e', 'x',
            'f', 'd', 'z', 'y', 'c', 'k', 'v', 't', 's', 'm', 'h'));

    PermutationCode p1 = new PermutationCode(alphabet);
    PermutationCode notRand = new PermutationCode(alphabet, 26);

    t.checkExpect(p1.initEncoder().size(), 52);
    t.checkExpect(notRand.initEncoder(), notRandExpected);
    t.checkExpect(notRand.initEncoder().size(), 26);
  }

  // test Permutation
  void testPermutation(Tester t) {
    ArrayList<Character> copy = new ArrayList<Character>(
        Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

    t.checkExpect(new PermutationCode(copy, 26).code, new PermutationCode(copy, 26).initEncoder());
  }
}
