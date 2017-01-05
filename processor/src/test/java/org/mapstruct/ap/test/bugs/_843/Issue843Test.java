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
package org.mapstruct.ap.test.bugs._843;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Commit.class,
    TagInfo.class,
    GitlabTag.class,
    TagMapper.class
})
@IssueKey("843")
public class Issue843Test {

    @Test
    public void testMapperCreation() {

        Commit commit = new Commit();
        commit.setAuthoredDate( new Date() );
        GitlabTag gitlabTag = new GitlabTag();
        gitlabTag.setCommit( commit );

        TagMapper.INSTANCE.gitlabTagToTagInfo( gitlabTag );

        assertThat( Commit.getCallCounter() ).isEqualTo( 1 );
        assertThat( GitlabTag.getCallCounter() ).isEqualTo( 1 );

    }

}
