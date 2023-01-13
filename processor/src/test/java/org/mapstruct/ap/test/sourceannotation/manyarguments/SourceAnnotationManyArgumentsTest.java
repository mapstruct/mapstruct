/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.sourceannotation.manyarguments;

import org.mapstruct.ap.test.sourceannotation.StringConversion;
import org.mapstruct.ap.test.sourceannotation.Target;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    FirstSource.class,
    SecondSource.class,
    SourceTargetMapper.class,
    StringConversion.class,
    Target.class,
})
public class SourceAnnotationManyArgumentsTest {

    @ProcessorTest
    public void sourceAnnotation() {
        FirstSource first = new FirstSource( "Hello" );
        SecondSource second = new SecondSource( "World" );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( first, second );

        assertThat( target ).isNotNull();
        assertThat( target.getFirstProperty() ).isEqualTo( "HELLO" );
        assertThat( target.getSecondProperty() ).isEqualTo( "world" );
    }

}
