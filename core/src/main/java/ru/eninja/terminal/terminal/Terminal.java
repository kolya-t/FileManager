package ru.eninja.terminal.terminal;

import ru.eninja.terminal.exceptions.CommandProcessorException;
import ru.eninja.terminal.exceptions.InvalidCommandException;
import ru.eninja.terminal.commands.CommandMapping;
import ru.eninja.terminal.exceptions.TerminalException;
import ru.eninja.terminal.modules.TerminalModule;

import java.util.Scanner;


/**
 * Терминал. Может иметь модули - реализации {@link TerminalModule},
 * которыми можно управлять с помощью команд, вводимых с клавиатуры.
 */
public class Terminal implements Runnable {

    /**
     * Обработчик вводимых команд
     */
    private CommandProcessor commandProcessor;
    /**
     * Программа работает пока истинно
     */
    private boolean running;

    /**
     * Принимает на вход экземпляр {@link TerminalConfiguration}, содержащий множество классов модулей терминала.
     *
     * @param cfg конфигурация терминала
     * @throws TerminalException в случае ошибки создания экземпляра модуля
     */
    public Terminal(TerminalConfiguration cfg) throws TerminalException {
        try {
            this.commandProcessor = new CommandProcessor(this, cfg.getModuleClasses());
        } catch (CommandProcessorException e) {
            throw new TerminalException(e);
        }
    }

    /**
     * Основная логика терминала. Запускать лучше через вызов start() экземпляра {@link Thread}.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        running = true;
        while (running) {
            System.out.print("> ");
            try {
                commandProcessor.execute(scanner.nextLine().trim());
            } catch (InvalidCommandException e) {
                System.out.println("Неверная команда!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Команда остановки работы терминала
     */
    @CommandMapping({"stop", "exit", "quit"})
    public void stop() {
        running = false;
    }
}
