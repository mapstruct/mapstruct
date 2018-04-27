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
package org.mapstruct.ap.test.source.constants;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-28T11:42:09+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class NumericMapperImpl implements NumericMapper {

    @Override
    public NumericTarget mapFromConstants(String dummy) {
        if ( dummy == null ) {
            return null;
        }

        NumericTarget numericTarget = new NumericTarget();

        numericTarget.setByteBoxed( (byte) -128 );
        numericTarget.setDoubleBoxed( (double) 0x0.001P-1062d );
        numericTarget.setIntValue( -03777777 );
        numericTarget.setLongBoxed( (long) 0xCAFEBABEL );
        numericTarget.setFloatBoxed( 3.4028235e38f );
        numericTarget.setFloatValue( 1.40e-45f );
        numericTarget.setIntBoxed( 15 );
        numericTarget.setDoubleValue( 1e137 );
        numericTarget.setLongValue( 0x7fffffffffffffffL );
        numericTarget.setShortBoxed( (short) -1996 );
        numericTarget.setShortValue( (short) 1996 );
        numericTarget.setByteValue( (byte) 20 );

        return numericTarget;
    }
}
