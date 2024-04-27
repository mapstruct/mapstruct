/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.deepstructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey( "3575" )
@WithClasses( { Source.class, Target.class, SourceContainer.class, TargetContainer.class, TargetCollectionItem.class,
    SourceCollectionItem.class, SourceInnerChild.class, SourceCollectionContainer.class, TargetChild.class,
    SourceSecondSubChild.class, TargetSubChild.class, SourceChild.class, SourceSubChild.class } )
public class DeepNestedMapperTest {

    @ProcessorTest
    @WithClasses( DeepNestedMapper.class )
    void mapperWithoutIgnoreByDefault() {
        SourceContainer sourceContainer = createSourceContainer();

        TargetContainer targetContainer = DeepNestedMapper.INSTANCE.map( sourceContainer );

        assertTargetFields( targetContainer );
        assertThat( targetContainer.getOtherField() ).isNotNull();
        assertThat( targetContainer.getTarget().getOtherField() ).isNotNull();
    }

    @ProcessorTest
    @WithClasses( DeepNestedIgnoreByDefaultMapper.class )
    void mapperWithIgnoreByDefault() {
        SourceContainer sourceContainer = createSourceContainer();

        TargetContainer targetContainer = DeepNestedIgnoreByDefaultMapper.INSTANCE.map( sourceContainer );

        assertThat( targetContainer.getOtherField() ).isNull(); // covered by ignoreByDefault
        assertThat( targetContainer.getTarget().getOtherField() ).isNull(); // covered by ignoreByDefault
        assertTargetFields( targetContainer ); // these should not be covered by ignoreByDefault
    }

    private SourceContainer createSourceContainer() {
        SourceContainer sourceContainer = new SourceContainer();
        sourceContainer.setOtherField( "otherField" );
        Source source = new Source();
        source.setOtherField( "otherField" );
        SourceInnerChild sourceInnerChild = new SourceInnerChild();
        SourceSecondSubChild nestedSecondSourceChild = new SourceSecondSubChild();
        nestedSecondSourceChild.setAutoMapField( "automapfield - nestedSecondSourceChild" );
        sourceInnerChild.setNestedSecondSourceChild( nestedSecondSourceChild );
        SourceChild sourceChild = new SourceChild();
        SourceSubChild sourceSubChild = new SourceSubChild();
        sourceSubChild.setAutoMapField( "automapfield - sourceSubChild" );
        sourceChild.setAutoMapChild( sourceSubChild );
        sourceInnerChild.setNestedSourceChild( List.of( sourceChild ) );
        source.setSourceInnerChild( sourceInnerChild );
        sourceContainer.setSource( source );

        SourceCollectionContainer collectionContainer = new SourceCollectionContainer();
        SourceCollectionItem sourceCollectionItem = new SourceCollectionItem();
        sourceCollectionItem.setItem( "item" );
        collectionContainer.setSource( List.of( sourceCollectionItem ) );
        sourceContainer.setCollectionContainer( collectionContainer );
        return sourceContainer;
    }

    private void assertTargetFields(TargetContainer container) {
        Optional<String> targetSubChildAutoMapField =
            Optional
                    .ofNullable( container )
                    .map( TargetContainer::getTarget )
                    .map( Target::getNestedSecondTargetChild )
                    .map( TargetChild::getAutoMapChild )
                    .map( TargetSubChild::getAutoMapField );
        assertThat( targetSubChildAutoMapField ).contains( "automapfield - nestedSecondSourceChild" );
        Stream<String> nestedTargetChildsAutoMapFields =
            Optional
                    .ofNullable( container )
                    .map( TargetContainer::getTarget )
                    .map( Target::getNestedTargetChild )
                    .map( Collection::stream )
                    .orElse( Stream.of() )
                    .map( TargetChild::getAutoMapChild )
                    .map( TargetSubChild::getAutoMapField );
        assertThat( nestedTargetChildsAutoMapFields ).containsExactly( "automapfield - sourceSubChild" );
        Stream<String> targetCollectionItems =
            Optional
                    .ofNullable( container )
                    .map( TargetContainer::getTargetCollection )
                    .map( Collection::stream )
                    .orElse( Stream.of() )
                    .map( TargetCollectionItem::getItem );
        assertThat( targetCollectionItems ).containsExactly( "item" );
    }
}
