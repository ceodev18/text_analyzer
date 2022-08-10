package com.example.analisistexxt.service;

import com.example.analisistexxt.model.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AnalizeServiceImpl implements AnalizeService {
    @Override
    public Result analizeText(String text) {
        Result result = new Result();

        if(text.isEmpty())return result;

        HashMap<Character, Integer> map = new HashMap<>();//aproach: use for getting top 10

        //counting whole chars
        result.setTotal_chars(text.length());

        for (int i = 0; i < text.length(); i++) {
            if (Character.isLetter(text.charAt(i))) {
                result.setAlphabetCharsCount();
            } else if (Character.isDigit(text.charAt(i))) {
                result.setNumericCharsCount();
            } else if (Character.isWhitespace(text.charAt(i))) {
                result.setWhitespaceCharsCount();
            }

            if (Character.isLowerCase(text.charAt(i))) {
                result.setLowerCharsCount();
            }
            if (Character.isUpperCase(text.charAt(i))) {
                result.setUpperCharsCount();
            }
            //for each char that is not whitespace wi will count and
            //then get the percentage in method setTopTen
            if(!Character.isWhitespace(text.charAt(i))){
                if (!map.containsKey(text.charAt(i))) {
                    map.put(text.charAt(i), 1);
                } else {
                    map.put(text.charAt(i), map.get(text.charAt(i)) + 1);
                }
            }

        }

        //calling method for chars punctuation
        result.setPunctuationCharsCount(text);

        //getting words base on regex
        String[] words = text.split("\\s+");
        result.setTotal_words(words.length);

        //calling method for getting top 10
        result.setTopTen(map);

        return result;
    }
}
