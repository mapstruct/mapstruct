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
package org.mapstruct.ap.test.ignore.inherit;

import java.util.Date;

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
    HammerDto.class,
    HammerEntity.class,
    ToolEntity.class,
    BaseEntity.class,
    ToolDto.class,
    WorkBenchDto.class,
    WorkBenchEntity.class,
    ToolMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class IgnorePropertyTest {

    @Test
    @IssueKey("1392")
    /**
     * Should not issue warnings on unmapped target properties
     */
    public void shouldIgnoreAllExeptOveriddenInherited() {
        HammerDto hammer = new HammerDto();
        hammer.setToolType( "smash" );
        hammer.setToolSize( 5 );

        HammerEntity toolTarget = ToolMapper.INSTANCE.mapHammer( hammer );

        assertThat( toolTarget ).isNotNull();
        assertThat( toolTarget.getSize() ).isNull();
        assertThat( toolTarget.getType() ).isEqualTo( "smash" );
        assertThat( toolTarget.getKey() ).isNull();
        assertThat( toolTarget.getCreationDate() ).isNull();
        assertThat( toolTarget.getModificationDate() ).isNull();

    }

    @Test
    @IssueKey("1392")
    public void shouldIgnoreBase() {

        WorkBenchDto workBenchDto = new WorkBenchDto();
        workBenchDto.setArticleName( "MyBench" );
        workBenchDto.setArticleDescription( "Beautiful" );
        workBenchDto.setCreationDate( new Date() );
        workBenchDto.setModificationDate( new Date() );

        WorkBenchEntity benchTarget = ToolMapper.INSTANCE.mapBench( workBenchDto );

        assertThat( benchTarget ).isNotNull();
        assertThat( benchTarget.getArticleName() ).isEqualTo( "MyBench" );
        assertThat( benchTarget.getDescription() ).isEqualTo( "Beautiful" );
        assertThat( benchTarget.getKey() ).isNull();
        assertThat( benchTarget.getModificationDate() ).isNull();
        assertThat( benchTarget.getCreationDate() ).isNull();
    }

}
