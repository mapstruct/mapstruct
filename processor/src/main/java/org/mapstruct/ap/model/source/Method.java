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

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
    private final List<Parameter> parameters;
    private final List<Parameter> sourceParameters;
    private final String resultName;
    private final Type resultType;
    private final Type returnType;
    private Map<String, Mapping> mappings;
    private IterableMapping iterableMapping;
    private MapMapping mapMapping;

    public static Method forMethodRequiringImplementation(ExecutableElement executable, List<Parameter> parameters,
                                                          List<Parameter> sourceParameters, Type resultType,
                                                          String resultName, Type targetType,
                                                          Map<String, Mapping> mappings,
                                                          IterableMapping iterableMapping, MapMapping mapMapping) {

        return new Method(
            null,
            executable,
            parameters,
            sourceParameters,
            resultType,
            resultName,
            targetType,
            mappings,
            iterableMapping,
            mapMapping
        );
    }

    public static Method forReferencedMethod(Type declaringMapper, ExecutableElement executable, String parameterName,
                                             Type sourceType, Type targetType) {

        return new Method(
            declaringMapper,
            executable,
            Arrays.asList( new Parameter( parameterName, sourceType ) ),
            Arrays.asList( new Parameter( parameterName, sourceType ) ),
            targetType,
            null,
            targetType,
            Collections.<String, Mapping>emptyMap(),
            null,
            null
        );
    }

    private Method(Type declaringMapper, ExecutableElement executable, List<Parameter> parameters,
                   List<Parameter> sourceParameters, Type resultType, String resultName,
                   Type returnType,
                   Map<String, Mapping> mappings, IterableMapping iterableMapping,
                   MapMapping mapMapping) {
        this.declaringMapper = declaringMapper;
        this.executable = executable;
        this.parameters = parameters;
        this.sourceParameters = sourceParameters;
        this.resultType = resultType;
        this.resultName = resultName;
        this.returnType = returnType;
        this.mappings = mappings;
        this.iterableMapping = iterableMapping;
        this.mapMapping = mapMapping;
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
        return resultName;
    }

    public List<Parameter> getSourceParameters() {
        return sourceParameters;
    }

    public Type getResultType() {
        return resultType;
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
            equals( getSingleSourceType(), method.getReturnType() )
                && equals( returnType, method.getSingleSourceType() );
    }

    public Type getSingleSourceType() {
        return sourceParameters.size() == 1 ? sourceParameters.get( 0 ).getType() : null;
    }

    public boolean isIterableMapping() {
        return getSingleSourceType().isIterableType() && resultType.isIterableType();
    }

    public boolean isMapMapping() {
        return getSingleSourceType().isMapType() && resultType.isMapType();
    }

    private boolean equals(Object o1, Object o2) {
        return ( o1 == null && o2 == null ) || ( o1 != null ) && o1.equals( o2 );
    }

    @Override
    public String toString() {
        return returnType + " " + getName() + "(" + getParamsList() + ")";
    }

    private String getParamsList() {
        StringBuilder sb = new StringBuilder();
        for ( Iterator<Parameter> it = parameters.iterator(); it.hasNext(); ) {
            Parameter param = it.next();
            sb.append( param.getType() ).append( " " ).append( param.getName() );
            if ( it.hasNext() ) {
                sb.append( ", " );
            }
        }

        return sb.toString();
    }
}
