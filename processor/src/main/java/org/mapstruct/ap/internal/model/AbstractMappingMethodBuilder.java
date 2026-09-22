/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.mapstruct.ap.internal.model.beanmapping.MappingReferences;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeInstance;
import org.mapstruct.ap.internal.model.source.MappingMethodOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Nullability;

/**
 * An abstract builder that can be reused for building {@link MappingMethod}(s).
 *
 * @param <B> the builder itself that needs to be used for chaining
 * @param <M> the method that the builder builds
 * @author Filip Hrisafov
 */
public abstract class AbstractMappingMethodBuilder<B extends AbstractMappingMethodBuilder<B, M>,
                M extends MappingMethod>
    extends AbstractBaseBuilder<B> {

    public AbstractMappingMethodBuilder(Class<B> selfType) {
        super( selfType );
    }

    private interface ForgeMethodCreator {
        ForgedMethod createMethod(String name, TypeInstance source, TypeInstance target,
                                   Method basedOn, ForgedMethodHistory history, boolean forgedNameBased,
                                   Predicate<MappingMethodOptions> returnDefaultValue);

        static ForgeMethodCreator forSubclassMapping(MappingReferences mappingReferences) {
            return (name, source, target, method, description,
                    forgedNameBased, returnDefaultValue) -> ForgedMethod
                        .forSubclassMapping(
                            name,
                            source,
                            target,
                            method,
                            mappingReferences,
                            description,
                            forgedNameBased,
                            returnDefaultValue );
        }
    }

    public abstract M build();

    private ForgedMethodHistory description;

    /**
     * @return {@code true} if property names should be used for the creation of the {@link ForgedMethodHistory}.
     */
    protected abstract boolean shouldUsePropertyNamesInHistory();

    Assignment forge(SourceRHS sourceRHS, Type sourceType, TypeInstance targetType, Message message) {
        Assignment assignment = forgeMapping( sourceRHS, sourceType, targetType );
        if ( assignment != null ) {
            ctx.getMessager().note( 2, message, assignment );
        }
        return assignment;
    }

    Assignment forgeMapping(SourceRHS sourceRHS, Type sourceType, TypeInstance targetType) {
        return forgeMapping( sourceRHS,
                TypeInstance.of( sourceType, sourceRHS.getSourceNullability() ),
                targetType,
                ForgedMethod::forElementMapping );
    }

    Assignment forgeSubclassMapping(SourceRHS sourceRHS, Type sourceType, TypeInstance targetType,
                                    MappingReferences mappingReferences) {
        return forgeMapping(
            sourceRHS,
            TypeInstance.of( sourceType, Nullability.hardcodedNullability( Nullability.NullabilityState.NON_NULL ) ),
            targetType,
            ForgeMethodCreator.forSubclassMapping( mappingReferences ) );
    }

    private Assignment forgeMapping(SourceRHS sourceRHS, TypeInstance sourceType, TypeInstance targetType,
                                   ForgeMethodCreator forgeMethodCreator) {
        if ( !canGenerateAutoSubMappingBetween( sourceType.getType(), targetType.getType() ) ) {
            return null;
        }

        String name = getName( sourceType.getType(), targetType.getType() );
        name = Strings.getSafeVariableName( name, ctx.getReservedNames() );
        ForgedMethodHistory history = null;
        if ( method instanceof ForgedMethod ) {
            history = ( (ForgedMethod) method ).getHistory();
        }

        description = new ForgedMethodHistory(
            history,
            Strings.stubPropertyName( sourceRHS.getSourceType().getName() ),
            Strings.stubPropertyName( targetType.getType().getName() ),
            sourceRHS.getSourceType(),
            targetType.getType(),
            shouldUsePropertyNamesInHistory(),
            sourceRHS.getSourceErrorMessagePart() );

        ForgedMethod forgedMethod =
            forgeMethodCreator.createMethod( name, sourceType, targetType,
                    method,
                    description, true,
                    ForgedMethod.NEVER_RETURN_DEFAULT
            );

        return createForgedAssignment( sourceRHS, forgedMethod );
    }

    private String getName(Type sourceType, Type targetType) {
        String fromName = getName( sourceType );
        String toName = getName( targetType );
        return Strings.decapitalize( fromName + "To" + toName );
    }

    private String getName(Type type) {
        StringBuilder builder = new StringBuilder();
        for ( Type typeParam : type.getTypeParameters() ) {
            builder.append( typeParam.getIdentification() );
        }
        builder.append( type.getIdentification() );
        return builder.toString();
    }

    public ForgedMethodHistory getDescription() {
        return description;
    }

    public List<Annotation> getMethodAnnotations() {
        if ( method instanceof ForgedMethod ) {
            return new ArrayList<>();
        }
        AdditionalAnnotationsBuilder additionalAnnotationsBuilder =
                new AdditionalAnnotationsBuilder(
                        ctx.getElementUtils(),
                        ctx.getTypeFactory(),
                        ctx.getMessager() );
        List<Annotation> annotations = new ArrayList<>(
            additionalAnnotationsBuilder.getProcessedAnnotations( method.getExecutable() )
        );

        if ( method.overridesMethod() ) {
            annotations.add( new Annotation( ctx.getTypeFactory().getType( Override.class ) ) );
        }
        return annotations;
    }

}
