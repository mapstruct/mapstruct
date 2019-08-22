/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.lossy;

import java.math.BigInteger;

/**
 * @author Sjaak Derksen
 */
public class VerySpecialNumberMapper {

    VerySpecialNumber fromFloat(float f) {
        return new VerySpecialNumber();
    }

    BigInteger toBigInteger(VerySpecialNumber v) {
        return new BigInteger( "10" );
    }
}
