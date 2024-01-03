/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._289;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/289.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "289" )
    @WithClasses( {
        Issue289Mapper.class,
        Source.class,
        TargetWithoutSetter.class,
        TargetWithSetter.class,
        SourceElement.class,
        TargetElement.class
    } )
public class Issue289Test {

    @ProcessorTest
    public void shouldLeaveEmptyTargetSetWhenSourceIsNullAndGetterOnlyForCreateMethod() {

        Source source = new Source();
        source.setCollection( null );

        TargetWithoutSetter target = Issue289Mapper.INSTANCE.sourceToTargetWithoutSetter( source );

        assertThat( target.getCollection() ).isEmpty();
    }

    @ProcessorTest
    public void shouldLeaveEmptyTargetSetWhenSourceIsNullAndGetterOnlyForUpdateMethod() {

        Source source = new Source();
        source.setCollection( null );
        TargetWithoutSetter target = new TargetWithoutSetter();
        TargetElement existingElement = new TargetElement();
        target.getCollection().add( existingElement );

        Issue289Mapper.INSTANCE.sourceToTargetWithoutSetter( source, target );

        assertThat( target.getCollection() ).containsExactly( existingElement );
    }

    @ProcessorTest
    public void shouldLeaveNullTargetSetWhenSourceIsNullForCreateMethod() {

        Source source = new Source();
        source.setCollection( null );

        TargetWithSetter target = Issue289Mapper.INSTANCE.sourceToTargetWithSetter( source );

        assertThat( target.getCollection() ).isNull();
    }
}
