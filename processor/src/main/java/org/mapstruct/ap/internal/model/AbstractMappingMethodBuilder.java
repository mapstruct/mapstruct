/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Element;

import org.mapstruct.ap.internal.model.beanmapping.MappingReferences;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.JSpecifyConstants;
import org.mapstruct.ap.internal.util.NullabilityResolver;
import org.mapstruct.ap.internal.util.Strings;

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
            return Collections.emptyList();
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

    /**
     * Propagates the JSpecify {@code @Nullable} annotations of the overridden mapper method onto the generated
     * implementation's return type and source parameters.
     * <p>
     * Only explicit {@code @Nullable} annotations are reproduced. A {@code @NonNull} nullability is left out on
     * purpose: within a {@code @NullMarked} scope it is the implied default, so emitting it would add noise to every
     * generated signature without changing the contract.
     * <p>
     * Nothing is propagated for methods that do not override a mapper method (e.g. forged methods), since those are
     * generated helpers that do not have to match an inherited signature.
     *
     * @param mappingMethod the mapping method to annotate
     */
    protected void propagateNullability(MappingMethod mappingMethod) {
        if ( method instanceof ForgedMethod || !method.overridesMethod() ) {
            return;
        }

        Type nullableType = null;

        if ( isJSpecifyNullable( method.getExecutable() ) ) {
            nullableType = getNullableAnnotationType();
            mappingMethod.setNullableReturnTypeAnnotationType( nullableType );
        }

        for ( Parameter parameter : mappingMethod.getParameters() ) {
            if ( parameter.getElement() != null && isJSpecifyNullable( parameter.getElement() ) ) {
                if ( nullableType == null ) {
                    nullableType = getNullableAnnotationType();
                }
                parameter.setNullableAnnotationType( nullableType );
            }
        }
    }

    private boolean isJSpecifyNullable(Element element) {
        return ctx.getNullabilityInMapperScope( element ) == NullabilityResolver.Nullability.NULLABLE;
    }

    private Type getNullableAnnotationType() {
        return ctx.getTypeFactory().getType( JSpecifyConstants.NULLABLE_FQN );
    }

}
