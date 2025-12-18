package ru.yandex.practicum;

import java.util.List;
import java.util.Random;

public class WordleDictionary {
    private final List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getWords() {
        return words;
    }

    public static String wordNormalization(String word) {
        return word.trim().toLowerCase().replace('ё', 'е');
    }
}
