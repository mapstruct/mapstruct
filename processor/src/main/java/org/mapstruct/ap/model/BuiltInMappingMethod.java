/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model;

import java.util.Collections;
import java.util.Set;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.ModelElement;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.util.Strings;

/**
 * Implementations create:
 * 1) an implementation of this build in method.
 * 2) a reference to a build in method, to use in property mappings
 * 3) a name for logging purposes.
 *
 * @author Sjaak Derksen
 */
public abstract class BuiltInMappingMethod extends ModelElement {

    /**
     * Creates a reference to the conversion method
     *
     * @return reference to method implementation
     */
    public abstract MethodReference createMethodReference();


    /**
     * Sets the conversion context which is used to add context information such as date / time
     * conversion  pattern, etc.
     *
     * @param conversionContext ConversionContext providing optional information required for creating
 the conversion.
     */
    public void setConversionContext(ConversionContext conversionContext) { }

    /**
     * hashCode
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    /**
     * equals based on class
     *
     * @param obj other class
     * @return true when classes are the same
     */
    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        return ( getClass() == obj.getClass() );
    }

    /**
     * tests whether generics do match. Default true.
     *
     * @param sourceType
     * @param targetType
     * @return
     */
    public boolean doGenericsMatch( Type sourceType, Type targetType ) {
        return true;
    }

    /**
     * method name
     * @return default method name is equal to class name of conversionmethod
     */
    public String getName() {
        return Strings.decapitalize( this.getClass().getSimpleName() );
    }

    /**
     * imported types default. Only used types should be added. Source and Target types are coming via
     * the MethodReference
     *
     * @return set of used types.
     */
    @Override
    public Set<Type> getImportTypes() {
        return Collections.<Type>emptySet();
    }

    public abstract Type source();

    public abstract Type target();
}
