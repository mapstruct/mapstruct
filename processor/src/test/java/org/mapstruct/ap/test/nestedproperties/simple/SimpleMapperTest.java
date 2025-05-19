/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedproperties.simple;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import org.mapstruct.ap.test.nestedproperties.simple._target.TargetObject;
import org.mapstruct.ap.test.nestedproperties.simple.source.SourceProps;
import org.mapstruct.ap.test.nestedproperties.simple.source.SourceRoot;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dennis Melzer
 */
@WithClasses({ SourceRoot.class, SourceProps.class, TargetObject.class })
@IssueKey("3852")
public class SimpleMapperTest {
    

    @ProcessorTest
    @WithClasses({ SimpleConstructorMapper.class })
    public void shouldOptionalNotNull() {
        TargetObject target = SimpleConstructorMapper.MAPPER.toTargetObject( null );

        assertThat( target.getSomeString() ).isEmpty();
        assertThat( target.getSomeInteger() ).isEmpty();
        assertThat( target.getSomeDouble() ).isEmpty();
        assertThat( target.getSomeBoolean() ).isEmpty();
        assertThat( target.getSomeIntValue() ).isEmpty();
        assertThat( target.getSomeDoubleValue() ).isEmpty();
        assertThat( target.getSomeLongValue() ).isEmpty();
    }

    @ProcessorTest
    @WithClasses({ SimpleConstructorMapper.class })
    public void shouldMapOptional() {
        SourceRoot sourceRoot = new SourceRoot( Optional.of( "someString" ), Optional.of( 10 ),
            Optional.of( 11D ), Optional.of( Boolean.TRUE), OptionalInt.of( 10 ),
            OptionalDouble.of( 100D ), OptionalLong.of( 200 ) );
        TargetObject target = SimpleConstructorMapper.MAPPER.toTargetObject( sourceRoot );

        assertThat( target.getSomeString() ).contains( "someString" );
        assertThat( target.getSomeInteger() ).contains( 10 );
        assertThat( target.getSomeDouble() ).contains( 11D );
        assertThat( target.getSomeBoolean() ).contains( Boolean.TRUE );
        assertThat( target.getSomeIntValue() ).hasValue( 10 );
        assertThat( target.getSomeDoubleValue() ).hasValue(100);
        assertThat( target.getSomeLongValue() ).hasValue(200);
    }

}
