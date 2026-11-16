/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.constants;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-28T11:42:09+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class ConstantsMapperImpl implements ConstantsMapper {

    @Override
    public ConstantsTarget mapFromConstants(String dummy) {
        if ( dummy == null ) {
            return null;
        }

        ConstantsTarget constantsTarget = new ConstantsTarget();

        constantsTarget.setBooleanValue( true );
        constantsTarget.setBooleanBoxed( false );
        constantsTarget.setCharValue( 'b' );
        constantsTarget.setCharBoxed( 'a' );
        constantsTarget.setByteValue( (byte) 20 );
        constantsTarget.setByteBoxed( (byte) -128 );
        constantsTarget.setShortValue( (short) 1996 );
        constantsTarget.setShortBoxed( (short) -1996 );
        constantsTarget.setIntValue( -03777777 );
        constantsTarget.setIntBoxed( 15 );
        constantsTarget.setLongValue( 0x7fffffffffffffffL );
        constantsTarget.setLongBoxed( 0xCAFEBABEL );
        constantsTarget.setFloatValue( 1.40e-45f );
        constantsTarget.setFloatBoxed( 3.4028235e38f );
        constantsTarget.setDoubleValue( 1e137 );
        constantsTarget.setDoubleBoxed( 0x0.001P-1062d );
        constantsTarget.setDoubleBoxedZero( 0.0 );

        return constantsTarget;
    }
}
