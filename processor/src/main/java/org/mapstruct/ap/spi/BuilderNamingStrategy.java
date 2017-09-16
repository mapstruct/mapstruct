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
package org.mapstruct.ap.spi;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * A service provider interface for the mapping between builder method names and properties.  It's common
 * to use a different accessor naming convention for builders than for POJOs.  Example:
 *
 * <code>
 *     ContactBuilder contactBuilder = Contact.builder();
 *     contactBuilder.firstName("Frank");
 *     contactBuilder.lastName("Barnes");
 *
 *     Contact contact = contactBuilder.build();
 *     assert "Frank".equals(contact.getFirstName());
 * </code>
 *
 * @author Eric Martineau
 * @author Gunnar Morling
 */
public interface BuilderNamingStrategy {

    /**
     * Returns the {@link MethodType} for the provided method.
     *
     * @param forType The concrete type this method is being inspected for
     * @param method to be analyzed.
     *
     * @return the method type.
     */
    MethodType getMethodType(TypeElement forType, ExecutableElement method, Elements elements, Types types);

    /**
     * Returns the name of the property represented by the given getter or setter method.
     * <p>
     * The default implementation will e.g. return "name" for {@code public String getName()} or {@code public void
     * setName(String name)}.
     *
     * @param forType The concrete type this method is being inspected for
     * @param method to be analyzed.
     *
     * @return property name derived from the method
     */
    String getPropertyName(TypeElement forType, ExecutableElement method, Elements elements, Types types);

    /**
     * Returns the element name of the given adder method.
     * <p>
     * The default implementation will e.g. return "item" for {@code public void addItem(String item)}.
     *
     * @param forType The concrete type this method is being inspected for
     * @param adderMethod to be getterOrSetterMethod.
     *
     * @return getter name for collections
     */
    String getBuilderElementName(TypeElement forType, ExecutableElement adderMethod, Elements elements, Types types);

    /**
     * Uses the provided {@link BuilderProvider} for any necessary naming operations.
     *
     * @return Either {@code this} or a new instance.
     */
    BuilderNamingStrategy withBuilderProvider(BuilderProvider provider);
}
