/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._289;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

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
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue289Test {

    @Test
    public void shouldLeaveEmptyTargetSetWhenSourceIsNullAndGetterOnlyForCreateMethod() {

        Source source = new Source();
        source.setCollection( null );

        TargetWithoutSetter target = Issue289Mapper.INSTANCE.sourceToTargetWithoutSetter( source );

        assertThat( target.getCollection() ).isEmpty();
    }

    @Test
    public void shouldLeaveEmptyTargetSetWhenSourceIsNullAndGetterOnlyForUpdateMethod() {

        Source source = new Source();
        source.setCollection( null );
        TargetWithoutSetter target = new TargetWithoutSetter();
        target.getCollection().add( new TargetElement() );

        Issue289Mapper.INSTANCE.sourceToTargetWithoutSetter( source, target );

        assertThat( target.getCollection() ).isEmpty();
    }

    @Test
    public void shouldLeaveNullTargetSetWhenSourceIsNullForCreateMethod() {

        Source source = new Source();
        source.setCollection( null );

        TargetWithSetter target = Issue289Mapper.INSTANCE.sourceToTargetWithSetter( source );

        assertThat( target.getCollection() ).isNull();
    }
}
