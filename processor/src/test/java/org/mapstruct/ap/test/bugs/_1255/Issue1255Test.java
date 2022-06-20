/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1255;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey("1255")
@WithClasses({
    AbstractA.class,
    SomeA.class,
    SomeB.class,
    SomeMapper.class,
    SomeMapperConfig.class})
public class Issue1255Test {

    @ProcessorTest
    public void shouldMapSomeBToSomeAWithoutField1() {
        SomeB someB = new SomeB();
        someB.setField1( "value1" );
        someB.setField2( "value2" );

        SomeA someA = SomeMapper.INSTANCE.toSomeA( someB );

        assertThat( someA.getField1() )
            .isNotEqualTo( someB.getField1() )
            .isNull();
        assertThat( someA.getField2() ).isEqualTo( someB.getField2() );
    }

    @ProcessorTest
    public void shouldMapSomeAToSomeB() {
        SomeA someA = new SomeA();
        someA.setField1( "value1" );
        someA.setField2( "value2" );

        SomeB someB = SomeMapper.INSTANCE.toSomeB( someA );

        assertThat( someB.getField1() ).isEqualTo( someA.getField1() );
        assertThat( someB.getField2() ).isEqualTo( someA.getField2() );
    }
}
