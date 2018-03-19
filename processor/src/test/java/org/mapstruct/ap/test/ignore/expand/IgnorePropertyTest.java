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
package org.mapstruct.ap.test.ignore.expand;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for ignoring properties during the mapping.
 *
 * @author Sjaak Derksen
 */
@WithClasses({
    ExpandedToolbox.class,
    Hammer.class,
    Wrench.class,
    FlattenedToolBox.class,
    ToolBoxMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class IgnorePropertyTest {

    @Test
    @IssueKey("1392")
    public void shouldIgnoreAll() {
        FlattenedToolBox toolboxSource = new FlattenedToolBox();
        toolboxSource.setBrand( "Stanley" );
        toolboxSource.setHammerDescription( "heavy" );
        toolboxSource.setHammerSize( 5 );
        toolboxSource.setWrenchIsBahco( Boolean.TRUE );
        toolboxSource.setWrenchDescription( "generic use" );

        ExpandedToolbox toolboxTarget = ToolBoxMapper.INSTANCE.expand( toolboxSource );

        assertThat( toolboxTarget ).isNotNull();
        assertThat( toolboxTarget.getBrand() ).isNull();
        assertThat( toolboxTarget.getHammer() ).isNotNull();
        assertThat( toolboxTarget.getHammer().getDescription() ).isEqualTo( "heavy" );
        assertThat( toolboxTarget.getHammer().getSize() ).isNull();
        assertThat( toolboxTarget.getWrench() ).isNull();
    }

}
