package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class WordleGame {
    private static final int MASK_LENGTH = 5;
    private String answer;
    private int steps;
    private final WordleDictionary  dictionary;

    private final Set<String> invalidWords = new HashSet<>();

    public WordleGame(WordleDictionary dictionary, int steps) {
        this.answer = dictionary.getRandomWord();
        this.steps = steps;
        this.dictionary = dictionary;
    }

    public String checkWord(String word) {
        StringBuilder check = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char wordChar = word.charAt(i);
            char answerChar = answer.charAt(i);
            if (wordChar == answerChar) {
                check.append("+");
            } else if (answer.indexOf(wordChar) != -1) {
                check.append("^");
            } else {
                check.append("-");
            }
        }
        return check.toString();
    }

    public String getHint(String mask, List<String> userInputs) {
        if (mask.equals("-----")) {
            return dictionary.getRandomWord();
        }

        List<String> allAppropriateWords = new ArrayList<>();
        for (String word : dictionary.getWords()) {
            if (!userInputs.contains(word)) {
                allAppropriateWords.add(word);
            }
        }
        List<String> hintWords = new ArrayList<>();

        for (String word : allAppropriateWords) {
            if (!invalidWords.contains(word) && checkMask(word, mask)) {
                hintWords.add(word);
            } else {
                invalidWords.add(word);
            }
        }
        if (hintWords.isEmpty()) {
            return "";
        } else {
            Random random = new Random();
            return hintWords.get(random.nextInt(hintWords.size()));
        }
    }

    public boolean checkMask(String word, String mask) {
        for (int i = 0; i < MASK_LENGTH; i++) {
            char wChar = word.charAt(i);
            char mChar = mask.charAt(i);
            char aChar = answer.charAt(i);

            switch (mChar) {
                case '+':
                    if (wChar != aChar) {
                        return false;
                    }
                    break;
                case '^':
                    if (!answer.contains(wChar + "")) {
                        return false;
                    }
                    break;
                case '-':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    public String getAnswer() {
        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public void considerAttempt() {
        steps--;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
