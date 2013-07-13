/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.collection.map;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

/**
 * Test for implementation of {@code Map} mapping methods.
 *
 * @author Gunnar Morling
 */
@WithClasses({ SourceTargetMapper.class, CustomNumberMapper.class })
@IssueKey("44")
public class MapMappingTest extends MapperTestBase {

    @BeforeMethod
    public void setDefaultLocale() {
        Locale.setDefault( Locale.GERMAN );
    }

    @Test
    public void shouldCreateMapMethodImplementation() {
        Map<Long, Date> values = new HashMap<Long, Date>();
        values.put( 42L, new GregorianCalendar( 1980, 0, 1 ).getTime() );
        values.put( 121L, new GregorianCalendar( 2013, 6, 20 ).getTime() );

        Map<String, String> target = SourceTargetMapper.INSTANCE.longDateMapToStringStringMap( values );

        assertThat( target ).isNotNull();
        assertThat( target ).hasSize( 2 );
        assertThat( target ).includes( entry( "42", "01.01.80 00:00" ), entry( "121", "20.07.13 00:00" ) );
    }

    @Test
    public void shouldCreateReverseMapMethodImplementation() {
        Map<String, String> values = new HashMap<String, String>();
        values.put( "42", "01.01.80 00:00" );
        values.put( "121", "20.07.13 00:00" );

        Map<Long, Date> target = SourceTargetMapper.INSTANCE.stringStringMapToLongDateMap( values );

        assertThat( target ).isNotNull();
        assertThat( target ).hasSize( 2 );
        assertThat( target ).includes(
            entry( 42L, new GregorianCalendar( 1980, 0, 1 ).getTime() ),
            entry( 121L, new GregorianCalendar( 2013, 6, 20 ).getTime() )
        );
    }
}
