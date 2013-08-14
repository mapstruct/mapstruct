/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.util;

import java.util.LinkedList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

import static javax.lang.model.util.ElementFilter.methodsIn;

/**
 * Filter methods for working with {@link Element} collections.
 *
 * @author Gunnar Morling
 */
public class Filters {

    private final Executables executables;

    public Filters(Executables executables) {
        this.executables = executables;
    }

    public List<ExecutableElement> getterMethodsIn(Iterable<? extends Element> elements) {
        List<ExecutableElement> getterMethods = new LinkedList<ExecutableElement>();

        for ( ExecutableElement method : methodsIn( elements ) ) {
            if ( executables.isGetterMethod( method ) ) {
                getterMethods.add( method );
            }
        }

        return getterMethods;
    }

    public List<ExecutableElement> setterMethodsIn(Iterable<? extends Element> elements) {
        List<ExecutableElement> setterMethods = new LinkedList<ExecutableElement>();

        for ( ExecutableElement method : methodsIn( elements ) ) {
            if ( executables.isSetterMethod( method ) ) {
                setterMethods.add( method );
            }
        }

        return setterMethods;
    }
}
