/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.mapstruct.ap.test.bugs._3652;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3652")
public class Issue3652Test {

    @WithClasses({
        Bar.class,
        Foo.class,
        FooBarConfig.class,
        FooBarMapper.class,
    })
    @ProcessorTest
    void ignoreMappingsWithoutSourceShouldBeInvertible() {
        Bar bar = new Bar();
        bar.setSecret( 123 );
        bar.setDoesNotExistInFoo( 6 );

        Foo foo = FooBarMapper.INSTANCE.toFoo( bar );

        assertThat( foo.getSecret() ).isEqualTo( 0 );
    }

}
