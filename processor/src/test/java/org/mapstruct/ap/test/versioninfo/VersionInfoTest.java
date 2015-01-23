/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.versioninfo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOptions;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Andreas Gudian
 *
 */
@RunWith( AnnotationProcessorTestRunner.class )
@IssueKey( "424" )
@WithClasses( SimpleMapper.class )
public class VersionInfoTest {
    private final GeneratedSource generatedSource = new GeneratedSource();

    @Rule
    public GeneratedSource getGeneratedSource() {
        return generatedSource;
    }

    @Test
    @ProcessorOption( name = "mapstruct.suppressGeneratorVersionInfoComment", value = "true" )
    public void includesNoComment() {
        generatedSource.forMapper( SimpleMapper.class ).content()
            .contains( "date = \"" )
            .doesNotContain( "comments = \"version: " );
    }

    @Test
    @ProcessorOptions( {
        @ProcessorOption( name = "mapstruct.suppressGeneratorVersionInfoComment", value = "true" ),
        @ProcessorOption( name = "mapstruct.suppressGeneratorTimestamp", value = "true" )
    } )
    public void includesNoCommentAndNoTimestamp() {
        generatedSource.forMapper( SimpleMapper.class ).content()
            .doesNotContain( "date = \"" )
            .doesNotContain( "comments = \"version: " );
    }

    @Test
    public void includesCommentAndTimestamp() {
        generatedSource.forMapper( SimpleMapper.class ).content()
            .contains( "date = \"" )
            .contains( "comments = \"version: " );
    }

}
