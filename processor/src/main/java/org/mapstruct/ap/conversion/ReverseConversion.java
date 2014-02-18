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

import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.TypeConversion;

/**
 * A {@link ConversionProvider} which creates the reversed conversions for a
 * given conversion provider.
 *
 * @author Gunnar Morling
 */
public class ReverseConversion implements ConversionProvider {

    private ConversionProvider conversionProvider;

    public static ReverseConversion reverse(ConversionProvider conversionProvider) {
        return new ReverseConversion( conversionProvider );
    }

    private ReverseConversion(ConversionProvider conversionProvider) {
        this.conversionProvider = conversionProvider;
    }

    @Override
    public TypeConversion to(String sourceReference, ConversionContext conversionContext) {
        return conversionProvider.from( sourceReference, conversionContext );
    }

    @Override
    public TypeConversion from(String targetReference, ConversionContext conversionContext) {
        return conversionProvider.to( targetReference, conversionContext );
    }
}
