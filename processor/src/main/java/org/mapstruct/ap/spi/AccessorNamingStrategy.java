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
package org.mapstruct.ap.spi;

import javax.lang.model.element.ExecutableElement;

/**
 * A service provider interface for the mapping between method names and properties.
 *
 * @author Christian Schuster
 * @author Gunnar Morling
 */
public interface AccessorNamingStrategy {

    /**
     * Returns the type of the given method.
     */
    MethodType getMethodType(ExecutableElement method);

    /**
     * Returns the name of the property represented by the given getter or setter method.
     * <p>
     * The default implementation will e.g. return "name" for {@code public String getName()} or {@code public void
     * setName(String name)}.
     */
    String getPropertyName(ExecutableElement getterOrSetterMethod);

    /**
     * Returns the element name of the given adder method.
     * <p>
     * The default implementation will e.g. return "item" for {@code public void addItem(String item)}.
     */
    String getElementName(ExecutableElement adderMethod);

    /**
     * Returns the getter name of the given collection property.
     * <p>
     * The default implementation will e.g. return "getItems" for "items".
     */
    String getCollectionGetterName(String property);
}
