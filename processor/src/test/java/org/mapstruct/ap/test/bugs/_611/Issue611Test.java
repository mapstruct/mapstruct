/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._611;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Tillmann Gaida
 */
@IssueKey("611")
@WithClasses({
    SomeClass.class,
    SomeOtherClass.class
})
public class Issue611Test {
    /**
     * Checks if an implementation of a nested mapper can be loaded at all.
     */
    @ProcessorTest
    public void mapperIsFound() {
        assertThat( SomeClass.InnerMapper.INSTANCE ).isNotNull();
    }

    /**
     * Checks if an implementation of a nested mapper can be loaded which is nested into an already
     * nested class.
     */
    @ProcessorTest
    public void mapperNestedInsideNestedClassIsFound() {
        assertThat( SomeClass.SomeInnerClass.InnerMapper.INSTANCE ).isNotNull();
    }

    /**
     * Checks if it is possible to load two mapper implementations which have equal simple names
     * in the same package.
     */
    @ProcessorTest
    public void rightMapperIsFound() {
        SomeClass.InnerMapper.Source source1 = new SomeClass.InnerMapper.Source( "test" );
        SomeOtherClass.InnerMapper.Source source2 = new SomeOtherClass.InnerMapper.Source( "test2" );

        SomeClass.InnerMapper.Target target1 = SomeClass.InnerMapper.INSTANCE.toTarget( source1 );
        SomeOtherClass.InnerMapper.Target target2 = SomeOtherClass.InnerMapper.INSTANCE.toTarget( source2 );

        assertThat( target1 ).isExactlyInstanceOf( SomeClass.InnerMapper.Target.class );
        assertThat( target2 ).isExactlyInstanceOf( SomeOtherClass.InnerMapper.Target.class );
    }
}
