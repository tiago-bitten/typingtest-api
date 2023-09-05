package com.labi.typing.util;

import com.labi.typing.exception.custom.FileReadException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestUtil {

    private static final List<String> shortWords;
    private static final List<String> mediumWords;
    private static final List<String> longWords;

    static {
        try {
            shortWords = extractWords("src/main/resources/typingtest-words/short_words.txt");
            mediumWords = extractWords("src/main/resources/typingtest-words/medium_words.txt");
            longWords = extractWords("src/main/resources/typingtest-words/long_words.txt");
        }
        catch (Exception e) {
            throw new ExceptionInInitializerError("Error initializing TestUtil");
        }
    }

    public static List<String> generateTest(int amountShortWords, int amountMediumWords, int amountLongWords) {
        List<String> test = new ArrayList<>();

        Random random = new Random();

        generateRandomWords(test, shortWords, amountShortWords, random);
        generateRandomWords(test, mediumWords, amountMediumWords, random);
        generateRandomWords(test, longWords, amountLongWords, random);

        return shuffleList(test);
    }

    private static void generateRandomWords(List<String> test, List<String> sourceWords, int amount, Random random) {
        List<String> availableWords = new ArrayList<>(sourceWords);
        for (int i = 0; i < amount; i++) {
            String randomWord = availableWords.remove(random.nextInt(availableWords.size()));
            test.add(randomWord);
        }
    }

    private static List<String> shuffleList(List<String> list) {
        List<String> shuffledList = new ArrayList<>(list);
        Collections.shuffle(shuffledList);
        return shuffledList;
    }

    private static List<String> extractWords(String path) throws FileReadException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return List.of(br.readLine().split(","));
        }
        catch (IOException e) {
            throw new FileReadException("Error reading words file: " + path);
        }
    }
}
