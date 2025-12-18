package ru.yandex.practicum;

import exceptions.LogUtilException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LogUtil implements AutoCloseable {
    private final PrintWriter printWriter;

    public LogUtil() throws LogUtilException {
        String nameLogFile = "log";
        String extensionLogFile = "txt";
        String formatDate = "yyyy.MM.dd_HH.mm.ss";

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(formatDate));
        String logFileName = String.format("%s %s.%s", nameLogFile, timestamp, extensionLogFile);
        try {
            this.printWriter = new PrintWriter(
                new FileWriter(logFileName, StandardCharsets.UTF_8, true)
            );
        } catch (IOException e) {
            throw new LogUtilException("Ошибка создания лог-файла: " + logFileName);
        }
    }

    public LogUtil(PrintWriter printWriter) throws LogUtilException {
        this.printWriter = printWriter;
    }

    public void write(String message) throws LogUtilException {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        printWriter.printf("%s: %s\n", time, message);
    }

    @Override
    public void close() {
        printWriter.close();
    }
}