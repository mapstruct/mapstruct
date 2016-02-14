/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ciaran Liedeman
 */
public class NativeTypesTest {

    @Test
    public void testIsNumber() throws Exception {
        assertFalse( NativeTypes.isNumber( null ) );
        assertFalse( NativeTypes.isNumber( Object.class ) );
        assertFalse( NativeTypes.isNumber( String.class ) );

        assertTrue( NativeTypes.isNumber( double.class ) );
        assertTrue( NativeTypes.isNumber( Double.class ) );
        assertTrue( NativeTypes.isNumber( long.class ) );
        assertTrue( NativeTypes.isNumber( Long.class ) );
        assertTrue( NativeTypes.isNumber( BigDecimal.class ) );
        assertTrue( NativeTypes.isNumber( BigInteger.class ) );
    }
}
