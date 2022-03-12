/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2538;

/**
 * @author Filip Hrisafov
 */
public class TeamRole {

    private final Group group;

    public TeamRole(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }
}
