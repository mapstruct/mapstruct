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

import java.beans.Introspector;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.util.Strings;

/**
 * Represents a mapping method with source and target type and the mappings between the properties of source and target
 * type.
 *
 * @author Gunnar Morling
 */
public class Method {

    private final Type declaringMapper;
    private final ExecutableElement executable;
    private final List<Parameter> parameters;
    private final Type returnType;

    private Map<String, Mapping> mappings;
    private IterableMapping iterableMapping;
    private MapMapping mapMapping;

    private final Parameter singleSourceParameter;
    private final Parameter targetParameter;

    public static Method forMethodRequiringImplementation(ExecutableElement executable, List<Parameter> parameters,
                                                          Type returnType, Map<String, Mapping> mappings,
                                                          IterableMapping iterableMapping, MapMapping mapMapping) {

        return new Method(
            null,
            executable,
            parameters,
            returnType,
            mappings,
            iterableMapping,
            mapMapping
        );
    }

    public static Method forReferencedMethod(Type declaringMapper, ExecutableElement executable,
                                             List<Parameter> parameters, Type returnType) {

        return new Method(
            declaringMapper,
            executable,
            parameters,
            returnType,
            Collections.<String, Mapping>emptyMap(),
            null,
            null
        );
    }

    private Method(Type declaringMapper, ExecutableElement executable, List<Parameter> parameters, Type returnType,
                   Map<String, Mapping> mappings, IterableMapping iterableMapping, MapMapping mapMapping) {
        this.declaringMapper = declaringMapper;
        this.executable = executable;
        this.parameters = parameters;
        this.returnType = returnType;

        this.mappings = mappings;
        this.iterableMapping = iterableMapping;
        this.mapMapping = mapMapping;

        this.singleSourceParameter = determineSingleSourceParameter();
        this.targetParameter = determineTargetParameter( parameters );
    }

    private Parameter determineTargetParameter(Iterable<Parameter> parameters) {
        for ( Parameter parameter : parameters ) {
            if ( parameter.isMappingTarget() ) {
                return parameter;
            }
        }

        return null;
    }

    private Parameter determineSingleSourceParameter() {
        for ( Parameter parameter : parameters ) {
            if ( !parameter.isMappingTarget() ) {
                return parameter;
            }
        }

        throw new IllegalStateException( "Method " + this + " has no source parameter." );
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

    public List<Parameter> getParameters() {
        return parameters;
    }

    public String getResultName() {
        return targetParameter != null ? targetParameter.getName() : Introspector.decapitalize( returnType.getName() );
    }

    public Type getResultType() {
        return targetParameter != null ? targetParameter.getType() : returnType;
    }

    public Type getReturnType() {
        return returnType;
    }

    public Map<String, Mapping> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, Mapping> mappings) {
        this.mappings = mappings;
    }

    public IterableMapping getIterableMapping() {
        return iterableMapping;
    }

    public void setIterableMapping(IterableMapping iterableMapping) {
        this.iterableMapping = iterableMapping;
    }

    public MapMapping getMapMapping() {
        return mapMapping;
    }

    public void setMapMapping(MapMapping mapMapping) {
        this.mapMapping = mapMapping;
    }

    public boolean reverses(Method method) {
        return
            equals( getSingleSourceParameter().getType(), method.getResultType() )
                && equals( getResultType(), method.getSingleSourceParameter().getType() );
    }

    public Parameter getSingleSourceParameter() {
        return singleSourceParameter;
    }

    public Parameter getTargetParameter() {
        return targetParameter;
    }

    public boolean isIterableMapping() {
        return getSingleSourceParameter().getType().isIterableType() && getResultType().isIterableType();
    }

    public boolean isMapMapping() {
        return getSingleSourceParameter().getType().isMapType() && getResultType().isMapType();
    }

    private boolean equals(Object o1, Object o2) {
        return ( o1 == null && o2 == null ) || ( o1 != null ) && o1.equals( o2 );
    }

    @Override
    public String toString() {
        return returnType + " " + getName() + "(" + Strings.join( parameters, ", " ) + ")";
    }
}
