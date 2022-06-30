/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.test.selection.jaxb.underscores.ObjectFactory;
import org.mapstruct.ap.test.selection.jaxb.underscores.SubType;
import org.mapstruct.ap.test.selection.jaxb.underscores.SuperType;
import org.mapstruct.ap.test.selection.jaxb.underscores.UnderscoreMapper;
import org.mapstruct.ap.test.selection.jaxb.underscores.UnderscoreType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJavaxJaxb;

/**
 * Ensure factory method selection works for classes generated from schemas using element names with underscores
 *
 * @author Vincent Alexander Beelte
 */
@IssueKey( "726" )
@WithClasses( { UnderscoreType.class, ObjectFactory.class, SuperType.class, SubType.class, UnderscoreMapper.class } )
@WithJavaxJaxb
public class UnderscoreSelectionTest {

    @ProcessorTest
    public void selectingUnderscorePropertiesWorks() {
        SubType target = UnderscoreMapper.INSTANCE.map( createSource() );
        assertThat( target.getInheritedUnderscore().getValue() ).isEqualTo( "hi" );
        assertThat( target.getDeclaredUnderscore().getValue() ).isEqualTo( "there" );
    }

    private UnderscoreType createSource() {
        UnderscoreType type = new UnderscoreType();
        type.setInheritedUnderscore( "hi" );
        type.setDeclaredUnderscore( "there" );
        return type;
    }
}
