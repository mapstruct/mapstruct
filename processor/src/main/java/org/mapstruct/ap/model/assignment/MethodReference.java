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
package org.mapstruct.ap.model.assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.model.Assignment;
import org.mapstruct.ap.model.FactoryMethod;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.MappingMethod;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.model.source.builtin.BuiltInMethod;

/**
 * Represents a reference to {@link MappingMethod}.
 *
 * @author Gunnar Morling
 */
public class MethodReference extends MappingMethod implements Assignment, FactoryMethod {

    private final MapperReference declaringMapper;
    private final Set<Type> importTypes;
    private final List<Type> exceptionTypes;

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
     * If there's no nested typeConversion or other mapping method, this will be a {@link Simple} assignment.
     */
    private Assignment assignment;

    /**
     * Creates a new reference to the given method.
     *
     * @param method the target method of the reference
     * @param declaringMapper the method declaring the mapper; {@code null} if the current mapper itself
     * @param targetType in case the referenced method has a parameter for passing the target type, the given
     * target type, otherwise {@code null}
     */
    public MethodReference(SourceMethod method, MapperReference declaringMapper, Type targetType) {
        super( method );
        this.declaringMapper = declaringMapper;
        this.contextParam = null;
        Set<Type> imported = new HashSet<Type>( method.getThrownTypes() );
        if ( targetType != null ) {
            imported.add( targetType );
        }
        this.importTypes = Collections.<Type>unmodifiableSet( imported );
        this.exceptionTypes = method.getThrownTypes();
   }

    public MethodReference(BuiltInMethod method, ConversionContext contextParam) {
        super( method );
        this.declaringMapper = null;
        this.contextParam = method.getContextParameter( contextParam );
        this.importTypes = Collections.emptySet();
        this.exceptionTypes = Collections.emptyList();
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

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<Type>( importTypes );
        if ( assignment != null ) {
            imported.addAll( assignment.getImportTypes() );
        }
        return imported;
    }

    @Override
    public List<Type> getExceptionTypes() {
        List<Type> exceptions = new ArrayList<Type>();
        exceptions.addAll( exceptionTypes );
        if ( assignment != null ) {
            exceptions.addAll( assignment.getExceptionTypes() );
        }
        return exceptions;
    }

    @Override
    public boolean isSimple() {
        return false;
    }
}
