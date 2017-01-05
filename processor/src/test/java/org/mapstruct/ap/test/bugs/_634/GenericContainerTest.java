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
package org.mapstruct.ap.test.bugs._634;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Gunnar Morling
 */
@WithClasses({
    Bar.class,
    Foo.class,
    Source.class,
    Target.class,
    SourceTargetMapper.class,
})
@RunWith(AnnotationProcessorTestRunner.class)
public class GenericContainerTest {

    @Test
    @IssueKey("634")
    public void canMapGenericSourceTypeToGenericTargetType() {
        List<Foo> items = Arrays.asList( new Foo( "42" ), new Foo( "84" ) );
        Source<Foo> source = new Source<Foo>( items );

        Target<Bar> target = SourceTargetMapper.INSTANCE.mapSourceToTarget( source );

        assertThat( target.getContent() ).extracting( "value" ).containsExactly( 42L, 84L );
    }
}
