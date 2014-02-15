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
package org.mapstruct.ap.conversion.methods;

import org.mapstruct.ap.conversion.ConversionProvider;
import org.mapstruct.ap.model.MethodReference;
import org.mapstruct.ap.model.common.ModelElement;
import org.mapstruct.ap.model.common.Type;

/**
 * Implementations create:
 * 1) an implementation of this build in method.
 * 2) a reference to a build in method, to use in property mappings
 * 3) a name for logging purposes.
 *
 * @author Sjaak Derksen
 */
public abstract class BuildInMethod extends ModelElement {


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
     * @param conversionContext Context providing optional information required for creating
     * the conversion.
     */
    public void setConversionContext(ConversionProvider.Context conversionContext) { }

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

    boolean doGenericsMatch( Type sourceType, Type targetType ) {
        return true;
    }


}
