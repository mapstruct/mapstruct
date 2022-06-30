/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.defaults;

import java.util.UUID;

import org.assertj.core.api.Assertions;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@WithClasses( { DirectoryNode.class, Folder.class } )
public class QualifierWithDefaultsTest {

    @ProcessorTest
    @WithClasses( FaultyDefaultValueUsageMapper.class )
    void defaultValueHasInvalidValue() {
        Folder rootFolder = new Folder( UUID.randomUUID(), null );
        FaultyDefaultValueUsageMapper faultyMapper = Mappers.getMapper( FaultyDefaultValueUsageMapper.class );

        Assertions
                  .assertThatThrownBy( () -> faultyMapper.convert( rootFolder ) )
                  .isInstanceOf( IllegalArgumentException.class ); // UUID.valueOf should throw this.
    }

    @ProcessorTest
    @WithClasses( DefaultValueUsageMapper.class )
    void defaultValuehasUsableValue() {
        Folder rootFolder = new Folder( UUID.randomUUID(), null );

        DirectoryNode node = Mappers.getMapper( DefaultValueUsageMapper.class ).convert( rootFolder );

        Assertions.assertThat( node.getParent() ).isEqualTo( "00000000-0000-4000-0000-000000000000" );
    }

    @ProcessorTest
    @WithClasses( DefaultExpressionUsageMapper.class )
    void defaultExpressionDoesNotGetConverted() {
        Folder rootFolder = new Folder( UUID.randomUUID(), null );

        DirectoryNode node = Mappers.getMapper( DefaultExpressionUsageMapper.class ).convert( rootFolder );

        Assertions.assertThat( node.getParent() ).isEqualTo( "#" );
    }
}
