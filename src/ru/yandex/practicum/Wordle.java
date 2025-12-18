package ru.yandex.practicum;

import exceptions.WordleDictionaryLoaderException;
import exceptions.LogUtilException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Wordle {
    private static final String WORDS_FILE = "words_ru.txt";
    private static final int COUNT_HINTS = 5;
    private static final int COUNT_STEPS = 6;
    private static final String START_MASK = "-----";
    private static final String WINNING_MASK = "+++++";
    private static final String STOP_WORD = "стоп";

    private final WordleDictionary wd;

    public Wordle() throws WordleDictionaryLoaderException {
        this.wd = WordleDictionaryLoader.loadFromFile(WORDS_FILE);
    }

    public static void main(String[] args) {
        try (LogUtil log = new LogUtil()) {
            log.write("Запуск программы");
            Wordle wordle = new Wordle();
            log.write("Словарь загружен");

            wordle.start(log);
        } catch (WordleDictionaryLoaderException | LogUtilException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private void start(LogUtil log) throws LogUtilException {
        WordleGame game = new WordleGame(wd, COUNT_STEPS);

        log.write("Начало игры");
        log.write("Загадано слово: " + game.getAnswer());
        System.out.println("==================================================");
        System.out.println("Нужно угадать слово из 5 русских букв за " + COUNT_STEPS + " попыток");
        System.out.println("Для подсказки просто нажмите Enter (всего " + COUNT_HINTS + " раз)");
        System.out.println("Введите \"" + STOP_WORD + "\" для выхода из программы");
        System.out.println("==================================================");

        Scanner scanner = new Scanner(System.in);
        List<String> userWords = new ArrayList<>();
        String mask = START_MASK;
        int countUsedHint = 0;
        while (game.getSteps() > 0) {
            System.out.print("Введите слово (осталось попыток - " + game.getSteps() + "): ");
            String inputWord = WordleDictionary.normalizeWord(scanner.nextLine());
            log.write("Введено слово: " + inputWord);
            if (inputWord.equals(STOP_WORD)) {
                log.write("Принудительный выход из игры");
                System.out.println("Выход из игры");
                break;
            } else if (inputWord.isEmpty()) {
                if (countUsedHint < COUNT_HINTS) {
                    log.write("Запрос подсказки");
                    inputWord = game.getHint(mask, userWords);
                    if (inputWord.isEmpty()) {
                        log.write("Слов для подсказки не найдено");
                        System.out.println("Слов для подсказки не найдено");
                    } else {
                        countUsedHint++;
                        log.write(String.format("Подсказка (%d из %d): %s", countUsedHint, COUNT_HINTS, inputWord));
                        System.out.printf("Подсказка (%d из %d): %s\n", countUsedHint, COUNT_HINTS, inputWord);
                    }
                } else {
                    log.write("Использованы все доступные подсказки");
                    System.out.println("Использованы все доступные подсказки");
                    continue;
                }

            } else if (!inputWord.matches("[а-яёА-ЯЁ]{5}")) {
                log.write("Ошибка - ожидается слово из 5 русских букв");
                System.out.println("Ошибка - ожидается слово из 5 русских букв");
                continue;
            } else if (!wd.getWords().contains(inputWord)) {
                log.write("Такое слово отсутствует в словаре");
                System.out.println("Такое слово отсутствует в словаре");
                continue;
            } else if (userWords.contains(inputWord)) {
                log.write("Такое слово уже было введено");
                System.out.println("Такое слово уже было введено");
                continue;
            }

            userWords.add(inputWord);
            game.considerAttempt();

            for (String userWord : userWords) {
                String tempMask = game.checkWord(userWord);
                System.out.println(userWord + " [" + tempMask + "]");
            }
            mask = game.checkWord(inputWord);
            log.write("Совпадение: " + mask);

            if (mask.equals(WINNING_MASK)) {
                log.write("Вы выиграли!");
                System.out.println("Вы выиграли!");
                return;
            }
        }
        if (game.getSteps() == 0) {
            log.write("Вы проиграли. Правильное слово: " + game.getAnswer());
            System.out.println("Вы проиграли. Правильное слово: " + game.getAnswer());
        }
    }
}
