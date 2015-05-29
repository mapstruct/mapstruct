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

import java.util.LinkedList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

/**
 * Filter methods for working with {@link Element} collections.
 *
 * @author Gunnar Morling
 */
public class Filters {

    private Filters() {
    }

    public static List<ExecutableElement> getterMethodsIn(Iterable<ExecutableElement> elements) {
        List<ExecutableElement> getterMethods = new LinkedList<ExecutableElement>();

        for ( ExecutableElement method : elements ) {
            if ( Executables.isGetterMethod( method ) ) {
                getterMethods.add( method );
            }
        }

        return getterMethods;
    }

    public static List<ExecutableElement> setterMethodsIn(Iterable<ExecutableElement> elements) {
        List<ExecutableElement> setterMethods = new LinkedList<ExecutableElement>();

        for ( ExecutableElement method : elements ) {
            if ( Executables.isSetterMethod( method ) ) {
                setterMethods.add( method );
            }
        }
        return setterMethods;
    }

    public static List<ExecutableElement> adderMethodsIn(Iterable<ExecutableElement> elements) {
        List<ExecutableElement> adderMethods = new LinkedList<ExecutableElement>();

        for ( ExecutableElement method : elements ) {
            if ( Executables.isAdderMethod( method ) ) {
                adderMethods.add( method );
            }
        }

        return adderMethods;
    }
}
