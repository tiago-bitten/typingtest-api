package com.labi.typing.service;

import com.labi.typing.DTO.GeneratedTestDTO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TypingTestService {

    private List<String> shortWords;
    private List<String> mediumWords;
    private List<String> longWords;

    public TypingTestService() {
        this.shortWords = extractWords("src/main/resources/typingtest-words/short_words.txt");
        this.mediumWords = extractWords("src/main/resources/typingtest-words/medium_words.txt");
        this.longWords = extractWords("src/main/resources/typingtest-words/long_words.txt");
    }

    public GeneratedTestDTO getShortTest() {
        return new GeneratedTestDTO(generateTest(5, 3, 2));
    }

    public GeneratedTestDTO getMediumTest() {
        return new GeneratedTestDTO(generateTest(4, 7, 4));
    }

    public GeneratedTestDTO getLongTest() {
        return new GeneratedTestDTO(generateTest(4, 9, 7));
    }

    private List<String> generateTest(int amountShortWords, int amountMediumWords, int amountLongWords) {
        List<String> copyShortWords = new ArrayList<>(shortWords);
        List<String> copyMediumWords = new ArrayList<>(mediumWords);
        List<String> copyLongWords = new ArrayList<>(longWords);

        List<String> test = new ArrayList<>();

        Random random = new Random();

        generateRandomWords(test, copyShortWords, amountShortWords, random);
        generateRandomWords(test, copyMediumWords, amountMediumWords, random);
        generateRandomWords(test, copyLongWords, amountLongWords, random);

        return shuffleList(test);
    }

    private void generateRandomWords(List<String> test, List<String> sourceWords, int amount, Random random) {
        for (int i = 0; i < amount; i++) {
            int index = random.nextInt(sourceWords.size());
            String randomWord = sourceWords.get(index);

            if (!test.contains(randomWord)) {
                test.add(randomWord);
            } else {
                i--;
            }
        }
    }

    private List<String> shuffleList(List<String> list) {
        Random random = new Random();
        for (int i = list.size() - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            String temp = list.get(index);
            list.set(index, list.get(i));
            list.set(i, temp);
        }
        return list;
    }

    private List<String> extractWords(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line = br.readLine();
            return List.of(line.split(","));

        }
        catch (Exception e) {
            throw new RuntimeException("Error while reading file");
        }
    }
}
