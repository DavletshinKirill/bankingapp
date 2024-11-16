package dev.davletshin.calculator.service;

import dev.davletshin.calculator.domain.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class LogData {
    private final Logger logger;
    private static final LogData logData = new LogData();

    private LogData() {
        logger = LoggerFactory.getLogger(LogData.class);
    }

    public static LogData getInstance() {
        return logData;
    }

    public void logInfo(Object object, String info, LogLevel logLevel) {
        String timestamp = new SimpleDateFormat("dd.MM.yy HH:mm:ss").format(new Date());
        switch (logLevel) {
            case INFO -> logger.info(String.format("Info: [%s] %s %s", timestamp, info, object.toString()));
            case ERROR -> logger.error(String.format("Error: [%s] %s", timestamp, info, object.toString()));
        }
    }
}
