package com.labi.typing.service;

import com.labi.typing.DTO.GeneratedTestDTO;
import com.labi.typing.DTO.TestRegisterDTO;
import com.labi.typing.exception.custom.UserNotFoundException;
import com.labi.typing.model.Test;
import com.labi.typing.model.User;
import com.labi.typing.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private UserService userService;

    private final List<String> shortWords;
    private final List<String> mediumWords;
    private final List<String> longWords;

    public TestService() throws Exception {
        shortWords = extractWords("src/main/resources/typingtest-words/short_words.txt");
        mediumWords = extractWords("src/main/resources/typingtest-words/medium_words.txt");
        longWords = extractWords("src/main/resources/typingtest-words/long_words.txt");
    }

    public void saveTest(TestRegisterDTO testRegisterDTO) {
        if (userService.findByUsername(testRegisterDTO.user().username()) == null) {
            throw new UserNotFoundException();
        }
        User user = userService.findByUsername(testRegisterDTO.user().username());
        Test test = mapTestRegisterDTOToTest(testRegisterDTO);
        test.setUser(user);

        testRepository.save(test);
        scoreService.registerScore(test);
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
        List<String> test = new ArrayList<>();

        Random random = new Random();

        generateRandomWords(test, shortWords, amountShortWords, random);
        generateRandomWords(test, mediumWords, amountMediumWords, random);
        generateRandomWords(test, longWords, amountLongWords, random);

        return shuffleList(test);
    }

    private void generateRandomWords(List<String> test, List<String> sourceWords, int amount, Random random) {
        List<String> availableWords = new ArrayList<>(sourceWords);
        for (int i = 0; i < amount; i++) {
            String randomWord = availableWords.remove(random.nextInt(availableWords.size()));
            test.add(randomWord);
        }
    }

    private List<String> shuffleList(List<String> list) {
        List<String> shuffledList = new ArrayList<>(list);
        Random random = new Random();
        for (int i = shuffledList.size() - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            String temp = shuffledList.get(index);
            shuffledList.set(index, shuffledList.get(i));
            shuffledList.set(i, temp);
        }
        return shuffledList;
    }

    private List<String> extractWords(String path) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return List.of(br.readLine().split(","));
        }
        catch (Exception e) {
            throw new Exception("Error while reading file");
        }
    }

    private Test mapTestRegisterDTOToTest(TestRegisterDTO testRegisterDTO) {
        return new Test(
                testRegisterDTO.testDate(),
                testRegisterDTO.testText(),
                testRegisterDTO.totalWords(),
                testRegisterDTO.finishedTime(),
                testRegisterDTO.totalLetters(),
                testRegisterDTO.incorrectLetters(),
                testRegisterDTO.testDifficulty(),
                null
        );
    }
}
