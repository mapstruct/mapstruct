/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._865;

import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public class ProjectEntity {

    private String name;
    private List<ProjCoreUserEntity> coreUsers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProjCoreUserEntity> getCoreUsers() {
        return coreUsers;
    }

    public void setCoreUsers(List<ProjCoreUserEntity> coreUsers) {
        this.coreUsers = coreUsers;
    }
}
