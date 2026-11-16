/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._895;

import org.mapstruct.ap.test.bugs._895.MultiArrayMapper.WithArrayOfByteArray;
import org.mapstruct.ap.test.bugs._895.MultiArrayMapper.WithArrayOfGenericArray;
import org.mapstruct.ap.test.bugs._895.MultiArrayMapper.WithListOfByteArray;
import org.mapstruct.ap.test.bugs._895.MultiArrayMapper.WithListOfGenericArray;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that forged iterable mapping methods for multidimensional arrays are generated properly.
 *
 * @author Andreas Gudian
 */
@IssueKey("895")
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

    @ProcessorTest
    public void properlyMapsGenericMultiDimensionalArrays() {
        WithArrayOfGenericArray<String> arrayOfStringArray = new MultiArrayMapper.WithArrayOfGenericArray<>();
        arrayOfStringArray.setData( new String[][] { new String[] { "a", "b" }, new String[] { "b", "c" } } );

        MultiArrayMapper mapper = Mappers.getMapper( MultiArrayMapper.class );
        WithListOfGenericArray<String> listOfStringArray = mapper.convertGeneric( arrayOfStringArray );
        assertThat( listOfStringArray.getData() )
                .containsExactly( new String[] { "a", "b" }, new String[] { "b", "c" } );

        arrayOfStringArray = mapper.convertGeneric( listOfStringArray );
        assertThat( arrayOfStringArray.getData() )
                .isDeepEqualTo( new String[][] { new String[] { "a", "b" }, new String[] { "b", "c" } } );
    }

    @ProcessorTest
    public void properlyReturnDefaultForMultiArray() {
        assertThat( Mappers.getMapper( MultiArrayMapper.class ).copyMultiArray( null ) ).isEmpty();
    }

    @ProcessorTest
    @SuppressWarnings("unchecked")
    public void properlyMapsGenericMultiArray() {
        WithArrayOfGenericArray<String > arrayOfGenericArray = new WithArrayOfGenericArray<>();
        arrayOfGenericArray.setData( new String[][] { new String[] { "a", "b" }, new String[] { "b", "c" } } );
        WithArrayOfGenericArray<String>[][] multiArrayOfGenericArray =
                new WithArrayOfGenericArray[][]{ new WithArrayOfGenericArray[]{ arrayOfGenericArray } };
        WithArrayOfGenericArray<String>[][] result =
                Mappers.getMapper( MultiArrayMapper.class ).copyGenericMultiArray( multiArrayOfGenericArray );
        assertThat( result ).isNotSameAs( multiArrayOfGenericArray );
        assertThat( result ).isDeepEqualTo( multiArrayOfGenericArray );
    }

    @ProcessorTest
    public void properlyReturnDefaultForGenericMultiArray() {
        assertThat( Mappers.getMapper( MultiArrayMapper.class ).copyGenericMultiArray( null ) ).isEmpty();
    }
}
