/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._895;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.test.bugs._895.MultiArrayMapper.WithArrayOfByteArray;
import org.mapstruct.ap.test.bugs._895.MultiArrayMapper.WithListOfByteArray;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

/**
 * Verifies that forged iterable mapping methods for multi-dimensional arrays are generated properly.
 *
 * @author Andreas Gudian
 */
@WithClasses(MultiArrayMapper.class)
public class Issue895Test {
    @ProcessorTest
    public void properlyMapsMultiDimensionalArrays() {
        WithArrayOfByteArray arrayOfByteArray = new WithArrayOfByteArray();
        arrayOfByteArray.setBytes( new byte[][] { new byte[] { 0, 1 }, new byte[] { 1, 2 } } );

        WithListOfByteArray listOfByteArray = Mappers.getMapper( MultiArrayMapper.class ).convert( arrayOfByteArray );
        assertThat( listOfByteArray.getBytes() ).containsExactly( new byte[] { 0, 1 }, new byte[] { 1, 2 } );

        arrayOfByteArray = Mappers.getMapper( MultiArrayMapper.class ).convert( listOfByteArray );
        assertThat( arrayOfByteArray.getBytes() ).isDeepEqualTo( new byte[][] { { 0, 1 }, { 1, 2 } } );
    }
}
