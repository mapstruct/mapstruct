/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1650;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("1650")
@WithClasses({
    AMapper.class,
    A.class,
    B.class,
    C.class,
    APrime.class,
    CPrime.class
})
public class Issue1650Test {

    @ProcessorTest
    public void shouldCompile() {

       A a = new A();
       a.setB( new B() );
       a.getB().setC( new C( 10 ) );

       APrime aPrime = new APrime();

       // update mapping
       AMapper.INSTANCE.toAPrime( a, aPrime );
       assertThat( aPrime.getcPrime() ).isNotNull();

       // create mapping
        APrime aPrime1 = AMapper.INSTANCE.toAPrime( a );
        assertThat( aPrime1.getcPrime() ).isNotNull();
    }
}
