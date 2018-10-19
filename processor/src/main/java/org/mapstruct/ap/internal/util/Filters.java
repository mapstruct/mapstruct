/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
        List<Accessor> getterMethods = new LinkedList<>();

        for ( Accessor method : elements ) {
            if ( accessorNaming.isGetterMethod( method ) ) {
                getterMethods.add( method );
            }
        }

        return getterMethods;
    }

    public static List<Accessor> fieldsIn(List<Accessor> accessors) {
        List<Accessor> fieldAccessors = new LinkedList<>();

        for ( Accessor accessor : accessors ) {
            if ( Executables.isFieldAccessor( accessor ) ) {
                fieldAccessors.add( accessor );
            }
        }

        return fieldAccessors;
    }

    public static List<ExecutableElementAccessor> presenceCheckMethodsIn(AccessorNamingUtils accessorNaming,
                                                                         List<Accessor> elements) {
        List<ExecutableElementAccessor> presenceCheckMethods = new LinkedList<>();

        for ( Accessor method : elements ) {
            if ( accessorNaming.isPresenceCheckMethod( method ) ) {
                presenceCheckMethods.add( (ExecutableElementAccessor) method );
            }
        }

        return presenceCheckMethods;
    }

    public static List<Accessor> setterMethodsIn(AccessorNamingUtils accessorNaming, List<Accessor> elements) {
        List<Accessor> setterMethods = new LinkedList<>();

        for ( Accessor method : elements ) {
            if ( accessorNaming.isSetterMethod( method ) ) {
                setterMethods.add( method );
            }
        }
        return setterMethods;
    }

    public static List<Accessor> adderMethodsIn(AccessorNamingUtils accessorNaming, List<Accessor> elements) {
        List<Accessor> adderMethods = new LinkedList<>();

        for ( Accessor method : elements ) {
            if ( accessorNaming.isAdderMethod( method ) ) {
                adderMethods.add( method );
            }
        }

        return adderMethods;
    }
}
