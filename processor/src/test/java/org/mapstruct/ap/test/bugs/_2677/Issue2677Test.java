/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2677;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2677")
@WithClasses({
    Issue2677Mapper.class
})
class Issue2677Test {

    @ProcessorTest
    void shouldCorrectlyUseGenericsWithExtends() {
        Issue2677Mapper.Parent parent = new Issue2677Mapper.Parent( 10 );
        Issue2677Mapper.Child child = new Issue2677Mapper.Child( 15, "Test" );

        Issue2677Mapper.Output output = Issue2677Mapper.INSTANCE.map( new Issue2677Mapper.Wrapper<>(
            parent,
            "extends"
        ) );

        assertThat( output.getStatus() ).isEqualTo( "extends" );
        assertThat( output.getId() ).isEqualTo( 10 );

        output = Issue2677Mapper.INSTANCE.mapFromChild( new Issue2677Mapper.Wrapper<>(
            child,
            "child"
        ) );

        assertThat( output.getStatus() ).isEqualTo( "child" );
        assertThat( output.getId() ).isEqualTo( 15 );

        output = Issue2677Mapper.INSTANCE.mapFromParent( new Issue2677Mapper.Wrapper<>(
            parent,
            "parent"
        ) );

        assertThat( output.getStatus() ).isEqualTo( "parent" );
        assertThat( output.getId() ).isEqualTo( 10 );

        output = Issue2677Mapper.INSTANCE.mapImplicitly( new Issue2677Mapper.Wrapper<>(
            child,
            "implicit"
        ) );

        assertThat( output.getStatus() ).isEqualTo( "implicit" );
        assertThat( output.getId() ).isEqualTo( 15 );

        Issue2677Mapper.Wrapper<String> result = Issue2677Mapper.INSTANCE.mapToWrapper(
            "test",
            new Issue2677Mapper.Wrapper<>(
                child,
                "super"
            )
        );

        assertThat( result.getStatus() ).isEqualTo( "super" );
        assertThat( result.getValue() ).isEqualTo( "test" );

        output = Issue2677Mapper.INSTANCE.mapWithPresenceCheck(
            new Issue2677Mapper.Wrapper<>(
                new Issue2677Mapper.ParentWithPresenceCheck( 8 ),
                "presenceCheck"
            )
        );

        assertThat( output.getStatus() ).isEqualTo( "presenceCheck" );
        assertThat( output.getId() ).isEqualTo( 0 );

        output = Issue2677Mapper.INSTANCE.mapWithPresenceCheck(
            new Issue2677Mapper.Wrapper<>(
                new Issue2677Mapper.ParentWithPresenceCheck( 15 ),
                "presenceCheck"
            )
        );

        assertThat( output.getStatus() ).isEqualTo( "presenceCheck" );
        assertThat( output.getId() ).isEqualTo( 15 );

    }
}
