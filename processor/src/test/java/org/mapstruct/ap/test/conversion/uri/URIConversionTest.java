/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.uri;

import java.net.URI;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests conversions between {@link java.net.URI} and String.
 *
 * @author Maciej Kucharczyk
 */
@IssueKey("4018")
@WithClasses({ URISource.class, URITarget.class, URIMapper.class })
public class URIConversionTest {

    @ProcessorTest
    public void shouldApplyURIConversion() {
        URISource source = new URISource();
        source.setUriA( URI.create( "https://mapstruct.org/" ) );

        URITarget target = URIMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getUriA() ).isEqualTo( source.getUriA().toString() );
    }

    @ProcessorTest
    public void shouldApplyReverseURIConversion() {
        URITarget target = new URITarget();
        target.setUriA( "https://mapstruct.org/" );

        URISource source = URIMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getUriA() ).isEqualTo( URI.create( target.getUriA() ) );
    }

    @ProcessorTest
    public void shouldHandleInvalidURIString() {
        URITarget target = new URITarget();
        target.setInvalidURI( "ht!tp://example.com" );

        assertThatThrownBy( () -> URIMapper.INSTANCE.targetToSource( target ) )
            .isInstanceOf( IllegalArgumentException.class );
    }
}
