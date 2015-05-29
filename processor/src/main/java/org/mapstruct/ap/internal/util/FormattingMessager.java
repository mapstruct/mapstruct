/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
     * annotation mirror of the annotated element.
     *
     * @param e    the annotated element
     * @param a    the annotation to use as a position hint
     * @param msg  the message
     * @param args Arguments referenced by the format specifiers in the format string. If there are more arguments
     * than format specifiers, the extra arguments are ignored
     *
     */
    void printMessage(Element e, AnnotationMirror a, Message msg, Object... args);

    /**
     * Prints a message of the specified kind at the location of the
     * annotation value inside the annotation mirror of the annotated
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
}
