/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references.samename;

import org.mapstruct.ap.test.references.samename.a.AnotherSourceTargetMapper;
import org.mapstruct.ap.test.references.samename.a.CustomMapper;
import org.mapstruct.ap.test.references.samename.model.Source;
import org.mapstruct.ap.test.references.samename.model.Target;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for referring several mappers with the same simple name.
 *
 * @author Gunnar Morling
 */
@IssueKey("112")
@WithClasses({
    Source.class,
    Target.class,
    SourceTargetMapper.class,
    CustomMapper.class,
    org.mapstruct.ap.test.references.samename.b.CustomMapper.class,
    Jsr330SourceTargetMapper.class,
    AnotherSourceTargetMapper.class
})
public class SeveralReferencedMappersWithSameSimpleNameTest {

    @ProcessorTest
    public void severalMappersWithSameSimpleNameCanBeReferenced() {
        Source source = new Source();
        source.setFoo( 123 );
        source.setBar( 456L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( "246" );
        assertThat( target.getBar() ).isEqualTo( "912" );
    }

    @ProcessorTest
    public void mapperInSamePackageAndAnotherMapperWithSameNameInAnotherPackageCanBeReferenced() {
        Source source = new Source();
        source.setFoo( 123 );
        source.setBar( 456L );

        Target target = AnotherSourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( "246" );
        assertThat( target.getBar() ).isEqualTo( "912" );
    }
}
