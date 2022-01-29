/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2677;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

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

    }
}
