package ru.eninja.terminal.modules;

import ru.eninja.terminal.commands.CommandMapping;

import java.io.File;
import java.util.Arrays;


public class FileManager extends TerminalModule {

    private File currentDirectory;

    public FileManager() {

    }

    @CommandMapping("cd")
    public void cd(String directory) {
        System.out.println("FileManager.cd");
        System.out.println("directory = " + directory + "");
    }

    @CommandMapping("test")
    public void test(String dir1, String dir2) {
        System.out.println("FileManager.cd");
        System.out.println("dir1 = [" + dir1 + "], dir2 = [" + dir2 + "]");
    }

    @CommandMapping("notepad")
    public void notepad(String... files) {
        System.out.println("FileManager.notepad");
        System.out.println("files = " + Arrays.toString(files));
    }
}
