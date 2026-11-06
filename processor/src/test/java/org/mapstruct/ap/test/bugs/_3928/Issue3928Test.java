/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3928;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJavaxJaxb;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The field of a generated JAXB class can differ in case from the property, e.g. {@code xyFirst} for the property
 * {@code XYFirst}. The {@code ObjectFactory} method should still be selected on the name of its {@code XmlElementRef}.
 */
@IssueKey("3928")
@WithClasses(Issue3928Mapper.class)
@WithJavaxJaxb
public class Issue3928Test {

    @ProcessorTest
    public void shouldSelectFactoryMethodWhenFieldNameDiffersInCaseFromProperty() {
        Issue3928Mapper.Source source = new Issue3928Mapper.Source();
        source.setXYFirst( "first" );
        source.setXYSecond( "second" );

        Issue3928Mapper.Target target = Issue3928Mapper.INSTANCE.map( source );

        assertThat( target.getXYFirst().getName() ).isEqualTo( Issue3928Mapper.ObjectFactory.XY_FIRST_QNAME );
        assertThat( target.getXYFirst().getValue() ).isEqualTo( "first" );
        assertThat( target.getXYSecond().getName() ).isEqualTo( Issue3928Mapper.ObjectFactory.XY_SECOND_QNAME );
        assertThat( target.getXYSecond().getValue() ).isEqualTo( "second" );
    }
}
