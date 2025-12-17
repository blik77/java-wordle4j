package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class WordleGameTest {
    private WordleGame wg;
    private WordleDictionary wd;

    @BeforeEach
    public void init() {
        List<String> dictionaryList = List.of("азарт", "столб", "рельс", "книги", "медок", "книга", "аймак");
        wd = new WordleDictionary(dictionaryList);
        wg = new WordleGame(wd, 6);
    }

    @Test
    public void shouldReturnTrueWhenWordCorrect() {
        wg.setAnswer("столб");
        Assertions.assertNotEquals("+++++", wg.checkWord("медок"));
        Assertions.assertEquals("+++++", wg.checkWord("столб"));
    }

    @Test
    public void shouldGiveCorrectHintWord() {
        wg.setAnswer("книга");
        Assertions.assertEquals("книги", wg.getHint("++++-", List.of()));
    }
}
