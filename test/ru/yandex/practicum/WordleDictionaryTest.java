package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class WordleDictionaryTest {
    private final List<String> dictionary = List.of("азарт", "азиат", "айван", "аймак", "айран");
    private final WordleDictionary wd = new WordleDictionary(dictionary);

    @Test
    public void shouldGetDictionary() {
        Assertions.assertEquals(dictionary, wd.getWords());
    }
}
