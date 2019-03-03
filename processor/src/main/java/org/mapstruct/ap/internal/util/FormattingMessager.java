/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

/**
 * Prints out diagnostics raised by the annotation processor. Messages are Java format strings taking the given
 * arguments for interpolation.
 *
 * @author Sjaak Derksen
 */
public interface FormattingMessager {

    /**
     * Prints a message of the specified kind.
     *
     * @param msg  the message
     * @param args Arguments referenced by the format specifiers in the format string. If there are more arguments
     * than format specifiers, the extra arguments are ignored
     */
    void printMessage(Message msg, Object... args);

    /**
     * Prints a message of the specified kind at the location of the
     * element.
     *
     * @param e    the element to use as a position hint
     * @param msg  the message
     * @param args Arguments referenced by the format specifiers in the format string. If there are more arguments
     * than format specifiers, the extra arguments are ignored
     */
    void printMessage(Element e, Message msg, Object... args);

    /**
     * Prints a message of the specified kind at the location of the
     * annotation positionHint of the annotated element.
     *
     * @param e    the annotated element
     * @param a    the annotation to use as a position hint (can be null)
     * @param msg  the message
     * @param args Arguments referenced by the format specifiers in the format string. If there are more arguments
     * than format specifiers, the extra arguments are ignored
     *
     */
    void printMessage(Element e, AnnotationMirror a, Message msg, Object... args);

    /**
     * Prints a message of the specified kind at the location of the
     * annotation value inside the annotation positionHint of the annotated
     * element.
     *
     * @param e    the annotated element
     * @param a    the annotation containing the annotation value
     * @param v    the annotation value to use as a position hint
     * @param msg  the message
     * @param args Arguments referenced by the format specifiers in the format string. If there are more arguments
     * than format specifiers, the extra arguments are ignored
     */
    void printMessage(Element e,
                      AnnotationMirror a,
                      AnnotationValue v,
                      Message msg,
                      Object... args);

    /**
     * Just log as plain note
     * @param log
     */
    void log(int level, String log);
}
