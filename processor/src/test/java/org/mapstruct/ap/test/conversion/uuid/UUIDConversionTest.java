/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.uuid;

import java.util.UUID;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests conversions between {@link java.util.UUID} and String.
 *
 * @author Jason Bodnar
 */
@IssueKey("2391")
@WithClasses({ UUIDSource.class, UUIDTarget.class, UUIDMapper.class })
public class UUIDConversionTest {

    @ProcessorTest
    public void shouldApplyUUIDConversion() {
        UUIDSource source = new UUIDSource();
        source.setUUIDA( UUID.randomUUID() );

        UUIDTarget target = UUIDMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getUUIDA() ).isEqualTo( source.getUUIDA().toString() );
    }

    @ProcessorTest
    public void shouldApplyReverseUUIDConversion() {
        UUIDTarget target = new UUIDTarget();
        target.setUUIDA( UUID.randomUUID().toString() );

        UUIDSource source = UUIDMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getUUIDA() ).isEqualTo( UUID.fromString( target.getUUIDA() ) );
    }

    @ProcessorTest
    public void shouldHandleInvalidUUIDString() {
        UUIDTarget target = new UUIDTarget();
        target.setInvalidUUID( "XXXXXXXXX" );

        assertThatThrownBy( () -> UUIDMapper.INSTANCE.targetToSource( target ) )
            .isInstanceOf( IllegalArgumentException.class );
    }
}
