/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
