package dev.codesoapbox.dummy4j;

import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLogging {

    private TestLogging() {
    }

    public static TestLogHandler mockLogging(Level logLevel) {
        LogManager.getLogManager().reset();
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(logLevel);
        TestLogHandler handler = new TestLogHandler();
        rootLogger.addHandler(handler);

        return handler;
    }

    public static final class TestLogHandler extends Handler {

        private final List<String> logs = new ArrayList<>();

        @Override
        public void publish(LogRecord record) {
            logs.add(MessageFormatter.arrayFormat(record.getMessage(), record.getParameters()).getMessage());
        }

        public void assertContains(String message) {
            assertTrue(logs.contains(message),
                    () -> "Message '" + message + "' was not found in logs:\n"
                            + String.join(",\n", logs) + "\n");
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }
    }
}
