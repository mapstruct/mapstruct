/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.util;

import java.util.LinkedList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;

import static javax.lang.model.util.ElementFilter.methodsIn;

/**
 * Filter methods for working with {@link Element} collections.
 *
 * @author Gunnar Morling
 */
public class Filters {

    public static List<ExecutableElement> getterMethodsIn(Iterable<? extends Element> elements) {
        List<ExecutableElement> getterMethods = new LinkedList<ExecutableElement>();

        for ( ExecutableElement method : methodsIn( elements ) ) {
            //TODO: consider is/has
            String name = method.getSimpleName().toString();

            if ( name.startsWith( "get" ) && name.length() > 3 && method.getParameters()
                .isEmpty() && method.getReturnType().getKind() != TypeKind.VOID ) {
                getterMethods.add( method );
            }
        }

        return getterMethods;
    }

    public static List<ExecutableElement> setterMethodsIn(Iterable<? extends Element> elements) {
        List<ExecutableElement> setterMethods = new LinkedList<ExecutableElement>();

        for ( ExecutableElement method : methodsIn( elements ) ) {
            //TODO: consider is/has
            String name = method.getSimpleName().toString();

            if ( name.startsWith( "set" ) && name.length() > 3 && method.getParameters()
                .size() == 1 && method.getReturnType().getKind() == TypeKind.VOID ) {
                setterMethods.add( method );
            }
        }

        return setterMethods;
    }
}
