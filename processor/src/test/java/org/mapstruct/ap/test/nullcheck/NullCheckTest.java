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
package org.mapstruct.ap.test.nullcheck;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for correct handling of null checks.
 *
 * @author Sjaak Derksen
 */
@WithClasses({
    SourceTargetMapper.class,
    NullObjectMapper.class,
    NullObject.class,
    CustomMapper.class,
    MyBigIntWrapper.class,
    MyLongWrapper.class,
    Source.class,
    Target.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class NullCheckTest {

    @Test(expected = NullPointerException.class)
    @IssueKey("214")
    public void shouldThrowNullptrWhenCustomMapperIsInvoked() {

        Source source = new Source();
        source.setNumber( "5" );
        source.setSomeInteger( 7 );
        source.setSomeLong( 2L );

        SourceTargetMapper.INSTANCE.sourceToTarget( source );
    }

    @Test
    @IssueKey("214")
    public void shouldSurroundTypeConversionWithNullCheck() {

        Source source = new Source();
        source.setSomeObject( new NullObject() );
        source.setSomeInteger( 7 );
        source.setSomeLong( 2L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getNumber() ).isNull();

    }

    @Test
    @IssueKey("214")
    public void shouldSurroundArrayListConstructionWithNullCheck() {

        Source source = new Source();
        source.setSomeObject( new NullObject() );
        source.setSomeInteger( 7 );
        source.setSomeLong( 2L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getSomeList() ).isNull();
    }

    @Test
    @IssueKey("237")
    public void shouldSurroundConversionPassedToMappingMethodWithNullCheck() {

        Source source = new Source();
        source.setSomeObject( new NullObject() );
        source.setSomeLong( 2L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getSomeList() ).isNull();
        assertThat( target.getSomeInteger() ).isNull();
    }

    @Test
    @IssueKey("231")
    public void shouldSurroundConversionFromWrappedPassedToMappingMethodWithPrimitiveArgWithNullCheck() {

        Source source = new Source();
        source.setSomeObject( new NullObject() );
        source.setSomeInteger( 7 );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getSomeList() ).isNull();
        assertThat( target.getSomeLong() ).isNull();
    }
}
