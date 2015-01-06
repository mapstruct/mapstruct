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
package org.mapstruct.ap.test.references.samename;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.references.samename.a.AnotherSourceTargetMapper;
import org.mapstruct.ap.test.references.samename.a.CustomMapper;
import org.mapstruct.ap.test.references.samename.model.Source;
import org.mapstruct.ap.test.references.samename.model.Target;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for referring several mappers with the same simple name.
 *
 * @author Gunnar Morling
 */
@IssueKey("112")
@WithClasses({
    Source.class,
    Target.class,
    SourceTargetMapper.class,
    CustomMapper.class,
    org.mapstruct.ap.test.references.samename.b.CustomMapper.class,
    Jsr330SourceTargetMapper.class,
    AnotherSourceTargetMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class SeveralReferencedMappersWithSameSimpleNameTest {

    @Test
    public void severalMappersWithSameSimpleNameCanBeReferenced() {
        Source source = new Source();
        source.setFoo( 123 );
        source.setBar( 456L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( "246" );
        assertThat( target.getBar() ).isEqualTo( "912" );
    }

    @Test
    public void mapperInSamePackageAndAnotherMapperWithSameNameInAnotherPackageCanBeReferenced() {
        Source source = new Source();
        source.setFoo( 123 );
        source.setBar( 456L );

        Target target = AnotherSourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( "246" );
        assertThat( target.getBar() ).isEqualTo( "912" );
    }
}
