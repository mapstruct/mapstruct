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
package org.mapstruct.ap.internal.util;

import java.util.LinkedList;
import java.util.List;

import javax.lang.model.element.Element;

import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.ExecutableElementAccessor;

/**
 * Filter methods for working with {@link Element} collections.
 *
 * @author Gunnar Morling
 */
public class Filters {

    private Filters() {
    }

    public static List<Accessor> getterMethodsIn(AccessorNamingUtils accessorNaming, List<Accessor> elements) {
        List<Accessor> getterMethods = new LinkedList<Accessor>();

        for ( Accessor method : elements ) {
            if ( accessorNaming.isGetterMethod( method ) ) {
                getterMethods.add( method );
            }
        }

        return getterMethods;
    }

    public static List<Accessor> fieldsIn(List<Accessor> accessors) {
        List<Accessor> fieldAccessors = new LinkedList<Accessor>();

        for ( Accessor accessor : accessors ) {
            if ( Executables.isFieldAccessor( accessor ) ) {
                fieldAccessors.add( accessor );
            }
        }

        return fieldAccessors;
    }

    public static List<ExecutableElementAccessor> presenceCheckMethodsIn(AccessorNamingUtils accessorNaming,
                                                                         List<Accessor> elements) {
        List<ExecutableElementAccessor> presenceCheckMethods = new LinkedList<ExecutableElementAccessor>();

        for ( Accessor method : elements ) {
            if ( accessorNaming.isPresenceCheckMethod( method ) ) {
                presenceCheckMethods.add( (ExecutableElementAccessor) method );
            }
        }

        return presenceCheckMethods;
    }

    public static List<Accessor> setterMethodsIn(AccessorNamingUtils accessorNaming, List<Accessor> elements) {
        List<Accessor> setterMethods = new LinkedList<Accessor>();

        for ( Accessor method : elements ) {
            if ( accessorNaming.isSetterMethod( method ) ) {
                setterMethods.add( method );
            }
        }
        return setterMethods;
    }

    public static List<Accessor> adderMethodsIn(AccessorNamingUtils accessorNaming, List<Accessor> elements) {
        List<Accessor> adderMethods = new LinkedList<Accessor>();

        for ( Accessor method : elements ) {
            if ( accessorNaming.isAdderMethod( method ) ) {
                adderMethods.add( method );
            }
        }

        return adderMethods;
    }
}
