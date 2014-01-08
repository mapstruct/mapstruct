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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An inline conversion between source and target type of a mapping.
 *
 * @author Gunnar Morling
 */
public class TypeConversion extends AbstractModelElement {

    private final Set<Type> importTypes;
    private final List<Type> exceptionTypes;
    private final String conversionString;

    public TypeConversion(String conversionString) {
        this( Collections.<Type>emptySet(), Collections.<Type>emptyList(), conversionString );
    }

    public TypeConversion(Set<Type> importTypes, List<Type> exceptionTypes, String conversionString) {
        this.importTypes = new HashSet<Type>( importTypes );
        this.importTypes.addAll( exceptionTypes );
        this.exceptionTypes = exceptionTypes;
        this.conversionString = conversionString;
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    public List<Type> getExceptionTypes() {
        return exceptionTypes;
    }

    public String getConversionString() {
        return conversionString;
    }
}
