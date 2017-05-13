package ru.eninja.terminal.terminal;

import org.reflections.Reflections;
import ru.eninja.terminal.modules.TerminalModule;

import java.util.Set;


/**
 * Конфигурация терминала {@link Terminal}.
 * Выполняет поиск а затем хранит в себе множество классов - модулей терминала.
 */
public class TerminalConfiguration {

    /**
     * Множество модулей терминала
     */
    private Set<Class<? extends TerminalModule>> moduleClasses;

    /**
     * Выполняет поиск модулей для терминала в пакетах modulePackages.
     *
     * @param modulePackages пакеты, которые могут содержать модули для терминала
     */
    public TerminalConfiguration(String... modulePackages) {
        for (String pkg : modulePackages) {
            Reflections packageReflections = new Reflections(pkg);
            moduleClasses = packageReflections.getSubTypesOf(TerminalModule.class);
        }
    }

    /**
     * @return множество модулей терминала
     */
    public Set<Class<? extends TerminalModule>> getModuleClasses() {
        return moduleClasses;
    }
}
