/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.url;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Tests conversions between {@link java.net.URL} and String.
 *
 * @author Adam Szatyin
 */
@WithClasses({ Source.class, Target.class, URLMapper.class })
public class URLConversionTest {

    @ProcessorTest
    public void shouldApplyURLConversion() throws MalformedURLException {
        Source source = new Source();
        source.setURL( new URL("https://mapstruct.org/") );

        Target target = URLMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getURL() ).isEqualTo( source.getURL().toString() );
    }

    @ProcessorTest
    public void shouldApplyReverseURLConversion() throws MalformedURLException {
        Target target = new Target();
        target.setURL( "https://mapstruct.org/" );

        Source source = URLMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getURL() ).isEqualTo( new URL( target.getURL() ) );
    }

    @ProcessorTest
    public void shouldHandleInvalidURLString() {
        Target target = new Target();
        target.setInvalidURL( "XXXXXXXXX" );

        assertThatThrownBy( () -> URLMapper.INSTANCE.targetToSource( target ) )
                .isInstanceOf( RuntimeException.class )
                .getRootCause().isInstanceOf( MalformedURLException.class );
    }

    @ProcessorTest
    public void shouldHandleInvalidURLStringWithMalformedURLException() {
        Target target = new Target();
        target.setInvalidURL( "XXXXXXXXX" );

        assertThatThrownBy( () -> URLMapper.INSTANCE.targetToSourceWithMalformedURLException( target ) )
                .isInstanceOf( MalformedURLException.class );
    }

    @ProcessorTest
    public void shouldHandleNullURLString() {
        Source source = new Source();

        Target target = URLMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getURL() ).isNull();
        assertThat( target.getInvalidURL() ).isNull();
    }
}
