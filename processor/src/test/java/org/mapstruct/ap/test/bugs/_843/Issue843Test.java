/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
