/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingsource.context;

import java.util.Arrays;
import java.util.List;

import org.mapstruct.ap.test.mappingsource.context.MappingSourceWithContextMapper.Item;
import org.mapstruct.ap.test.mappingsource.context.MappingSourceWithContextMapper.ItemDTO;
import org.mapstruct.ap.test.mappingsource.context.MappingSourceWithContextMapper.Model;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3665")
@WithClasses(MappingSourceWithContextMapper.class)
public class MappingSourceWithContextTest {

    @ProcessorTest
    @WithClasses(MappingSourceWithContextMapper.class)
    public void testMappingSourceWithContext() {
        Model model = new Model( "MODEL-123" );
        List<Item> items = Arrays.asList(
            new Item( "ITEM-1", "First Item" ),
            new Item( "ITEM-2", "Second Item" )
        );
        model.setItems( items );

        List<ItemDTO> result = MappingSourceWithContextMapper.INSTANCE.toDto( model );

        assertThat( result ).isNotNull();
        assertThat( result ).hasSize( 2 );

        ItemDTO dto1 = result.getFirst();
        assertThat( dto1.getItemId() ).isEqualTo( "ITEM-1" );
        assertThat( dto1.getName() ).isEqualTo( "First Item" );
        assertThat( dto1.getModelId() ).isEqualTo( "MODEL-123" );

        ItemDTO dto2 = result.get( 1 );
        assertThat( dto2.getItemId() ).isEqualTo( "ITEM-2" );
        assertThat( dto2.getName() ).isEqualTo( "Second Item" );
        assertThat( dto2.getModelId() ).isEqualTo( "MODEL-123" );
    }
}
