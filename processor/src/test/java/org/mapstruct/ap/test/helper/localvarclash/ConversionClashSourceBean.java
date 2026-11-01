/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.localvarclash;

import org.jspecify.annotations.Nullable;

public class ConversionClashSourceBean {

    @Nullable
    private ConversionClashInput conversionClashInput;

    @Nullable
    public ConversionClashInput getConversionClashInput() {
        return conversionClashInput;
    }

    public void setConversionClashInput(@Nullable ConversionClashInput conversionClashInput) {
        this.conversionClashInput = conversionClashInput;
    }
}
