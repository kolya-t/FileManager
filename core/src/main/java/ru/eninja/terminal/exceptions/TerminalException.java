package ru.eninja.terminal.exceptions;


/**
 * Исключение работы терминала
 */
public class TerminalException extends Exception {
    public TerminalException() {
    }

    public TerminalException(String message) {
        super(message);
    }

    public TerminalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TerminalException(Throwable cause) {
        super(cause);
    }

    public TerminalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
