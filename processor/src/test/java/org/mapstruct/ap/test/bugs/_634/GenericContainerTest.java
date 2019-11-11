/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._634;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Gunnar Morling
 */
@WithClasses({
    Bar.class,
    Foo.class,
    Source.class,
    Target.class,
    SourceTargetMapper.class,
})
@RunWith(AnnotationProcessorTestRunner.class)
public class GenericContainerTest {

    @Test
    @IssueKey("634")
    public void canMapGenericSourceTypeToGenericTargetType() {
        List<Foo> items = Arrays.asList( new Foo( "42" ), new Foo( "84" ) );
        Source<Foo> source = new Source<>( items );

        Target<Bar> target = SourceTargetMapper.INSTANCE.mapSourceToTarget( source );

        assertThat( target.getContent() ).extracting( "value" ).containsExactly( 42L, 84L );
    }
}
