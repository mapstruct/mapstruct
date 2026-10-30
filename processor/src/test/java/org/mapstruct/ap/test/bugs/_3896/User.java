/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3896;

public class User {

    private UserState state;
    private GroupState groupState;

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public GroupState getGroupState() {
        return groupState;
    }

    public void setGroupState(GroupState groupState) {
        this.groupState = groupState;
    }
}
