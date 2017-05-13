package ru.eninja.terminal.exceptions;


/**
 * Исключение файлового менеджера как модуля терминала.
 */
public class FileManagerException extends TerminalModuleException {
    public FileManagerException() {
    }

    public FileManagerException(String message) {
        super(message);
    }

    public FileManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileManagerException(Throwable cause) {
        super(cause);
    }

    public FileManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
