package exceptions;

import java.io.IOException;

public class WordleDictionaryLoaderException extends IOException {
    public WordleDictionaryLoaderException(String message) {
        super(message);
    }
}