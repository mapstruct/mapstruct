/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.selection.jaxb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.selection.jaxb.underscores.ObjectFactory;
import org.mapstruct.ap.test.selection.jaxb.underscores.SubType;
import org.mapstruct.ap.test.selection.jaxb.underscores.SuperType;
import org.mapstruct.ap.test.selection.jaxb.underscores.UnderscoreMapper;
import org.mapstruct.ap.test.selection.jaxb.underscores.UnderscoreType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Ensure factory method selection works for classes generated from schemas using element names with underscores
 *
 * @author Vincent Alexander Beelte
 */
@IssueKey( "726" )
@WithClasses( { UnderscoreType.class, ObjectFactory.class, SuperType.class, SubType.class, UnderscoreMapper.class } )
@RunWith( AnnotationProcessorTestRunner.class )
public class UnderscoreSelectionTest {

    @Test
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
