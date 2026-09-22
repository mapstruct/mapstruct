/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.Optional;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.GeneratedTypeMethod;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.MappingMethod;
import org.mapstruct.ap.internal.model.NormalTypeMappingMethod;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.accessor.Nullability;
import org.mapstruct.ap.internal.util.accessor.NullabilityResolver;

import static org.mapstruct.ap.internal.util.accessor.JSpecifyConstants.NON_NULL_FQN;
import static org.mapstruct.ap.internal.util.accessor.JSpecifyConstants.NULLABLE_FQN;
import static org.mapstruct.ap.internal.util.accessor.JSpecifyConstants.NULL_MARKED_FQN;
import static org.mapstruct.ap.internal.util.accessor.JSpecifyConstants.NULL_UNMARKED_FQN;

public class NullabilityAnnotationProcessor implements ModelElementProcessor<Mapper, Mapper> {

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, Mapper mapper) {
        NullabilityResolver nullabilityResolver = context.getNullabilityResolver();
        if ( !nullabilityResolver.isJSpecifyEnabled() ) {
            return mapper;
        }
        NullabilityResolver.JspecifyNullabilityScope packageNullabilityScope =
                nullabilityResolver.getPackageNullabilityScope(
                        context.getElementUtils().getPackageElement( mapper.getPackageName() ) );
        NullabilityResolver.JspecifyNullabilityScope innerScope = packageNullabilityScope;
        NullabilityResolver.JspecifyNullabilityScope jspecifyNullabilityScope =
                nullabilityResolver.getParentTypeNullabilityScope(  mapperTypeElement );
        if ( packageNullabilityScope.needsScopeAnnotation( jspecifyNullabilityScope ) ) {
            mapper.addAnnotation( createNullabilityScopeAnnotation( context, jspecifyNullabilityScope ) );
            innerScope = jspecifyNullabilityScope;
        }
        for ( GeneratedTypeMethod method : mapper.getMethods() ) {
            if ( method instanceof MappingMethod ) {
                NullabilityResolver.JspecifyNullabilityScope scopeForTypes = innerScope;
                MappingMethod mappingMethod = (MappingMethod) method;
                if ( mappingMethod instanceof NormalTypeMappingMethod ) {
                    NormalTypeMappingMethod normalMappingMethod = (NormalTypeMappingMethod) method;
                    NullabilityResolver.JspecifyNullabilityScope methodScope =
                            nullabilityResolver.getMethodNullabilityScope( normalMappingMethod.getExecutable() );
                    if ( innerScope.needsScopeAnnotation( methodScope ) ) {
                        Annotation annotation = createNullabilityScopeAnnotation( context, methodScope );
                        normalMappingMethod.addAnnotation( annotation );
                        mapper.addExtraImportedType( annotation.getType() );
                        scopeForTypes = methodScope;
                    }
                }
                Nullability returnTypeNullability = mappingMethod.getReturnTypeNullability();
                if ( scopeForTypes.needsAnnotation( returnTypeNullability ) ) {
                    createNullabilityAnnotationType( context, returnTypeNullability )
                            .ifPresent( nullabilityAnnotation -> {
                                mappingMethod.addTypeAnnotation( nullabilityAnnotation );
                                mapper.addExtraImportedType( nullabilityAnnotation );
                            } );
                }
                for ( Parameter parameter : mappingMethod.getParameters() ) {
                    if ( scopeForTypes.needsAnnotation( parameter.getNullability() ) ) {
                        createNullabilityAnnotationType( context, parameter.getNullability() )
                                .ifPresent( nullabilityAnnotation -> {
                                    parameter.setTypeAnnotation( nullabilityAnnotation );
                                    mapper.addExtraImportedType( nullabilityAnnotation );
                                } );
                    }
                }
            }
        }
        return mapper;
    }

    private static Annotation createNullabilityScopeAnnotation(ProcessorContext context,
                                                               NullabilityResolver.JspecifyNullabilityScope
                                                                       jspecifyNullabilityScope) {
        return new Annotation( context.getTypeFactory().getType(
                jspecifyNullabilityScope == NullabilityResolver.JspecifyNullabilityScope.NULL_MARKED
                        ? NULL_MARKED_FQN : NULL_UNMARKED_FQN ) );
    }

    private static Optional<Type> createNullabilityAnnotationType(ProcessorContext context, Nullability nullability) {
        String canonicalName = nullability.getState() == Nullability.NullabilityState.NON_NULL
                ? NON_NULL_FQN : NULLABLE_FQN;
        if ( !context.getTypeFactory().isTypeAvailable(  canonicalName ) ) {
            return Optional.empty();
        }
        return Optional.of( context.getTypeFactory().getType( canonicalName ) );
    }

    @Override
    public int getPriority() {
        return 1200;
    }
}
