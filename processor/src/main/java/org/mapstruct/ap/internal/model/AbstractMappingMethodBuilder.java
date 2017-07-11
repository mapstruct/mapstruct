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

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.ForgedMethodHistory;
import org.mapstruct.ap.internal.util.Strings;

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
        name = Strings.getSaveVariableName( name, ctx.getNamesOfMappingsToGenerate() );
        ForgedMethodHistory history = null;
        if ( method instanceof ForgedMethod ) {
            history = ( (ForgedMethod) method ).getHistory();
        }
        ForgedMethod forgedMethod = new ForgedMethod(
            name,
            sourceType,
            targetType,
            method.getMapperConfiguration(),
            method.getExecutable(),
            method.getContextParameters(),
            method.getContextProvidedMethods(),
            new ForgedMethodHistory(
                history,
                Strings.stubPropertyName( sourceRHS.getSourceType().getName() ),
                Strings.stubPropertyName( targetType.getName() ),
                sourceRHS.getSourceType(),
                targetType,
                shouldUsePropertyNamesInHistory(),
                sourceRHS.getSourceErrorMessagePart()
            ),
            null,
            true
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
}
