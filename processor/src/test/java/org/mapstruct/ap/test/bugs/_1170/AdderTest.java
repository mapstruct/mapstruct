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
package org.mapstruct.ap.test.bugs._1170;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._1170._target.Target;
import org.mapstruct.ap.test.bugs._1170.source.Source;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Cornelius Dirmeier
 */
@WithClasses({
    Source.class,
    Target.class,
    AdderSourceTargetMapper.class,
    PetMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class AdderTest {

    @IssueKey("1170")
    @Test
    public void testWildcardAdder() {
        Source source = new Source();
        source.addWithoutWildcard( "mouse" );
        source.addWildcardInTarget( "mouse" );
        source.addWildcardInSource( "mouse" );
        source.addWildcardInBoth( "mouse" );
        source.addWildcardAdderToSetter( "mouse" );

        Target target = AdderSourceTargetMapper.INSTANCE.toTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getWithoutWildcards() ).containsExactly( 2L );
        assertThat( target.getWildcardInSources() ).containsExactly( 2L );
        assertThat( target.getWildcardInTargets() ).containsExactly( 2L );
        assertThat( target.getWildcardInBoths() ).containsExactly( 2L );
        assertThat( target.getWildcardAdderToSetters() ).containsExactly( 2L );
    }

    @IssueKey("1170")
    @Test
    public void testWildcardAdderTargetToSource() {
        Target target = new Target();
        target.addWithoutWildcard( 2L );
        target.addWildcardInTarget( 2L );
        target.getWildcardInSources().add( 2L );
        target.addWildcardInBoth( 2L );
        target.setWildcardAdderToSetters( Arrays.asList( 2L ) );

        Source source = AdderSourceTargetMapper.INSTANCE.toSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getWithoutWildcards() ).containsExactly( "mouse" );
        assertThat( source.getWildcardInSources() ).containsExactly( "mouse" );
        assertThat( source.getWildcardInTargets() ).containsExactly( "mouse" );
        assertThat( source.getWildcardInBoths() ).containsExactly( "mouse" );
        assertThat( source.getWildcardAdderToSetters() ).containsExactly( "mouse" );
    }

}
