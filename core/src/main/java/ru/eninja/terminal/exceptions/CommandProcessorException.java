package ru.eninja.terminal.exceptions;


/**
 * Исключение обработчика команд терминала
 */
public class CommandProcessorException extends TerminalException {
    public CommandProcessorException() {
    }

    public CommandProcessorException(String message) {
        super(message);
    }

    public CommandProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandProcessorException(Throwable cause) {
        super(cause);
    }

    public CommandProcessorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
