package com.example.analisistexxt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Result implements Serializable {
    @JsonProperty(value = "total_chars")
    private int total_chars;

    @JsonProperty(value = "total_numeric_chars")
    private int total_numeric_chars;

    @JsonProperty(value = "total_alpha_chars")
    private int total_alpha_chars;

    @JsonProperty(value = "total_uppercase_alpha_chars")
    private int total_uppercase_alpha_chars;

    @JsonProperty(value = "total_words")
    private int total_words;

    @JsonProperty(value = "total_punctuation_space")
    private int total_punctuation_space;

    @JsonProperty(value = "total_white_space")
    private int total_white_space;

    @JsonProperty(value = "total_lowercase_alpha_chars")
    private int total_lowercase_alpha_chars;

    @JsonProperty(value = "top_10")
    private HashMap<Character, Map<String, Number>> top_10;

    private final static int TOP_TEN = 10;

    public Result() {
        this.total_numeric_chars = 0;
        this.total_alpha_chars = 0;
        this.total_uppercase_alpha_chars = 0;
        this.total_words = 0;
        this.total_punctuation_space = 0;
        this.total_white_space = 0;
        this.total_lowercase_alpha_chars = 0;
    }

    public void setNumericCharsCount() {
        this.total_numeric_chars += 1;
    }

    public void setAlphabetCharsCount() {
        this.total_alpha_chars += 1;
    }

    public void setUpperCharsCount() {
        this.total_uppercase_alpha_chars += 1;
    }

    public void setLowerCharsCount() {
        this.total_lowercase_alpha_chars += 1;
    }

    public void setWhitespaceCharsCount() {
        this.total_white_space += 1;
    }

    public void setPunctuationCharsCount(String text) {
        Pattern p = Pattern.compile("[\\p{Punct}]");
        Matcher m = p.matcher(text);
        while (m.find()) {
            this.total_punctuation_space += 1;
        }

    }

    public HashMap<Character, Map<String, Number>> getTop_10() {
        return top_10;
    }

    public void setTop_10(HashMap<Character, Map<String, Number>> top_10) {
        this.top_10 = top_10;
    }


    @Override
    public String toString() {
        return "Result{" +
                "total_chars=" + total_chars +
                ", total_numeric_chars=" + total_numeric_chars +
                ", total_alpha_chars=" + total_alpha_chars +
                ", total_uppercase_alpha_chars=" + total_uppercase_alpha_chars +
                ", total_words=" + total_words +
                ", total_punctuation_space=" + total_punctuation_space +
                ", total_white_space=" + total_white_space +
                ", total_lowercase_alpha_chars=" + total_lowercase_alpha_chars +
                '}';
    }

    public void setTotal_words(int total_words) {
        this.total_words = total_words;
    }

    public void setTotal_chars(int total_chars) {
        this.total_chars = total_chars;
    }

    public void setTopTen(@NotNull HashMap<Character, Integer> mapCounter) {
        //filter top ten
        Map<Character, Integer> topTen =
                mapCounter.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(TOP_TEN)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        setTop_10(createJsonTopTen(topTen));
    }

    private HashMap<Character, Map<String, Number>> createJsonTopTen(Map<Character, Integer> topTen) {
        //creating response
        HashMap<Character, Map<String, Number>> completed = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : topTen.entrySet()) {
            Map<String, Number> content = new HashMap<>();
            content.put("count", entry.getValue());
            float percentage = (float) entry.getValue() / (allCharactersExceptWhiteSpace()) * 100;
            content.put("percentage",new BigDecimal(percentage).setScale(2, RoundingMode.HALF_UP));
            completed.put(entry.getKey(), content);
        }
        return completed;
    }

    public int allCharactersExceptWhiteSpace() {
        return this.total_numeric_chars + this.total_punctuation_space + this.total_alpha_chars;
    }
}
