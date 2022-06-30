/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.objectfactory;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

public class ObjectFactoryTest {

    @IssueKey( "2463" )
    @ProcessorTest
    @WithClasses(  ObjectFactoryMapper.class )
    public void testSelectionOfFactoryMethod() {

        ObjectFactoryMapper.SourceA source = new ObjectFactoryMapper.SourceA();

        ObjectFactoryMapper.Target target = ObjectFactoryMapper.INSTANCE.toTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target ).isInstanceOf( ObjectFactoryMapper.TargetA.class );
    }
}
