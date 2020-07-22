package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @since 0.4.0
 */
public class NatoPhoneticAlphabetDummy {

    private static final Map<Character, String> natoAlphabet = new HashMap<>();

    static {
        natoAlphabet.put('a', "Alpha");
        natoAlphabet.put('b', "Bravo");
        natoAlphabet.put('c', "Charlie");
        natoAlphabet.put('d', "Delta");
        natoAlphabet.put('e', "Echo");
        natoAlphabet.put('f', "Foxtrot");
        natoAlphabet.put('g', "Golf");
        natoAlphabet.put('h', "Hotel");
        natoAlphabet.put('i', "India");
        natoAlphabet.put('j', "Juliett");
        natoAlphabet.put('k', "Kilo");
        natoAlphabet.put('l', "Lima");
        natoAlphabet.put('m', "Mike");
        natoAlphabet.put('n', "November");
        natoAlphabet.put('o', "Oscar");
        natoAlphabet.put('p', "Papa");
        natoAlphabet.put('q', "Quebec");
        natoAlphabet.put('r', "Romeo");
        natoAlphabet.put('s', "Sierra");
        natoAlphabet.put('t', "Tango");
        natoAlphabet.put('u', "Uniform");
        natoAlphabet.put('v', "Victor");
        natoAlphabet.put('w', "Whiskey");
        natoAlphabet.put('x', "X-ray");
        natoAlphabet.put('y', "Yankee");
        natoAlphabet.put('z', "Zulu");
        natoAlphabet.put('1', "One");
        natoAlphabet.put('2', "Two");
        natoAlphabet.put('3', "Three");
        natoAlphabet.put('4', "Four");
        natoAlphabet.put('5', "Five");
        natoAlphabet.put('6', "Six");
        natoAlphabet.put('7', "Seven");
        natoAlphabet.put('8', "Eight");
        natoAlphabet.put('9', "Nine");
        natoAlphabet.put('0', "Zero");
    }

    private final Dummy4j dummy4j;

    public NatoPhoneticAlphabetDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public String word() {
        String[] values = natoAlphabet.values().toArray(new String[0]);
        return values[dummy4j.random().nextInt(values.length - 1)];
    }

    public String of(String text) {
        StringJoiner joiner = new StringJoiner(" ");
        text.toLowerCase().chars()
                .forEach(c -> joiner.add(natoAlphabet.get((char) c)));
        return joiner.toString();
    }
}
