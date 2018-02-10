/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;

/**
 * Represents a reference to another method, e.g. used to map a bean property from source to target type or to
 * instantiate the return value of a mapping method (rather than calling the {@code new} operator).
 *
 * @author Gunnar Morling
 */
public class MethodReference extends ModelElement implements Assignment {
    private final String name;
    private final List<Parameter> sourceParameters;
    private final Type returnType;
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
     * {@code setFoo(barToFoo( jaxbElemToValue( bar) ) )}. If there's no nested typeConversion or other mapping method,
     * this will be a direct assignment.
     */
    private Assignment assignment;

    private final Type definingType;
    private final List<ParameterBinding> parameterBindings;
    private final Parameter providingParameter;
    private final boolean isStatic;

    /**
     * Creates a new reference to the given method.
     *
     * @param method the target method of the reference
     * @param declaringMapper the method declaring the mapper; {@code null} if the current mapper itself
     * @param providingParameter The parameter providing the mapper, or {@code null} if the method is defined by the
     *            mapper itself or by {@code declaringMapper}.
     * @param parameterBindings the parameter bindings of this method reference
     */
    protected MethodReference(Method method, MapperReference declaringMapper, Parameter providingParameter,
                              List<ParameterBinding> parameterBindings) {
        this.declaringMapper = declaringMapper;
        this.sourceParameters = Parameter.getSourceParameters( method.getParameters() );
        this.returnType = method.getReturnType();
        this.providingParameter = providingParameter;
        this.parameterBindings = parameterBindings;
        this.contextParam = null;
        Set<Type> imported = new HashSet<Type>();

        for ( Type type : method.getThrownTypes() ) {
            imported.addAll( type.getImportTypes() );
        }

        for ( ParameterBinding binding : parameterBindings ) {
            imported.addAll( binding.getImportTypes() );
        }

        this.importTypes = Collections.<Type>unmodifiableSet( imported );
        this.thrownTypes = method.getThrownTypes();
        this.isUpdateMethod = method.getMappingTargetParameter() != null;
        this.definingType = method.getDefiningType();
        this.isStatic = method.isStatic();
        this.name = method.getName();
   }

    private MethodReference(BuiltInMethod method, ConversionContext contextParam) {
        this.sourceParameters = Parameter.getSourceParameters( method.getParameters() );
        this.returnType = method.getReturnType();
        this.declaringMapper = null;
        this.providingParameter = null;
        this.contextParam = method.getContextParameter( contextParam );
        this.importTypes = Collections.emptySet();
        this.thrownTypes = Collections.emptyList();
        this.definingType = null;
        this.isUpdateMethod = method.getMappingTargetParameter() != null;
        this.parameterBindings = ParameterBinding.fromParameters( method.getParameters() );
        this.isStatic = method.isStatic();
        this.name = method.getName();
    }

    private MethodReference(String name, Type definingType, boolean isStatic) {
        this.name = name;
        this.definingType = definingType;
        this.sourceParameters = Collections.emptyList();
        this.returnType = null;
        this.declaringMapper = null;
        this.importTypes = Collections.emptySet();
        this.thrownTypes = Collections.emptyList();
        this.isUpdateMethod = false;
        this.contextParam = null;
        this.parameterBindings = Collections.emptyList();
        this.providingParameter = null;
        this.isStatic = isStatic;
    }

    public MapperReference getDeclaringMapper() {
        return declaringMapper;
    }

    public Parameter getProvidingParameter() {
        return providingParameter;
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

    public String getName() {
        return name;
    }

    public List<Parameter> getSourceParameters() {
        return sourceParameters;
    }

    @Override
    public void setAssignment( Assignment assignment ) {
        this.assignment = assignment;
    }

    @Override
    public String getSourceReference() {
        return assignment.getSourceReference();
    }

    @Override
    public String getSourcePresenceCheckerReference() {
        return assignment.getSourcePresenceCheckerReference();
    }

    @Override
    public Type getSourceType() {
        return assignment.getSourceType();
    }

    @Override
    public String createLocalVarName( String desiredName ) {
        return assignment.createLocalVarName( desiredName );
    }

    @Override
    public String getSourceLocalVarName() {
        return assignment.getSourceLocalVarName();
    }

    @Override
    public void setSourceLocalVarName(String sourceLocalVarName) {
        assignment.setSourceLocalVarName( sourceLocalVarName );
    }

    @Override
    public String getSourceParameterName() {
        return assignment.getSourceParameterName();
    }

    @Override
    public boolean isSourceReferenceParameter() {
        return assignment.isSourceReferenceParameter();
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

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public boolean isCallingUpdateMethod() {
        return isUpdateMethod;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public List<ParameterBinding> getParameterBindings() {
        return parameterBindings;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( declaringMapper == null ) ? 0 : declaringMapper.hashCode() );
        result = prime * result + ( ( providingParameter == null ) ? 0 : providingParameter.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( !super.equals( obj ) ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        MethodReference other = (MethodReference) obj;
        if ( declaringMapper == null ) {
            if ( other.declaringMapper != null ) {
                return false;
            }
        }
        else if ( !declaringMapper.equals( other.declaringMapper ) ) {
            return false;
        }
        if ( providingParameter == null ) {
            if ( other.providingParameter != null ) {
                return false;
            }
        }
        else if ( !providingParameter.equals( other.providingParameter ) ) {
            return false;
        }
        return true;
    }

    public static MethodReference forBuiltInMethod(BuiltInMethod method, ConversionContext contextParam) {
        return new MethodReference( method, contextParam );
    }

    public static MethodReference forForgedMethod(Method method, List<ParameterBinding> parameterBindings) {
        return new MethodReference( method, null, null, parameterBindings );
    }

    public static MethodReference forParameterProvidedMethod(Method method, Parameter providingParameter,
            List<ParameterBinding> parameterBindings) {
        return new MethodReference( method, null, providingParameter, parameterBindings );
    }

    public static MethodReference forMapperReference(Method method, MapperReference declaringMapper,
            List<ParameterBinding> parameterBindings) {
        return new MethodReference( method, declaringMapper, null, parameterBindings );
    }

    public static MethodReference forStaticBuilder(String builderCreationMethod, Type definingType) {
        return new MethodReference( builderCreationMethod, definingType, true );
    }

    public static MethodReference forMethodCall(String methodName) {
        return new MethodReference( methodName, null, false );
    }
}
