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
public class ProjectEntityWithoutSetter {

    private String name;
    private List<ProjCoreUserEntity> coreUsers = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProjCoreUserEntity> getCoreUsers() {
        return coreUsers;
    }

}
