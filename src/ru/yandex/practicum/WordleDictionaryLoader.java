package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import exceptions.WordleDictionaryLoaderException;

public class WordleDictionaryLoader {
    private WordleDictionaryLoader() {
    }

    public static WordleDictionary loadFromFile(String fileName) throws WordleDictionaryLoaderException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            List<String> words = new ArrayList<>();
            while (br.ready()) {
                String word = WordleDictionary.normalizeWord(br.readLine());
                if (word.matches("[а-яёА-ЯЁ]{5}")) {
                    words.add(word);
                }
            }
            return new WordleDictionary(words);
        } catch (IOException e) {
            throw new WordleDictionaryLoaderException("Ошибка чтения файла словаря: " + fileName);
        }
    }
}
