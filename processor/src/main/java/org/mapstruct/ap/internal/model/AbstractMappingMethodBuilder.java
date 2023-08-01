/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.model.beanmapping.MappingReferences;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An abstract builder that can be reused for building {@link MappingMethod}(s).
 *
 * @author Filip Hrisafov
 */
public abstract class AbstractMappingMethodBuilder<B extends AbstractMappingMethodBuilder<B, M>,
                M extends MappingMethod>
    extends AbstractBaseBuilder<B> {

    public AbstractMappingMethodBuilder(Class<B> selfType) {
        super( selfType );
    }

    private interface ForgeMethodCreator {
        ForgedMethod createMethod(String name, Type sourceType, Type returnType, Method basedOn,
                                  ForgedMethodHistory history, boolean forgedNameBased);

        static ForgeMethodCreator forSubclassMapping(MappingReferences mappingReferences) {
            return (name, sourceType, targetType, method, description,
                    forgedNameBased) -> ForgedMethod
                                                    .forSubclassMapping(
                                                        name,
                                                        sourceType,
                                                        targetType,
                                                        method,
                                                        mappingReferences,
                                                        description,
                                                        forgedNameBased );
        }
    }

    public abstract M build();

    private ForgedMethodHistory description;

    /**
     * @return {@code true} if property names should be used for the creation of the {@link ForgedMethodHistory}.
     */
    protected abstract boolean shouldUsePropertyNamesInHistory();

    Assignment forgeMapping(SourceRHS sourceRHS, Type sourceType, Type targetType) {
        return forgeMapping( sourceRHS, sourceType, targetType, ForgedMethod::forElementMapping );
    }

    Assignment forgeSubclassMapping(SourceRHS sourceRHS, Type sourceType, Type targetType,
                                    MappingReferences mappingReferences) {
        return forgeMapping(
            sourceRHS,
            sourceType,
            targetType,
            ForgeMethodCreator.forSubclassMapping( mappingReferences ) );
    }

    private Assignment forgeMapping(SourceRHS sourceRHS, Type sourceType, Type targetType,
                            ForgeMethodCreator forgeMethodCreator) {
        if ( !canGenerateAutoSubMappingBetween( sourceType, targetType ) ) {
            return null;
        }

        String name = getName( sourceType, targetType );
        name = Strings.getSafeVariableName( name, ctx.getReservedNames() );
        ForgedMethodHistory history = null;
        if ( method instanceof ForgedMethod ) {
            history = ( (ForgedMethod) method ).getHistory();
        }

        description = new ForgedMethodHistory(
            history,
            Strings.stubPropertyName( sourceRHS.getSourceType().getName() ),
            Strings.stubPropertyName( targetType.getName() ),
            sourceRHS.getSourceType(),
            targetType,
            shouldUsePropertyNamesInHistory(),
            sourceRHS.getSourceErrorMessagePart() );

        ForgedMethod forgedMethod =
            forgeMethodCreator.createMethod( name, sourceType, targetType, method, description, true );
        BuilderGem builder = method.getOptions().getBeanMapping().getBuilder();

        return createForgedAssignment(
            sourceRHS,
            ctx.getTypeFactory().builderTypeFor( targetType, builder ),
            forgedMethod );
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
            return Collections.emptyList();
        }
        AdditionalAnnotationsBuilder additionalAnnotationsBuilder =
                new AdditionalAnnotationsBuilder(
                        ctx.getElementUtils(),
                        ctx.getTypeFactory(),
                        ctx.getMessager() );
        return new ArrayList<>( additionalAnnotationsBuilder.getProcessedAnnotations( method.getExecutable() ) );
    }

}
