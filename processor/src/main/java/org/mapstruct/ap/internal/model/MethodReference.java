/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;

/**
 * Represents a reference to another method, e.g. used to map a bean property from source to target type or to
 * instantiate the return value of a mapping method (rather than calling the {@code new} operator).
 *
 * @author Gunnar Morling
 */
public class MethodReference extends MappingMethod implements Assignment {

    private final MapperReference declaringMapper;
    private final Set<Type> importTypes;
    private final List<Type> thrownTypes;
    private final boolean isUpdateMethod;

    /**
     * In case this reference targets a built-in method, allows to pass specific context information to the invoked
     * method. Currently this is only used to pass in the configured date format string when invoking a built-in method
     * which requires that.
     */
    private final String contextParam;


    /**
     * A reference to another mapping method or typeConversion in case this is a two-step mapping, e.g. from
     * {@code JAXBElement<Bar>} to {@code Foo} to for which a nested method call will be generated:
     * {@code setFoo(barToFoo( jaxbElemToValue( bar) ) )}
     *
     * If there's no nested typeConversion or other mapping method, this will be a {@link Direct} assignment.
     */
    private Assignment assignment;


    private final Type definingType;

    /**
     * Creates a new reference to the given method.
     *
     * @param method the target method of the reference
     * @param declaringMapper the method declaring the mapper; {@code null} if the current mapper itself
     * @param targetType in case the referenced method has a parameter for passing the target type, the given
     * target type, otherwise {@code null}
     */
    public MethodReference(Method method, MapperReference declaringMapper, Type targetType) {
        super( method );
        this.declaringMapper = declaringMapper;
        this.contextParam = null;
        Set<Type> imported = new HashSet<Type>( method.getThrownTypes() );
        if ( targetType != null ) {
            imported.add( targetType );
        }
        this.importTypes = Collections.<Type>unmodifiableSet( imported );
        this.thrownTypes = method.getThrownTypes();
        this.isUpdateMethod = method.getMappingTargetParameter() != null;
        this.definingType = method.getDefiningType();
   }

    public MethodReference(BuiltInMethod method, ConversionContext contextParam) {
        super( method );
        this.declaringMapper = null;
        this.contextParam = method.getContextParameter( contextParam );
        this.importTypes = Collections.emptySet();
        this.thrownTypes = Collections.emptyList();
        this.definingType = null;
        this.isUpdateMethod = method.getMappingTargetParameter() != null;
    }

    public MapperReference getDeclaringMapper() {
        return declaringMapper;
    }

    public String getMapperVariableName() {
        return declaringMapper.getVariableName();
    }

    public String getContextParam() {
        return contextParam;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    @Override
    public void setAssignment( Assignment assignment ) {
        this.assignment = assignment;
    }

    @Override
    public String getSourceReference() {
        return assignment.getSourceReference();
    }

    /**
     * @return the type of the single source parameter that is not the {@code @TargetType} parameter
     */
    public Type getSingleSourceParameterType() {
        for ( Parameter parameter : getSourceParameters() ) {
            if ( !parameter.isTargetType() ) {
                return parameter.getType();
            }
        }
        return null;
    }

    public Type getDefiningType() {
        return definingType;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<Type>( importTypes );
        if ( assignment != null ) {
            imported.addAll( assignment.getImportTypes() );
        }
        if ( isStatic() ) {
            imported.add( definingType );
        }
        return imported;
    }

    @Override
    public List<Type> getThrownTypes() {
        List<Type> exceptions = new ArrayList<Type>();
        exceptions.addAll( thrownTypes );
        if ( assignment != null ) {
            exceptions.addAll( assignment.getThrownTypes() );
        }
        return exceptions;
    }

    @Override
    public AssignmentType getType() {

        switch ( assignment.getType() ) {
            case DIRECT:
                return AssignmentType.MAPPED;
            case MAPPED:
                return AssignmentType.MAPPED_TWICE;
            case TYPE_CONVERTED:
                return AssignmentType.TYPE_CONVERTED_MAPPED;
            default:
                return null;
        }
    }

    @Override
    public boolean isUpdateMethod() {
        return isUpdateMethod;
    }
}
