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
package org.mapstruct.ap.model;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one
 * iterable type to another. The collection elements are mapped either by a
 * {@link TypeConversion} or another mapping method.
 *
 * @author Gunnar Morling
 */
public class IterableMappingMethod extends MappingMethod {

    private final MappingMethodReference elementMappingMethod;
    private final String toConversion;

    public IterableMappingMethod(String name, String parameterName, Type sourceType, Type targetType,
                                 MappingMethodReference elementMappingMethod,
                                 String toConversion) {
        super( name, parameterName, sourceType, targetType );
        this.elementMappingMethod = elementMappingMethod;
        this.toConversion = toConversion;
    }

    public MappingMethodReference getElementMappingMethod() {
        return elementMappingMethod;
    }

    public String getToConversion() {
        return toConversion;
    }

    @Override
    public String toString() {
        return "MappingMethod {" +
            "\n    elementMappingMethod=" + elementMappingMethod +
            "\n}";
    }
}
