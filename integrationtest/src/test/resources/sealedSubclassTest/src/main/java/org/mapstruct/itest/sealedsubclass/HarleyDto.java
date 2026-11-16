/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.sealedsubclass;

public final class HarleyDto extends MotorDto {
    private int engineDb;

    public int getEngineDb() {
        return engineDb;
    }

    public void setEngineDb(int engineDb) {
        this.engineDb = engineDb;
    }
}
