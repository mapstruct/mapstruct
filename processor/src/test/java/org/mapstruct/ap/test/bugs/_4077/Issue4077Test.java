/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4077;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reproducer for <a href="https://github.com/mapstruct/mapstruct/issues/4077">4077</a>.
 *
 * @author Agustin Ranieri
 */
@IssueKey( "4077" )
@WithJSpecify
public class Issue4077Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( { Issue4077Mapper.class, Source.class, Target.class } )
    public void shouldAddNullCheckBeforeReusingMethod() {
        generatedSource.addComparisonToFixtureFor( Issue4077Mapper.class );

        // a null nested source must be mapped to a null nested target
        Target target = Mappers.getMapper( Issue4077Mapper.class ).map( new Source( null ) );
        assertThat( target.getNested() ).isNull();

        Source source = new Source( new Source.Nested( "foo", 1 ) );
        target = Mappers.getMapper( Issue4077Mapper.class ).map( source );
        assertThat( target.getNested() ).isNotNull();
        assertThat( target.getNested().getFoo() ).isEqualTo( "foo" );
        assertThat( target.getNested().getBar() ).isEqualTo( 1 );
    }
}
