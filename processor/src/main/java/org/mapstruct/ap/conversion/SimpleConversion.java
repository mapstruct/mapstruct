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
package org.mapstruct.ap.conversion;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.model.TypeConversion;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.Type;

/**
 * Base class for {@link ConversionProvider}s creating {@link TypeConversion}s which don't declare any exception types.
 *
 * @author Gunnar Morling
 */
public abstract class SimpleConversion implements ConversionProvider {

    @Override
    public TypeConversion to(String sourceReference, ConversionContext conversionContext) {
        return new TypeConversion(
            getToConversionImportTypes( conversionContext ),
            Collections.<Type> emptyList(),
            getToConversionString( sourceReference, conversionContext )
        );
    }

    @Override
    public TypeConversion from(String targetReference, ConversionContext conversionContext) {
        return new TypeConversion(
            getFromConversionImportTypes( conversionContext ),
            Collections.<Type> emptyList(),
            getFromConversionString( targetReference, conversionContext )
        );
    }

    /**
     * @param conversionContext the conversion context
     * @return conversion types required in the from-conversion
     */
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.<Type> emptySet();
    }

    /**
     * @param conversionContext the conversion context
     * @return conversion types required in the to-conversion
     */
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.<Type> emptySet();
    }

    /**
     * Returns the conversion string from source to target.
     *
     * @param sourceReference A reference to the source object, e.g. {@code beanName.getFoo()}.
     * @param conversionContext ConversionContext providing optional information required for creating the conversion.
     * @return The conversion string from source to target.
     */
    protected abstract String getToConversionString(String sourceReference, ConversionContext conversionContext);

    /**
     * Creates the conversion string from target to source.
     *
     * @param targetReference A reference to the targetReference object, e.g.
     * {@code beanName.getFoo()}.
     * @param conversionContext ConversionContext providing optional information required for creating
 the conversion.
     *
     * @return The conversion string from target to source.
     */
    protected abstract String getFromConversionString(String targetReference, ConversionContext conversionContext);
}
