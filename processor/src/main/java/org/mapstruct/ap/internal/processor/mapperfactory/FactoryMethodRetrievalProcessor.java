/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.processor.mapperfactory;

import static org.mapstruct.ap.internal.util.Executables.getAllEnclosedExecutableElements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.processor.MapperElementProcessor;
import org.mapstruct.ap.internal.util.FormattingMessager;

/**
 * A {@link MapperElementProcessor} which retrieves a list of {@link SourceMethod}s representing all the mapping methods
 * of the given bean mapper type as well as all referenced mapper methods declared by other mappers referenced by the
 * current mapper.
 *
 * @author Sjaak Derksen
 */
public class FactoryMethodRetrievalProcessor implements FactoryElementProcessor<Void, List<SourceMethod>> {

    private FormattingMessager messager;
    private TypeFactory typeFactory;
    private Types typeUtils;
    private Elements elementUtils;

    @Override
    public List<SourceMethod> process(ProcessorContext context, FactoryGenerationInfo info, Void sourceModel) {
        this.messager = context.getMessager();
        this.typeFactory = context.getTypeFactory();
        this.typeUtils = context.getTypeUtils();
        this.elementUtils = context.getElementUtils();

        return retrieveMethods( info );
    }

    @Override
    public int getPriority() {
        return 1;
    }

    private List<SourceMethod> retrieveMethods(FactoryGenerationInfo factoryGenerationInfo) {

        // obtain candidate factory methods
        List<SourceMethod> candidateMapperFactoryMethods = new ArrayList<SourceMethod>();
        TypeElement mapperFactory = factoryGenerationInfo.getFactoryElement();
        List<ExecutableElement> executableElemements = getAllEnclosedExecutableElements( elementUtils, mapperFactory );
        for ( ExecutableElement executableElement : executableElemements ) {
            SourceMethod method = asSourceMethod( mapperFactory, executableElement );
            if ( method != null ) {
                candidateMapperFactoryMethods.add( method );
            }
        }

        // get available constructors
        Map<Type, List<SourceMethod>> availableMappersWithTheirConstructors = new HashMap<Type, List<SourceMethod>>();
        for ( TypeElement mapper : factoryGenerationInfo.getMapperElements() ) {

            // Mapper type
            Type mapperType = typeFactory.getType( mapper );

            // Add all constructors
            List<SourceMethod> mapperConstructors = new ArrayList<SourceMethod>();
            List<ExecutableElement> constructors = ElementFilter.constructorsIn( mapper.getEnclosedElements() );
            for ( ExecutableElement constructor : constructors ) {
                if ( constructor.getModifiers().contains( Modifier.PUBLIC )
                    || constructor.getModifiers().contains( Modifier.PROTECTED ) ) {
                    mapperConstructors.add( asConstructor( constructor ) );
                }
            }

            // add to the map
            availableMappersWithTheirConstructors.put( mapperType, mapperConstructors );
        }

        // cross check
        List<SourceMethod> mapperFactoryMethods = new ArrayList<SourceMethod>();
        List<SourceMethod> clonedCandidates = new ArrayList<SourceMethod>( candidateMapperFactoryMethods );

        for ( SourceMethod candidateMapperFactoryMethod : clonedCandidates ) {
            for ( Map.Entry<Type, List<SourceMethod>> entry : availableMappersWithTheirConstructors.entrySet() ) {

                if ( candidateMapperFactoryMethod.getReturnType().equals( entry.getKey() ) ) {
                    // now look for a matching constructor
                    for ( SourceMethod constructorMethod : entry.getValue() ) {
                        if ( constructorMatchesFactoryMethod( candidateMapperFactoryMethod, constructorMethod ) ) {
                            mapperFactoryMethods.add( candidateMapperFactoryMethod );
                            candidateMapperFactoryMethods.remove( candidateMapperFactoryMethod );
                        }
                    }
                }
            }
        }

        // TODO: error handling when candidates is not empty (no matching constructor found)

        return mapperFactoryMethods;
    }

    private SourceMethod asSourceMethod(TypeElement mapperFactory, ExecutableElement mapperFactoryMethod) {

        ExecutableType methodType
            = typeFactory.getMethodType( (DeclaredType) mapperFactory.asType(), mapperFactoryMethod );
        List<Parameter> parameters = typeFactory.getParameters( methodType, mapperFactoryMethod );
        Type returnType = typeFactory.getReturnType( methodType );
        List<Type> thrownTypes = typeFactory.getThrownTypes( methodType );

        return new SourceMethod.Builder()
            .setExecutable( mapperFactoryMethod )
            .setParameters( parameters )
            .setReturnType( returnType )
            .setExceptionTypes( thrownTypes )
            .setTypeUtils( typeUtils )
            .setMessager( messager )
            .setTypeFactory( typeFactory )
            .build();

    }

    private SourceMethod asConstructor(ExecutableElement method) {

        ExecutableType executableType = (ExecutableType) method.asType();
        List<Type> exceptionTypes = typeFactory.getThrownTypes( executableType );
        List<Parameter> parameters = typeFactory.getParameters( executableType, method );

        return new SourceMethod.Builder()
            .setExecutable( method )
            .setParameters( parameters )
            .setExceptionTypes( exceptionTypes )
            .setTypeUtils( typeUtils )
            .setMessager( messager )
            .setTypeFactory( typeFactory )
            .setConstructor()
            .build();
    }

    /**
     * A constructor matches if and only if:
     * <ol>
     * <li>the parameter types are the same and in exactly the same order</li>
     * <li>the thrown exceptions match in an arbitrary order</li>
     * </ol>
     *
     * @param factoryMethod
     * @param constructor
     * @return true when match.
     */
    private boolean constructorMatchesFactoryMethod(SourceMethod factoryMethod, SourceMethod constructor) {
        boolean result = false;

        if ( factoryMethod.getParameterTypes().equals( constructor.getParameterTypes() ) ) {
            Set<Type> factoryMethodThrownTypes = new HashSet<Type>( factoryMethod.getThrownTypes() );
            Set<Type> constructorThrownTypes = new HashSet<Type>( constructor.getThrownTypes() );
            result = factoryMethodThrownTypes.equals( constructorThrownTypes );
        }
        return result;
    }

}
