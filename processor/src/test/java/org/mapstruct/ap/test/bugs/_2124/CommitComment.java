/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2124;

/**
 * @author Filip Hrisafov
 */
public class CommitComment {

    private final Integer issueId;

    public CommitComment(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getIssueId() {
        return issueId;
    }
}
