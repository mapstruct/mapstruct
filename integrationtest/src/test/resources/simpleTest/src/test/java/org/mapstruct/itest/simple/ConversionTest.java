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
package org.mapstruct.itest.simple;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.mapstruct.itest.simple.Source;
import org.mapstruct.itest.simple.SourceTargetMapper;
import org.mapstruct.itest.simple.Target;

public class ConversionTest {

    @Test
    public void shouldApplyConversions() {
        Source source = new Source();
        source.setFoo( 42 );
        source.setBar( 23L );
        source.setZip( 73 );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 43 ) );
        assertThat( target.getBar() ).isEqualTo( 23 );
        assertThat( target.getZip() ).isEqualTo( "73" );
    }

    @Test
    public void shouldHandleNulls() {
        Source source = new Source();
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 1 ) );
        assertThat( target.getBar() ).isEqualTo( 0 );
        assertThat( target.getZip() ).isEqualTo( "0" );
    }

    @Test
    public void shouldApplyConversionsToMappedProperties() {
        Source source = new Source();
        source.setQax( 42 );
        source.setBaz( 23L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getBaz() ).isEqualTo( Long.valueOf( 43 ) );
        assertThat( target.getQax() ).isEqualTo( 23 );
    }

    @Test
    public void shouldApplyConversionsForReverseMapping() {
        Target target = new Target();
        target.setFoo( 42L );
        target.setBar( 23 );
        target.setZip( "73" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getFoo() ).isEqualTo( 42 );
        assertThat( source.getBar() ).isEqualTo( 24 );
        assertThat( source.getZip() ).isEqualTo( 73 );
    }

    @Test
    public void shouldApplyConversionsToMappedPropertiesForReverseMapping() {
        Target target = new Target();
        target.setQax( 42 );
        target.setBaz( 23L );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getBaz() ).isEqualTo( 43 );
        assertThat( source.getQax() ).isEqualTo( 23 );
    }

    @Test
    public void shouldWorkWithAbstractClass() {
        Source source = new Source();
        source.setFoo( 42 );
        source.setBar( 23L );
        source.setZip( 73 );

        Target target = SourceTargetAbstractMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 43 ) );
        assertThat( target.getBar() ).isEqualTo( 23 );
        assertThat( target.getZip() ).isEqualTo( "73" );
    }
}
