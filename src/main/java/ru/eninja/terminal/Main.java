package ru.eninja.terminal;

import ru.eninja.terminal.exceptions.TerminalException;
import ru.eninja.terminal.terminal.Terminal;
import ru.eninja.terminal.terminal.TerminalConfiguration;


public class Main {
    public static void main(String[] args) throws TerminalException {
        TerminalConfiguration cfg = new TerminalConfiguration("ru.eninja.terminal.modules");
        new Thread(new Terminal(cfg)).start();
    }
}
