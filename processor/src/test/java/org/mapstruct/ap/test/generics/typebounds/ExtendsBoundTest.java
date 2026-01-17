/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.typebounds;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2677")
@WithClasses({
    ExtendsBoundMapper.class
})
class ExtendsBoundTest {

    @ProcessorTest
    void shouldCorrectlyUseGenericsWithExtends() {
        ExtendsBoundMapper.Parent parent = new ExtendsBoundMapper.Parent( 10 );
        ExtendsBoundMapper.Child child = new ExtendsBoundMapper.Child( 15, "Test" );

        ExtendsBoundMapper.Output output = ExtendsBoundMapper.INSTANCE.map( new ExtendsBoundMapper.Wrapper<>(
            parent,
            "extends"
        ) );

        assertThat( output.getStatus() ).isEqualTo( "extends" );
        assertThat( output.getId() ).isEqualTo( 10 );

        output = ExtendsBoundMapper.INSTANCE.mapFromChild( new ExtendsBoundMapper.Wrapper<>(
            child,
            "child"
        ) );

        assertThat( output.getStatus() ).isEqualTo( "child" );
        assertThat( output.getId() ).isEqualTo( 15 );

        output = ExtendsBoundMapper.INSTANCE.mapFromParent( new ExtendsBoundMapper.Wrapper<>(
            parent,
            "parent"
        ) );

        assertThat( output.getStatus() ).isEqualTo( "parent" );
        assertThat( output.getId() ).isEqualTo( 10 );

        output = ExtendsBoundMapper.INSTANCE.mapImplicitly( new ExtendsBoundMapper.Wrapper<>(
            child,
            "implicit"
        ) );

        assertThat( output.getStatus() ).isEqualTo( "implicit" );
        assertThat( output.getId() ).isEqualTo( 15 );

        ExtendsBoundMapper.Wrapper<String> result = ExtendsBoundMapper.INSTANCE.mapToWrapper(
            "test",
            new ExtendsBoundMapper.Wrapper<>(
                child,
                "super"
            )
        );

        assertThat( result.getStatus() ).isEqualTo( "super" );
        assertThat( result.getValue() ).isEqualTo( "test" );

        output = ExtendsBoundMapper.INSTANCE.mapWithPresenceCheck(
            new ExtendsBoundMapper.Wrapper<>(
                new ExtendsBoundMapper.ParentWithPresenceCheck( 8 ),
                "presenceCheck"
            )
        );

        assertThat( output.getStatus() ).isEqualTo( "presenceCheck" );
        assertThat( output.getId() ).isEqualTo( 0 );

        output = ExtendsBoundMapper.INSTANCE.mapWithPresenceCheck(
            new ExtendsBoundMapper.Wrapper<>(
                new ExtendsBoundMapper.ParentWithPresenceCheck( 15 ),
                "presenceCheck"
            )
        );

        assertThat( output.getStatus() ).isEqualTo( "presenceCheck" );
        assertThat( output.getId() ).isEqualTo( 15 );

    }
}
