package ru.eninja.terminal.exceptions;


/**
 * Исключение при работе модуля терминала
 */
public class TerminalModuleException extends TerminalException {
    public TerminalModuleException() {
    }

    public TerminalModuleException(String message) {
        super(message);
    }

    public TerminalModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public TerminalModuleException(Throwable cause) {
        super(cause);
    }

    public TerminalModuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
