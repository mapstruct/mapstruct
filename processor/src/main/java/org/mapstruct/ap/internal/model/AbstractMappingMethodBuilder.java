/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.BeanMapping;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.model.ForgedMethod.forElementMapping;

/**
 * An abstract builder that can be reused for building {@link MappingMethod}(s).
 *
 * @author Filip Hrisafov
 */
public abstract class AbstractMappingMethodBuilder<B extends AbstractMappingMethodBuilder<B, M>,
    M extends MappingMethod> extends AbstractBaseBuilder<B> {

    public AbstractMappingMethodBuilder(Class<B> selfType) {
        super( selfType );
    }

    public abstract M build();

    /**
     * @return {@code true} if property names should be used for the creation of the {@link ForgedMethodHistory}.
     */
    protected abstract boolean shouldUsePropertyNamesInHistory();

    Assignment forgeMapping(SourceRHS sourceRHS, Type sourceType, Type targetType) {
        if ( !canGenerateAutoSubMappingBetween( sourceType, targetType ) ) {
            return null;
        }

        String name = getName( sourceType, targetType );
        name = Strings.getSafeVariableName( name, ctx.getReservedNames() );
        ForgedMethodHistory history = null;
        if ( method instanceof ForgedMethod ) {
            history = ( (ForgedMethod) method ).getHistory();
        }

        ForgedMethodHistory forgedHistory = new ForgedMethodHistory(
            history,
            Strings.stubPropertyName( sourceRHS.getSourceType().getName() ),
            Strings.stubPropertyName( targetType.getName() ),
            sourceRHS.getSourceType(),
            targetType,
            shouldUsePropertyNamesInHistory(),
            sourceRHS.getSourceErrorMessagePart() );

        ForgedMethod forgedMethod = forElementMapping( name, sourceType, targetType, method, forgedHistory, true );

        return createForgedAssignment(
                        sourceRHS,
                        ctx.getTypeFactory()
                           .builderTypeFor( targetType, BeanMapping.builderPrismFor( method ).orElse( null ) ),
                        forgedMethod
        );
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
}
