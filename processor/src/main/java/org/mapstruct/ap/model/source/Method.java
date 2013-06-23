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

import java.util.Collections;
import java.util.Map;
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
    private final Type sourceType;
    private final Type targetType;
    private Map<String, Mapping> mappings;

    public static Method forMethodRequiringImplementation(ExecutableElement executable, String parameterName,
                                                          Type sourceType, Type targetType,
                                                          Map<String, Mapping> mappings) {

        return new Method(
            null,
            executable,
            parameterName,
            sourceType,
            targetType,
            mappings
        );
    }

    public static Method forReferencedMethod(Type declaringMapper, ExecutableElement executable, String parameterName,
                                             Type sourceType, Type targetType) {

        return new Method(
            declaringMapper,
            executable,
            parameterName,
            sourceType,
            targetType,
            Collections.<String, Mapping>emptyMap()
        );
    }

    private Method(Type declaringMapper, ExecutableElement executable, String parameterName, Type sourceType,
                   Type targetType, Map<String, Mapping> mappings) {
        this.declaringMapper = declaringMapper;
        this.executable = executable;
        this.parameterName = parameterName;
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.mappings = mappings;
    }

    /**
     * Returns the mapper type declaring this method if it is not declared by
     * the mapper interface currently processed but by another mapper imported
     * via {@code Mapper#users()}.
     *
     * @return The declaring mapper type
     */
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

    public Map<String, Mapping> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, Mapping> mappings) {
        this.mappings = mappings;
    }

    public boolean reverses(Method method) {
        return
            equals( sourceType, method.getTargetType() ) &&
                equals( targetType, method.getSourceType() );
    }

    public boolean isIterableMapping() {
        return sourceType.isIterableType() && targetType.isIterableType();
    }

    private boolean equals(Object o1, Object o2) {
        return ( o1 == null && o2 == null ) || ( o1 != null ) && o1.equals( o2 );
    }

    @Override
    public String toString() {
        return targetType + " " + getName() + "(" + sourceType + " " + parameterName + ")";
    }
}
