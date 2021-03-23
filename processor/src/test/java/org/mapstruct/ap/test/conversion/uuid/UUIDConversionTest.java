/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.uuid;

import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests conversions between {@link java.util.UUID} and String.
 *
 * @author Jason Bodnar
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class UUIDConversionTest {

    @Test
    @IssueKey("2391")
    @WithClasses({ UUIDSource.class, UUIDTarget.class, UUIDMapper.class })
    public void shouldApplyUUIDConversion() {
        UUIDSource source = new UUIDSource();
        source.setUUIDA( UUID.randomUUID() );

        UUIDTarget target = UUIDMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getUUIDA() ).isEqualTo( source.getUUIDA().toString() );
    }

    @Test
    @IssueKey("2391")
    @WithClasses({ UUIDSource.class, UUIDTarget.class, UUIDMapper.class })
    public void shouldApplyReverseUUIDConversion() {
        UUIDTarget target = new UUIDTarget();
        target.setUUIDA( UUID.randomUUID().toString() );

        UUIDSource source = UUIDMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getUUIDA() ).isEqualTo( UUID.fromString( target.getUUIDA() ) );
    }

    @Test()
    @IssueKey("2391")
    @WithClasses( { UUIDSource.class, UUIDTarget.class, UUIDMapper.class } )
    public void shouldHandleInvalidUUIDString() {
        UUIDTarget target = new UUIDTarget();
        target.setInvalidUUID( "XXXXXXXXX" );

        assertThatExceptionOfType( IllegalArgumentException.class )
            .isThrownBy( () -> UUIDMapper.INSTANCE.targetToSource( target ) );
    }
}
