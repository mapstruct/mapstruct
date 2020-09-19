/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2125;

/**
 * @author Filip Hrisafov
 */
public
class Comment {
    private final Integer issueId;
    private final String comment;

    public Comment(Integer issueId, String comment) {
        this.issueId = issueId;
        this.comment = comment;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public String getComment() {
        return comment;
    }
}
