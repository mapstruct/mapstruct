/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._843;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 *
 * @author Sjaak Derksen
 */
@WithClasses({
    Commit.class,
    TagInfo.class,
    GitlabTag.class,
    TagMapper.class
})
@IssueKey("843")
public class Issue843Test {

    @ProcessorTest
    public void testMapperCreation() {

        Commit commit = new Commit();
        commit.setAuthoredDate( new Date() );
        GitlabTag gitlabTag = new GitlabTag();
        gitlabTag.setCommit( commit );

        Commit.resetCallCounter();
        GitlabTag.resetCallCounter();
        TagMapper.INSTANCE.gitlabTagToTagInfo( gitlabTag );

        assertThat( Commit.getCallCounter() ).isEqualTo( 1 );
        assertThat( GitlabTag.getCallCounter() ).isEqualTo( 1 );

    }

}
