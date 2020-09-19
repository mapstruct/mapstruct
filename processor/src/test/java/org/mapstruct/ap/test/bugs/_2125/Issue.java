/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2125;

/**
 * @author Filip Hrisafov
 */
public class Issue {
    private final Integer issueId;

    public Issue(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getIssueId() {
        return issueId;
    }
}
