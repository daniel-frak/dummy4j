package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoremDummy {

    private final Dummy4j dummy4j;

    public LoremDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * @return a string of random characters
     */
    public String characters(int numberOfCharacters) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numberOfCharacters; i++) {
            builder.append(character());
        }
        return builder.toString();
    }

    /**
     * @return a random character
     */
    public String character() {
        return dummy4j.expressionResolver().resolveKey("lorem.characters");
    }

    /**
     * @return a sentence of word count between {@code minWords} and {@code maxWords}
     */
    public String sentence(int minWords, int maxWords) {
        int numberOfWords = dummy4j.number().nextInt(minWords, maxWords);

        return sentence(numberOfWords);
    }

    /**
     * @return a sentence with the given number of words
     */
    public String sentence(int numberOfWords) {
        String sentence = sentenceWithoutPunctuation(numberOfWords);

        return capitalize(sentence) + sentenceEndingPunctuation();
    }

    private String capitalize(String value) {
        return value.substring(0, 1).toUpperCase(Locale.ENGLISH) + value.substring(1);
    }

    private String sentenceEndingPunctuation() {
        // Reduce the chance of punctuation marks other than a full stop
        if (dummy4j.number().nextInt(15) > 1) {
            return ".";
        }
        return dummy4j.expressionResolver().resolveKey("lorem.additional_sentence_ending_punctuation");
    }

    private String sentenceWithoutPunctuation(int numberOfWords) {
        List<String> elements = new ArrayList<>();
        for (int i = 0; i < numberOfWords; i++) {
            elements.add(word());
        }
        return String.join(" ", elements);
    }

    /**
     * @return a single random word
     */
    public String word() {
        return dummy4j.expressionResolver().resolveKey("lorem.word");
    }

    /**
     * @return a random paragraph
     */
    public String paragraph() {
        int numberOfParagraphs = dummy4j.number().nextInt(3, 10);

        return paragraph(numberOfParagraphs);
    }

    /**
     * @return a paragraph with the given number of sentences
     */
    public String paragraph(int numberOfSentences) {
        List<String> sentences = new ArrayList<>();
        for (int i = 0; i < numberOfSentences; i++) {
            sentences.add(sentence());
        }

        return String.join(" ", sentences);
    }

    /**
     * @return a random sentence
     */
    public String sentence() {
        int numberOfWords = dummy4j.number().nextInt(3, 10);
        return sentence(numberOfWords);
    }
}
