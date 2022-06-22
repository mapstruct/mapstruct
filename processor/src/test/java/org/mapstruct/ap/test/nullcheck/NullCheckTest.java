/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

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
public class NullCheckTest {

    @ProcessorTest
    @IssueKey("214")
    public void shouldThrowNullptrWhenCustomMapperIsInvoked() {

        Source source = new Source();
        source.setNumber( "5" );
        source.setSomeInteger( 7 );
        source.setSomeLong( 2L );

        assertThatThrownBy( () -> SourceTargetMapper.INSTANCE.sourceToTarget( source ) )
            .isInstanceOf( NullPointerException.class );
    }

    @ProcessorTest
    @IssueKey("214")
    public void shouldSurroundTypeConversionWithNullCheck() {

        Source source = new Source();
        source.setSomeObject( new NullObject() );
        source.setSomeInteger( 7 );
        source.setSomeLong( 2L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getNumber() ).isNull();

    }

    @ProcessorTest
    @IssueKey("214")
    public void shouldSurroundArrayListConstructionWithNullCheck() {

        Source source = new Source();
        source.setSomeObject( new NullObject() );
        source.setSomeInteger( 7 );
        source.setSomeLong( 2L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getSomeList() ).isNull();
    }

    @ProcessorTest
    @IssueKey("237")
    public void shouldSurroundConversionPassedToMappingMethodWithNullCheck() {

        Source source = new Source();
        source.setSomeObject( new NullObject() );
        source.setSomeLong( 2L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getSomeList() ).isNull();
        assertThat( target.getSomeInteger() ).isNull();
    }

    @ProcessorTest
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
