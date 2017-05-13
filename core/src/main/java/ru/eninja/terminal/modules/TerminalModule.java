package ru.eninja.terminal.modules;


import ru.eninja.terminal.commands.CommandMapping;

/**
 * Модуль терминала. Должен содержать методы, помеченные аннотацией {@link CommandMapping}.
 * С помощью этих методов и происходит управление модулем.
 */
public abstract class TerminalModule {

    /**
     * Модуль должен иметь конструктор по-умолчанию
     */
    public TerminalModule() {
    }
}
