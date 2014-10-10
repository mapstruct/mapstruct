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
package org.mapstruct.ap.processor.creation;

import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import org.mapstruct.ap.model.FactoryMethod;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.MappingMethod;
import org.mapstruct.ap.model.assignment.AssignmentFactory;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.model.source.selector.MethodSelectors;
import org.mapstruct.ap.option.Options;

/**
 *
 * @author Sjaak Derksen
 */
public abstract class MappingBuilder {

    private final MappingContext ctx;

    protected MappingBuilder( MappingContext ctx ) {
        this.ctx = ctx;
    }

    protected TypeFactory getTypeFactory() {
        return ctx.getTypeFactory();
    }

    protected Elements getElementUtils() {
        return ctx.getElementUtils();
    }

    protected Types getTypeUtils() {
        return ctx.getTypeUtils();
    }

    protected Messager getMessager() {
        return ctx.getMessager();
    }

    protected List<MapperReference> getMapperReferences() {
        return ctx.getMapperReferences();
    }

    protected List<SourceMethod> getSourceModel() {
        return ctx.getSourceModel();
    }

    protected Options getOptions() {
        return ctx.getOptions();
    }

    protected TypeElement getMapperTypeElement() {
        return ctx.getMapperTypeElement();
    }

    protected MappingResolver getMappingResolver() {
        return ctx.getMappingResolver();
    }

    protected List<MappingMethod> getMappingsToGenerate() {
        return ctx.getMappingsToGenerate();
    }

    protected MappingContext getMappingContext() {
        return ctx;
    }

    protected FactoryMethod getFactoryMethod( Type returnType ) {
        FactoryMethod result = null;
        for ( SourceMethod method : getSourceModel() ) {
            if ( !method.overridesMethod() && !method.isIterableMapping() && !method.isMapMapping()
                    && method.getSourceParameters().isEmpty() ) {

                List<Type> parameterTypes = MethodSelectors.getParameterTypes(
                        getTypeFactory(),
                        method.getParameters(),
                        null,
                        returnType
                );

                if ( method.matches( parameterTypes, returnType ) ) {
                    if ( result == null ) {
                        MapperReference mapperReference = findMapperReference( getMapperReferences(), method );
                        result = AssignmentFactory.createFactory( method, mapperReference );
                    }
                    else {
                        getMessager().printMessage(
                                Diagnostic.Kind.ERROR,
                                String.format(
                                        "Ambiguous factory methods: \"%s\" conflicts with \"%s\".",
                                        result,
                                        method
                                ),
                                method.getExecutable()
                        );
                    }
                }
            }
        }
        return result;
    }

    private MapperReference findMapperReference( List<MapperReference> mapperReferences, SourceMethod method ) {
        for ( MapperReference ref : mapperReferences ) {
            if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
                return ref;
            }
        }
        return null;
    }
}
