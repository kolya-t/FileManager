package ru.eninja.terminal.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Этой аннотацией помечается метод, который выполнится при вводе команды value.
 * Аннотация похожа на RequestMapping в SpringFramework.
 * <ul>Ограничения на параметры методов, помеченных аннотацией:
 * <li>Без параметров</li>
 * <li>Один и более параметров типа {@link String}</li>
 * <li>Единственный параметр переменной длины типа {@link String}</li>
 * </ul>
 * На одну команду можно определить только один метод. TODO: возможно заменить в будущем
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandMapping {

    /**
     * Массив идентификаторов команды, по которым можно вызвать эту команду
     */
    String[] value();
}
