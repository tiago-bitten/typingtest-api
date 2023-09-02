package com.labi.typing.service;

import com.labi.typing.DTO.test.TestGeneratedDTO;
import com.labi.typing.DTO.test.TestRegisterDTO;
import com.labi.typing.exception.custom.FileReadException;
import com.labi.typing.exception.custom.ValidationException;
import com.labi.typing.model.Test;
import com.labi.typing.model.User;
import com.labi.typing.repository.TestRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final List<String> shortWords;
    private final List<String> mediumWords;
    private final List<String> longWords;

    public TestService() throws Exception {
        shortWords = extractWords("src/main/resources/typingtest-words/short_words.txt");
        mediumWords = extractWords("src/main/resources/typingtest-words/medium_words.txt");
        longWords = extractWords("src/main/resources/typingtest-words/long_words.txt");
    }

    public void saveTest(TestRegisterDTO testRegisterDTO, String authHeader) {
        String token = jwtTokenProvider.resolveToken(authHeader);
        User user = userService.findByUsername(jwtTokenProvider.validateToken(token));
        if (user == null) {
            throw new ValidationException("User not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Test test = mapTestRegisterDTOToTest(testRegisterDTO);
        test.setUser(user);

        testRepository.save(test);
        scoreService.registerScore(test);
    }

    public TestGeneratedDTO getShortTest() {
        return new TestGeneratedDTO(generateTest(5, 3, 2));
    }

    public TestGeneratedDTO getMediumTest() {
        return new TestGeneratedDTO(generateTest(4, 7, 4));
    }

    public TestGeneratedDTO getLongTest() {
        return new TestGeneratedDTO(generateTest(4, 9, 7));
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
            throw new FileReadException();
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
