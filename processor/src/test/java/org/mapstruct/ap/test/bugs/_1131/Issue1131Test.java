/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1131;

import java.util.ArrayList;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1131")
@WithClasses({
    Issue1131Mapper.class,
    Issue1131MapperWithContext.class,
    Source.class,
    Target.class
})
public class Issue1131Test {

    @ProcessorTest
    public void shouldUseCreateWithSourceNested() {

        Source source = new Source();
        source.setNested( new Source.Nested() );
        source.getNested().setProperty( "something" );
        source.setMoreNested( new ArrayList<>() );

        Target target = new Target();

        Issue1131Mapper.CALLED_METHODS.clear();
        Issue1131Mapper.INSTANCE.merge( source, target );

        assertThat( target.getNested() ).isNotNull();
        assertThat( target.getNested().getProperty() ).isEqualTo( "something" );
        assertThat( target.getNested().getInternal() ).isEqualTo( "from object factory" );
        assertThat( Issue1131Mapper.CALLED_METHODS ).containsExactly(
            "create(Source.Nested)",
            "create(List<Source.Nested>)"
        );
    }

    @ProcessorTest
    public void shouldUseContextObjectFactory() {

        Source source = new Source();
        source.setNested( new Source.Nested() );
        source.getNested().setProperty( "something" );
        source.setMoreNested( new ArrayList<>() );

        Target target = new Target();

        Issue1131MapperWithContext.MappingContext context = new Issue1131MapperWithContext.MappingContext();
        Issue1131MapperWithContext.INSTANCE.merge( source, target, context );

        assertThat( target.getNested() ).isNotNull();
        assertThat( target.getNested().getProperty() ).isEqualTo( "something" );
        assertThat( target.getNested().getInternal() ).isEqualTo( "from within @Context" );
        assertThat( context.getCalledMethods() ).containsExactly(
            "create(Source.Nested)",
            "create(List<Source.Nested>)"
        );
    }
}
