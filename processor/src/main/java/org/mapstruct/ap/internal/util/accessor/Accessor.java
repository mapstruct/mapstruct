/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

/**
 * This represents an Accessor that can be used for writing/reading a property to/from a bean.
 *
 * @author Filip Hrisafov
 */
public interface Accessor {

    /**
     * This returns the type that this accessor gives as a return.
     *
     * e.g. The {@link ExecutableElement#getReturnType()} if this is a method accessor,
     * or {@link javax.lang.model.element.VariableElement#asType()} for field accessors.
     *
     * @return the type that the accessor gives as a return
     */
    TypeMirror getAccessedType();

    /**
     * @return the simple name of the accessor
     */
    Name getSimpleName();

    /**
     * @return the set of modifiers that the accessor has
     */
    Set<Modifier> getModifiers();

    /**
     * @return the {@link ExecutableElement}, or {@code null} if the accessor does not have one
     */
    ExecutableElement getExecutable();

    /**
     * @return the underlying {@link Element}
     */
    Element getElement();
}
