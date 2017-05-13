package ru.eninja.terminal.terminal;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import ru.eninja.terminal.commands.CommandMapping;
import ru.eninja.terminal.exceptions.CommandProcessorException;
import ru.eninja.terminal.exceptions.InvalidCommandException;
import ru.eninja.terminal.modules.TerminalModule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Обработчик команд, поступивших на вход в терминал.
 * Вызывает соответствующий введенной команде метод с соответствующими параметрами.
 */
public class CommandProcessor {

    /**
     * Словарь команд на метод, соответствующий команде
     */
    private final Map<String, Method> commandToMethodMap = new HashMap<>();
    /**
     * Словарь методов на объекты, которые должны вызвать эти методы
     */
    private final Map<Method, Object> methodToInvokerMap = new HashMap<>();
    /**
     * Регулярное выражение определяющиее команды,без вычета кавычек (их нужно убирать дополнительно)
     */
    private final Pattern commandPattern = Pattern.compile("(\"[^\"]+\"|[^\\s\"]+)");

    /**
     * Конструктор обработчика команд. Выполняет поиск методов, помеченных аннотацией {@link CommandMapping}
     * в классах реализации {@link TerminalModule}, представленных в множестве moduleClasses,
     * а также в классе {@link Terminal}.
     *
     * @param terminal      терминал, чьи команды обрабатывает создаваемый объект
     * @param moduleClasses множество классов - модулей для терминала
     * @throws CommandProcessorException в случае ошибки, свзяанной с обработкой команды
     */
    public CommandProcessor(Terminal terminal, Set<Class<? extends TerminalModule>> moduleClasses)
            throws CommandProcessorException {

        // scan terminal commands
        Reflections terminalReflections = new Reflections(Terminal.class, new MethodAnnotationsScanner());
        for (Method method : terminalReflections.getMethodsAnnotatedWith(CommandMapping.class)) {
            for (String command : method.getAnnotation(CommandMapping.class).value()) {
                commandToMethodMap.put(command, method);
            }
            methodToInvokerMap.put(method, terminal);
        }

        // scan commands of modules
        for (Class<?> clazz : moduleClasses) {
            Object invoker;
            try {
                invoker = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new CommandProcessorException();
            }

            Reflections classReflections = new Reflections(clazz, new MethodAnnotationsScanner());
            for (Method method : classReflections.getMethodsAnnotatedWith(CommandMapping.class)) {
                for (String command : method.getAnnotation(CommandMapping.class).value()) {
                    commandToMethodMap.put(command, method);
                }
                methodToInvokerMap.put(method, invoker);
            }
        }
    }

    /**
     * Принимает на вход каманду в таком виде, в каком ее ввел пользователь,
     * проводит её синтаксический разбор на тело команды и её аргументы
     * соответственно регулярному выражению commandPattern. Затем ищет метод, соответсвующий команде,
     * которую ввёл пользователь и передаёт управление в метод executeMethod.
     *
     * @param nonParsedCommandGroup строка команды в исходном виде
     * @throws InvalidCommandException в случае, если пользователь ввёл несуществующую команду
     *                                 или команду с неверными аргументами
     */
    public void execute(String nonParsedCommandGroup) throws InvalidCommandException {
        Matcher matcher = commandPattern.matcher(nonParsedCommandGroup);
        if (!matcher.find()) {
            return;
        }

        // parsing
        String command = matcher.group();
        List<String> args = new LinkedList<>();
        while (matcher.find()) {
            args.add(matcher.group().replaceAll("\"", ""));
        }

        // get method for command and execute
        Method method = commandToMethodMap.get(command);
        if (method == null) {
            throw new InvalidCommandException("Invalid command name");
        }
        executeMethod(method, args);
    }

    /**
     * Вызывает метод method с параметрами argList
     *
     * @param method  метод который нужно вызвать
     * @param argList параметры вызываемого метода
     * @throws InvalidCommandException в случае, если метод невозможно вызвать с такими параметрами
     */
    private void executeMethod(Method method, List<String> argList) throws InvalidCommandException {
        try {
            Object invoker = methodToInvokerMap.get(method);
            Parameter[] parameters = method.getParameters();
            Object[] arguments;

            // varargs?
            if ((parameters.length == 1) && parameters[0].getType().equals(String[].class)) {
                arguments = new Object[]{argList.toArray(new String[argList.size()])};
            } else {
                arguments = argList.toArray(new Object[argList.size()]);
            }

            method.invoke(invoker, arguments);
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            throw new InvalidCommandException("Invalid arguments", e);
        }
    }
}
