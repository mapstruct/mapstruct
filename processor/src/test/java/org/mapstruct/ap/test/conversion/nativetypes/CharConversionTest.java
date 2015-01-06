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
package org.mapstruct.ap.test.conversion.nativetypes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

@WithClasses({
    CharSource.class,
    CharTarget.class,
    CharMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class CharConversionTest {

    @Test
    public void shouldApplyCharConversion() {
        CharSource source = new CharSource();
        source.setC( 'G' );

        CharTarget target = CharMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getC() ).isEqualTo( Character.valueOf( 'G' ) );
    }

    @Test
    public void shouldApplyReverseCharConversion() {
        CharTarget target = new CharTarget();
        target.setC( Character.valueOf( 'G' ) );

        CharSource source = CharMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getC() ).isEqualTo( 'G' );
    }

    @Test
    @IssueKey( "229" )
    public void wrapperToPrimitveIsNullSafe() {
        CharTarget target = new CharTarget();

        CharSource source = CharMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
    }
}
