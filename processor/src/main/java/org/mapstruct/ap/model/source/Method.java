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
package org.mapstruct.ap.model.source;

import java.util.List;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.model.Type;

/**
 * Represents a mapping method with source and target type and the mappings
 * between the properties of source and target type.
 *
 * @author Gunnar Morling
 */
public class Method {

    private final Type declaringMapper;
    private final ExecutableElement executable;
    private final String parameterName;
    private final String targetParameterName;
    private final Type sourceType;
    private final Type targetType;
    private final List<MappedProperty> mappedProperties;
    private final boolean mapExistingObject;

    public Method(ExecutableElement executable, String parameterName, Type sourceType, String targetParameterName,
                  Type targetType,
                  List<MappedProperty> mappedProperties, boolean mapExistingObject) {
        this(
            null, executable, parameterName, sourceType, targetParameterName, targetType, mappedProperties,
            mapExistingObject
        );
    }

    public Method(Type declaringMapper, ExecutableElement executable, String parameterName, Type sourceType,
                  String targetParameterName, Type targetType,
                  List<MappedProperty> mappedProperties, boolean mapExistingObject) {
        this.declaringMapper = declaringMapper;
        this.executable = executable;
        this.parameterName = parameterName;
        this.targetParameterName = targetParameterName;
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.mappedProperties = mappedProperties;
        this.mapExistingObject = mapExistingObject;
    }

    public Type getDeclaringMapper() {
        return declaringMapper;
    }

    public ExecutableElement getExecutable() {
        return executable;
    }

    public String getName() {
        return executable.getSimpleName().toString();
    }

    public String getParameterName() {
        return parameterName;
    }

    public Type getSourceType() {
        return sourceType;
    }

    public Type getTargetType() {
        return targetType;
    }

    public List<MappedProperty> getMappedProperties() {
        return mappedProperties;
    }

    public String getTargetParameterName() {
        return targetParameterName;
    }

    public boolean isMapExistingObject() {
        return mapExistingObject;
    }

    public boolean reverses(Method method) {
        return equals( sourceType, method.getTargetType() ) &&
            equals( targetType, method.getSourceType() ) &&
            equals( isMapExistingObject(), method.isMapExistingObject() );
    }

    public boolean relatedNewObjectMapping(Method method) {
        return equals( method ) && !method.isMapExistingObject();
    }

    public boolean relatedExistingObjetcMapping(Method method) {
        return equals( method ) && method.isMapExistingObject();
    }

    public boolean equals(Method method) {
        return equals( sourceType, method.getSourceType() ) &&
            equals( targetType, method.getTargetType() );
    }

    @Override
    public int hashCode() {
        int result = sourceType != null ? sourceType.hashCode() : 0;
        result = 31 * result + ( targetType != null ? targetType.hashCode() : 0 );
        return result;
    }

    private boolean equals(Object o1, Object o2) {
        return ( o1 == null && o2 == null ) || ( o1 != null ) && o1.equals( o2 );
    }

    @Override
    public String toString() {
        return targetType + " " + getName() + "(" + sourceType + " " + parameterName + ")";
    }
}
