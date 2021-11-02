/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2636;

import java.util.UUID;
import org.assertj.core.api.Assertions;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@WithClasses( Issue2636Mapper.class )
public class Isssue2636Test {

    @ProcessorTest
    void defaultValueShouldBeUsedInsteadOfMapped() {
        Folder rootFolder = new Folder( UUID.randomUUID(), null );

        DirectoryNode directoryNode = Mappers.getMapper( Issue2636Mapper.class ).convert( rootFolder );

        Assertions.assertThat( directoryNode.getParent() ).isEqualTo( "#" );
    }
}
